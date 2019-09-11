package com.oursky.presentation.microservices.kotlin.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.oursky.presentation.microservices.kotlin.product.service.ProductService
import com.oursky.presentation.microservices.kotlin.product.entity.Product
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    lateinit var productService: ProductService

    data class AddProductResponse(
            val productId: Long
    )

    data class AddImageResponse(
            val data: String
    )

    data class AllProductResponse(
            var products: MutableIterable<Product>
    )

    data class DeleteProductResponse(
            var success: Boolean
    )

    @PostMapping("/")
    fun add(
            request: MultipartHttpServletRequest,
            @RequestParam("files") image: MultipartFile,
            @RequestParam("name") name: String,
            @RequestParam("description") description: String,
            @RequestParam("price") price: Float
    ): ResponseEntity<AddProductResponse> {
        val productId = productService.addNewProduct(name, description, price, image)
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(AddProductResponse(
                productId = productId
        ))
    }

    @GetMapping("/")
    fun getAllProduct(): ResponseEntity<AllProductResponse>{
        val data = productService.getAll()
        return ResponseEntity.ok(AllProductResponse(
                products = data
        ))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
            @PathVariable id: Long
    ): ResponseEntity<DeleteProductResponse> {
        return ResponseEntity.ok(DeleteProductResponse(
                success = productService.deleteProduct(id)
        ))
    }
}
