package com.example.demouploads3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID

@RestController
@RequestMapping
class GenerateFileS3Controller {

    @GetMapping
    fun apply() : DownloadFile {
        val list = mutableListOf<Order>()
        for (i in 1..1000) {
            list.add(
                Order(
                    id = i.toLong(),
                    code = UUID.randomUUID(),
                    value = BigDecimal(i),
                    type = "SELL"
                )
            )
        }

        for(o in list) {

        }
        return DownloadFile(url = "http://localhost:9090/hahaha")
    }
}

data class DownloadFile(
    val url: String
)

data class Order(
    val id: Long,
    val code: UUID,
    val value: BigDecimal,
    val type: String
)