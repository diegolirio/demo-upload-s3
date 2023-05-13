package com.example.demouploads3.s3

data class FileInfo(
    var id: Long? = null,
    val fileName: String,
    val fileUrl: String,
    val isUploadSuccessFull: Boolean
)