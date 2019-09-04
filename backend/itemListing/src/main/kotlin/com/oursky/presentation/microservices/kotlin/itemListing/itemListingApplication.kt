package com.oursky.presentation.microservices.kotlin.itemListing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItemListingApplication

fun main(args: Array<String>) {
    runApplication<ItemListingApplication>(*args)
}
