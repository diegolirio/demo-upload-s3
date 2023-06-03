package com.example.demouploads3.api

//import com.example.demouploads3.repository.OrderModel
//import com.example.demouploads3.repository.OrderRepository
import com.example.demouploads3.s3.AwsS3Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.*

@RestController
@RequestMapping("/upload")
class GenerateFileS3Controller(
    private val awsS3Service: AwsS3Service,
//    private val orderRepository: OrderRepository
) {

    @GetMapping
    fun apply() : DownloadFile {

        val fileName = "orders-ddamacena"
        val basePathDir = "/home/diegolirio/Documents" //"/Users/ddamacena/Documents"
        val file = createTxtFile(fileName, basePathDir)
        val fr = FileWriter(file, true)

//        val count = orderRepository.count()
//        val size = 100
//        val pages = count / size
//
//        for (i in 0..pages) {
//            orderRepository
//                .findAll(PageRequest.of(0, size, getSort("id", "ASC")))
//                .let { order ->
//                    order.content.forEach {
//                        fr.write(it.id.toString())
//                        fr.write("\n")
//                    }
//                }
//        }
        fr.close()
        val fileInfo = awsS3Service.upload(file.name, file.absolutePath)
        return DownloadFile(url = fileInfo.fileUrl)
    }

    fun createTxtFile(name: String, baseDirPath: String): File {
        val fileName = "${baseDirPath}/$name-${LocalDateTime.now().toString().replace(Regex("[\\:\\.]"), "")}.csv"
        println(fileName)
        return File(fileName)
    }

    fun getSort(sortBy: String, sortDirection: String) =
        if(sortDirection.equals(Sort.Direction.ASC.name, false)) Sort.by(sortBy).ascending() else
            Sort.by(sortBy).descending()

//    fun writeTxtFile(fileName: String, lines: List<OrderModel>) {
//        val writer = BufferedWriter(FileWriter(fileName, true))
//        lines.map {
//            writer.append(it.id.toString()+";")
//            writer.append("\n")
//        }
//        writer.close()
//    }
}

data class DownloadFile(
    val url: String
)
