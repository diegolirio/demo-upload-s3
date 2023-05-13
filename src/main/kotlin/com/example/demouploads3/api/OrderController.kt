package com.example.demouploads3.api

import com.example.demouploads3.repository.OrderModel
import com.example.demouploads3.repository.OrderRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderRepository: OrderRepository
) {

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun load() {
        val list = mutableListOf<OrderModel>()
        for (i in 1..1000) {
            list.add(
                OrderModel(
                    code = UUID.randomUUID(),
                    value = BigDecimal(i),
                    type = "SELL"
                )
            )
        }
        orderRepository.saveAll(list)
    }
}