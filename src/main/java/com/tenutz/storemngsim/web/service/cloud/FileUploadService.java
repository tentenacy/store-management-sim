package com.tenutz.storemngsim.web.service.cloud;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tenutz.storemngsim.domain.menu.MenuImage;
import com.tenutz.storemngsim.domain.menu.MenuImageRepository;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.MenuImageArgs;
import com.tenutz.storemngsim.web.client.UploadClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static com.tenutz.storemngsim.web.exception.io.CIOException.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileUploadService {

    private final MenuImageRepository menuImageRepository;
    private final UploadClient s3Client;
    private final StoreMasterRepository storeMasterRepository;

    @Transactional
    public String uploadKioskMenuImage(MenuImageArgs args) {

        final int EQU_TYPE = 4;

        String newFileName = createFileName(args.getFileToUpload().getOriginalFilename());
        String filePath = "FILE_MANAGER/" + args.getSiteCd() + "/" + args.getStoreCd() + "/" + EQU_TYPE;
        menuImageRepository.save(
                MenuImage.createKioskMenu(
                        args.getSiteCd(),
                        args.getStoreCd(),
                        newFileName,
                        null,
                        (int) args.getFileToUpload().getSize(),
                        "smartcast-img/" + filePath,
                        1
                )
        );
        args.setNewFileName(newFileName);
        return upload(args.getFileToUpload(), filePath + "/" + newFileName);
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
        menuImageRepository.deleteBySiteCdAndStrCdAndEquTypeAndFileNm(args.getSiteCd(), args.getStoreCd(), "4", args.getNewFileName());
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
