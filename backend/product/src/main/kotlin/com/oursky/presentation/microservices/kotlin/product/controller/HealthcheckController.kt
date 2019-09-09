package com.oursky.presentation.microservices.kotlin.product.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.ResponseEntity

@RestController
public class HealthcheckController {
    @GetMapping("/healthcheck")
    fun healthcheck(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}
