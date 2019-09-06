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

@Service
class ProductService {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var repository: ProductRepository

    var minioClient: MinioClient

    constructor(){
        // minioClient = MinioClient("127.0.0.1", 9000, "minioaccesskey", "miniosecretkey", false);
        // minioClient = MinioClient("http://127.0.0.1:9000", "minioaccesskey", "miniosecretkey")
        minioClient = MinioClient(URL("http", "172.18.0.1", 9000, ""), "minioaccesskey", "miniosecretkey")
        if(!minioClient.bucketExists("images")) {
            minioClient.makeBucket("images")
        }
    }

    fun deleteProduct(id: Long): Boolean {
        try{
            repository.deleteById(id)
            return true
        }catch(e: IllegalArgumentException){
            return false
        }
    }

    fun addNewProduct(name: String, description: String, price: Float, image: MultipartFile): Long? {
        try{
            val currentTimestamp = System.currentTimeMillis().toString()
            val bytes = image.getName().plus(currentTimestamp).toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val objectName = digest.fold("", { str, it -> str + "%02x".format(it) })
            println(objectName)

            val headerMap: HashMap<String,String> = hashMapOf<String,String>("Content-Type" to "application/octet-stream")

            minioClient.putObject("images", objectName, image.getInputStream(), image.getSize(), headerMap)
            val product = repository.save(Product(name = name, description = description, price = price, image = objectName, enabled = true))

            return product.id

        }catch(e: DataIntegrityViolationException) {
            return null
        }
    }

    fun getAll(): MutableIterable<Product>{
        return repository.findAll()
    }
}