package com.byneuron.azure.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.objectstorage.azure.AzureBlobStorageOperations;
import io.micronaut.objectstorage.request.UploadRequest;
import io.micronaut.objectstorage.response.UploadResponse;

@Controller("/upload")
public class UploadController {

    private final AzureBlobStorageOperations objectStorage;

    public UploadController(AzureBlobStorageOperations objectStorage) {
        this.objectStorage = objectStorage;
    }

    @Post(consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> upload(CompletedFileUpload fileUpload) {
        UploadRequest objectStorageUpload = UploadRequest.fromCompletedFileUpload(fileUpload);

        UploadResponse<?> response = objectStorage.upload(objectStorageUpload, builder -> {

        });

        return HttpResponse
            .created(response.getKey())
            .header("ETag", response.getNativeResponse().toString());
    }

}

