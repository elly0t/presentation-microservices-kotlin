package com.oursky.presentation.microservices.kotlin.itemListing.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column

@Entity
data class Item(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,

        @Column(nullable = false, unique = false, length = 256)
        val name: String = "",

        @Column(nullable = true, unique = false, length = 256)
        val description: String = "",

        @Column(nullable = false)
        val price: Float = 0f,

//        @Column(nullable = false)
//        val stock: ULong = 0,

        val enabled: Boolean = true
)