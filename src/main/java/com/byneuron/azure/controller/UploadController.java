package com.byneuron.azure.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;

@Controller("/upload")
public class UploadController {

	Logger oLogger = LoggerFactory.getLogger(this.getClass());

    @Post(consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> upload(CompletedFileUpload fileUpload) {

        oLogger.info("test upload method");
        return HttpResponse.ok();
    }

}

