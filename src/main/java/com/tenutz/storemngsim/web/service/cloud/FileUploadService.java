package com.tenutz.storemngsim.web.service.cloud;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.domain.menu.id.MainMenuId;
import com.tenutz.storemngsim.domain.menu.id.OptionId;
import com.tenutz.storemngsim.web.api.storemngsim.dto.common.MenuImageArgs;
import com.tenutz.storemngsim.web.client.common.UploadClient;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException.CMenuImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.tenutz.storemngsim.web.exception.io.CIOException.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileUploadService {

    private final MenuImageRepository menuImageRepository;
    private final MainMenuRepository mainMenuRepository;
    private final OptionRepository optionRepository;
    private final UploadClient s3Client;

    @Transactional
    public String uploadKioskMenuImage(MenuImageArgs args) {

        String newFileName = createFileName(args.getFileToUpload().getOriginalFilename());
        String filePath = "FILE_MANAGER/" + args.getSiteCd() + "/" + args.getStoreCd();

        inactivateMenuImageIfExists(args);

        menuImageRepository.save(
                MenuImage.createKioskMenu(
                        args.getSiteCd(),
                        args.getStoreCd(),
                        newFileName,
                        null,
                        (int) args.getFileToUpload().getSize(),
                        "sms-img/" + filePath
                )
        );
        args.setNewFileName(newFileName);
        return upload(args.getFileToUpload(), filePath + "/" + newFileName);
    }

    private void inactivateMenuImageIfExists(MenuImageArgs args) {
        AtomicReference<String> imageName = new AtomicReference<>("");

        if(!args.getMainMenuCd().isEmpty()) {
            mainMenuRepository.findById(
                    MainMenuId.create(
                            args.getSiteCd(),
                            args.getStoreCd(),
                            args.getMainCateCd(),
                            args.getMiddleCateCd(),
                            args.getSubCateCd(),
                            args.getMainMenuCd()
                    )
            ).ifPresent(mainMenu -> { imageName.set(mainMenu.getImgNm()); });
        } else if(!args.getOptionCd().isEmpty()) {
            optionRepository.findById(
                    OptionId.create(
                            args.getSiteCd(),
                            args.getStoreCd(),
                            args.getOptionCd()
                    )
            ).ifPresent(option -> { imageName.set(option.getImgNm()); });
        }

        if(imageName.get() != null && !imageName.get().isEmpty()) {
            MenuImage foundMenuImage = menuImageRepository.findBySiteCdAndStrCdAndFileNm(
                    args.getSiteCd(),
                    args.getStoreCd(),
                    imageName.get()
            ).orElseThrow(CMenuImageNotFoundException::new);
            foundMenuImage.delete();
        }
    }

    /*public String uploadRollbackable(MenuImageArgs args, List<String> uploadedFiles) {
        try {
            return upload(args);
        } catch (CCloudCommunicationException | CFileConvertFailedException e) {
            deleteFiles(uploadedFiles);
            throw e;
        }
    }*/

    public String upload(MultipartFile fileToUpload, String filePath) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileToUpload.getSize());
        objectMetadata.setContentType(fileToUpload.getContentType());

        try (InputStream inputStream = fileToUpload.getInputStream()) {
            s3Client.upload(inputStream, objectMetadata, filePath);
        } catch (SdkClientException e) {
            throw new CCloudCommunicationException();
        } catch (IOException e) {
            throw new CFileConvertFailedException();
        }
        return s3Client.getFileUrl(filePath);
    }

    public boolean doesFileExists(String fileName) {
        return s3Client.doesFileExists(fileName);
    }

    @Transactional
    public void deleteKioskMenuImage(String imageUrl, MenuImageArgs args) {
        menuImageRepository.deleteBySiteCdAndStrCdAndFileNm(args.getSiteCd(), args.getStoreCd(), args.getNewFileName());
        delete(imageUrl);
    }

    public void delete(String fileUrl) {
        try {
            s3Client.delete(fileUrl);
        } catch (SdkClientException e) {
            throw new CCloudCommunicationException();
        }
    }

    public void deleteFiles(List<String> fileNames) {
        try {
            s3Client.deleteFiles(fileNames);
        } catch (SdkClientException e) {
            throw new CCloudCommunicationException();
        }
    }

    //기존 확장자명 유지한 채 유니크한 파일의 이름 생성
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    //파일 확장자명 가져옴
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new CInvalidFileFormatException();
        }
    }
}
