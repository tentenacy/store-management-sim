package com.tenutz.storemngsim.web.client;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;
import java.util.List;

public interface UploadClient {

    void upload(InputStream inputStream, ObjectMetadata objectMetadata, String filePath);

    String getFileUrl(String fileName);

    void deleteFiles(List<String> fileNames);

    void delete(String fileName);

    boolean doesFileExists(String filaName);
}
