package com.oursky.presentation.microservices.kotlin.itemListing.repository

import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository
import com.oursky.presentation.microservices.kotlin.itemListing.entity.Item

@Repository
interface ItemListingRepository : CrudRepository <Item, Long> {
}