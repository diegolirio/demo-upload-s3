package com.example.demouploads3.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name="order")
data class OrderModel(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var code: UUID? = null,
    var value: BigDecimal? = null,
    var type: String? = null
)