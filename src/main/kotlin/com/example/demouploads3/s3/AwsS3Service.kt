package com.example.demouploads3.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*


@Service
class AwsS3Service(
    private val amazonS3: AmazonS3,
    private val s3Config: S3Config
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun upload(fileName: String, locationFile: String): FileInfo {
        createS3BucketIfItNotExist(s3Config.bucketName, true)

        val putObject = amazonS3.putObject(
            s3Config.bucketName,
            fileName,
            File(locationFile)
        )
        val fileUrl: String = (s3Config.s3EndpointUrl + "/" + s3Config.bucketName) + "/" + fileName
        println("fileUrl=$fileUrl")
        val url = generateUrl(fileName, HttpMethod.GET)
        println("url=$url")
        return FileInfo(fileName = fileName, fileUrl=url, isUploadSuccessFull=Objects.nonNull(putObject))
    }

    private fun generateUrl(fileName: String, httpMethod: HttpMethod): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 1)
        return amazonS3.generatePresignedUrl(s3Config.bucketName, fileName, calendar.time, httpMethod).toString()
    }

    fun uploadObjectToS3(fileName: String, fileData: ByteArray): FileInfo {
        log.info("Uploading file '{}' to bucket: '{}' ", fileName, s3Config.bucketName)
        val byteArrayInputStream = ByteArrayInputStream(fileData)
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = fileData.size.toLong()

        val fileUrl: String =
            (s3Config.s3EndpointUrl + "/" + s3Config.bucketName) + "/" + fileName

        val putObjectResult: PutObjectResult = amazonS3.putObject(
            s3Config.bucketName,
            fileName,
            byteArrayInputStream,
            objectMetadata
        )
        return FileInfo(fileName = fileName, fileUrl=fileUrl, isUploadSuccessFull=Objects.nonNull(putObjectResult))
    }

    fun downloadFileFromS3Bucket(fileName: String?): S3ObjectInputStream {
        log.info("Downloading file '{}' from bucket: '{}' ", fileName, s3Config.bucketName)
        val s3Object: S3Object = amazonS3.getObject(s3Config.bucketName, fileName)
        return s3Object.objectContent
    }

    fun listObjects(): List<S3ObjectSummary> {
        log.info("Retrieving object summaries for bucket '{}'", s3Config.bucketName)
        val objectListing: ObjectListing = amazonS3.listObjects(s3Config.bucketName)
        return objectListing.objectSummaries
    }

    fun createS3BucketIfItNotExist(bucketName: String?, publicBucket: Boolean) {
        if (amazonS3.doesBucketExistV2(bucketName)) {
            log.info("Bucket name already in use. Try another name.")
            return
        }
        if (publicBucket) {
            amazonS3.createBucket(
                CreateBucketRequest(bucketName).withCannedAcl(CannedAccessControlList.PublicReadWrite)
            )
        } else {
            amazonS3.createBucket(
                CreateBucketRequest(bucketName).withCannedAcl(CannedAccessControlList.Private)
            )
        }
    }
}