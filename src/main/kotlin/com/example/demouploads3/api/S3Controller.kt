package com.example.demouploads3.api

import com.amazonaws.services.s3.model.S3ObjectSummary
import com.example.demouploads3.s3.AwsS3Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/s3")
class S3Controller(
    private val awsS3Service: AwsS3Service
) {

    @GetMapping
    fun getList(): List<S3ObjectSummary> {
        return awsS3Service.listObjects()
    }

}