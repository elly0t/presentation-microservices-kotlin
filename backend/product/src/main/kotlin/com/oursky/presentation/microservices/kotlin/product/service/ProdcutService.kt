package com.oursky.presentation.microservices.kotlin.product.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.DataIntegrityViolationException
import com.oursky.presentation.microservices.kotlin.product.entity.Product
import com.oursky.presentation.microservices.kotlin.product.repository.ProductRepository
import org.springframework.web.multipart.MultipartFile
import io.minio.MinioClient
import io.minio.errors.MinioException
import io.minio.ServerSideEncryption
import java.security.MessageDigest
import kotlin.reflect.jvm.internal.impl.builtins.DefaultBuiltIns.getInstance
import javax.crypto.KeyGenerator
import java.net.URL
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

@Service
class ProductService {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var repository: ProductRepository

    var minioClient: MinioClient

    constructor(){
        minioClient = MinioClient(URL("http", "172.18.0.1", 9000, ""), "minioaccesskey", "miniosecretkey")
        if(!minioClient.bucketExists("images")) {
            minioClient.makeBucket("images")
        }
        val builder = StringBuilder()
        builder.append("{\n")
        builder.append("    \"Statement\": [\n")
        builder.append("        {\n")
        builder.append("            \"Action\": [\n")
        builder.append("                \"s3:GetBucketLocation\",\n")
        builder.append("                \"s3:ListBucket\"\n")
        builder.append("            ],\n")
        builder.append("            \"Effect\": \"Allow\",\n")
        builder.append("            \"Principal\": \"*\",\n")
        builder.append("            \"Resource\": \"arn:aws:s3:::images\"\n")
        builder.append("        },\n")
        builder.append("        {\n")
        builder.append("            \"Action\": \"s3:GetObject\",\n")
        builder.append("            \"Effect\": \"Allow\",\n")
        builder.append("            \"Principal\": \"*\",\n")
        builder.append("            \"Resource\": \"arn:aws:s3:::images/*\"\n")
        builder.append("        }\n")
        builder.append("    ],\n")
        builder.append("    \"Version\": \"2012-10-17\"\n")
        builder.append("}\n")
        minioClient.setBucketPolicy("images", builder.toString())
    }

    fun findById(id: Long): Product {
        return repository.findById(id).get()
    }

    fun deleteProduct(id: Long): Boolean {
        try{
            repository.deleteById(id)
            return true
        }catch(e: IllegalArgumentException){
            return false
        }
    }

    fun uploadImage(id: Long, image: MultipartFile): String? {
        try{
            if(!repository.existsById(id)){
                return null
            }
            val currentTimestamp = System.currentTimeMillis().toString()
            val bytes = image.getName().plus(currentTimestamp).toByteArray()
            val md = MessageDigest.getInstance("SHA-1")
            val digest = md.digest(bytes)
            val objectName = digest.fold("", { str, it -> str + "%02x".format(it) })
            val headerMap: HashMap<String,String> = hashMapOf<String,String>("Content-Type" to "application/octet-stream")
            minioClient.putObject("images", objectName, image.getInputStream(), image.getSize(), headerMap)
//            val url = minioClient.presignedPutObject("images", currentTimestamp)
//            println(url)
            var product = this.findById(id)
            product.image = objectName
            repository.save(product)
            return objectName
        }catch(e: Throwable){
            println(e.message)
            println(e.cause)
            return null
        }
    }

    fun addNewProduct(name: String, description: String, price: Float): Long? {
        try{
            val product = repository.save(Product(name = name, description = description, price = price, enabled = true))
            return product.id
        }catch(e: Throwable){
            println(e.message)
            println(e.cause)
            return null
        }
    }

    fun getAll(): MutableIterable<Product>{
        return repository.findAll()
    }
}