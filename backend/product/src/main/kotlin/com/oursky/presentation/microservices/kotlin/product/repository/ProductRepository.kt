package com.oursky.presentation.microservices.kotlin.product.repository

import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository
import com.oursky.presentation.microservices.kotlin.product.entity.Product

@Repository
interface ProductRepository : CrudRepository <Product, Long> {
}