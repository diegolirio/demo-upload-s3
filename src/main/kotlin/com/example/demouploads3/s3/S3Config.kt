package com.example.demouploads3.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
data class S3Config(
    @Value("\${config.aws.region}")
    val region: String,
    @Value("\${config.aws.s3.url}")
    val s3EndpointUrl: String,
    @Value("\${config.aws.s3.bucket-name}")
    val bucketName: String,
    @Value("\${config.aws.s3.access-key}")
    val accessKey: String,
    @Value("\${config.aws.s3.secret-key}")
    val secretKey: String
) {

//    fun credentials(): AWSCredentials {
//        return BasicAWSCredentials(
//            "accesskey",
//            "secretkey"
//        )
//    }
//
//    @Bean
//    fun amazonS3(): AmazonS3 {
//        return AmazonS3ClientBuilder
//            .standard()
//            .withCredentials(AWSStaticCredentialsProvider(credentials()))
//            .withRegion(Regions.US_EAST_1)
//            .build()
//    }

    @Bean(name = ["amazonS3"])
    fun amazonS3(): AmazonS3? {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(getCredentialsProvider())
            .withEndpointConfiguration(getEndpointConfiguration(s3EndpointUrl))
            .build()
    }

    private fun getEndpointConfiguration(url: String?): EndpointConfiguration? {
        return EndpointConfiguration(url, region)
    }

    private fun getCredentialsProvider(): AWSStaticCredentialsProvider? {
        return AWSStaticCredentialsProvider(getBasicAWSCredentials())
    }

    private fun getBasicAWSCredentials(): BasicAWSCredentials? {
        return BasicAWSCredentials(accessKey, secretKey)
    }

}