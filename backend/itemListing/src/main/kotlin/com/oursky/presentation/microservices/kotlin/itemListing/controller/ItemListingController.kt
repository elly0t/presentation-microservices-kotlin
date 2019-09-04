package com.oursky.presentation.microservices.kotlin.itemListing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.oursky.presentation.microservices.kotlin.itemListing.service.ItemListingService
import com.oursky.presentation.microservices.kotlin.itemListing.entity.Item

@RestController
@RequestMapping("/item")
public class ItemListingController {
    @Autowired
    lateinit var itemListingService: ItemListingService

    data class AddItemRequest(
            val name: String,
            val description: String,
            val price: Float
    )

    data class AddItemResponse(
            val itemId: Long
    )

    data class AllItemResponse(
            var items: MutableIterable<Item>
    )

    @PostMapping("/add")
    fun add(
            @RequestBody body: AddItemRequest
    ): ResponseEntity<AddItemResponse> {
        val itemId = itemListingService.addNewItem(body.name, body.description, body.price)
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(AddItemResponse(
                itemId = itemId
        ))
    }

    @GetMapping("/all")
    fun getAllItemListing(): ResponseEntity<AllItemResponse>{
        val data = itemListingService.getAll()
        return ResponseEntity.ok(AllItemResponse(
                items = data
        ))
    }
}
