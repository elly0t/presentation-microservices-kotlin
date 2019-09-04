package com.oursky.presentation.microservices.kotlin.itemListing.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.DataIntegrityViolationException
import com.oursky.presentation.microservices.kotlin.itemListing.entity.Item
import com.oursky.presentation.microservices.kotlin.itemListing.repository.ItemListingRepository

@Service
class ItemListingService {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var repository: ItemListingRepository

    fun addNewItem(name: String, description: String, price: Float): Long? {
        try{
            val item = repository.save(Item(name = name, description = description, price = price, enabled = true))
            return item.id
        }catch(e: DataIntegrityViolationException) {
            return null
        }
    }

    fun getAll(): MutableIterable<Item>{
        return repository.findAll()
    }
}