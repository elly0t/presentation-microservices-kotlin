package com.oursky.presentation.microservices.kotlin.product.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.DataIntegrityViolationException
import com.oursky.presentation.microservices.kotlin.product.entity.Product
import com.oursky.presentation.microservices.kotlin.product.repository.ProductRepository

@Service
class ProductService {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var repository: ProductRepository

    fun addNewProduct(name: String, description: String, price: Float): Long? {
        try{
            val product = repository.save(Product(name = name, description = description, price = price, enabled = true))
            return product.id
        }catch(e: DataIntegrityViolationException) {
            return null
        }
    }

    fun getAll(): MutableIterable<Product>{
        return repository.findAll()
    }
}