package com.partner.batch.service

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.partner.batch.models.OrdersData
import com.partner.batch.utils.readFileDirectlyAsText
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

interface OrdersService {

    fun ordersList(date : String): OrdersData
}

@Service
class OrdersServiceImpl : OrdersService {

    @Value("\${export.filePath}")
    private lateinit var filePath : String

    override fun ordersList(date: String): OrdersData {

        try {

            val filePath = getFilePath(date)
            val fileJsonText = readFileDirectlyAsText(filePath)

            val gson = Gson()

            return gson.fromJson(fileJsonText, OrdersData::class.java)

        } catch (e: Exception) {
            throw JsonParseException("Json Processing Exception")
        }
    }



    private fun getFilePath(date: String) : String {

        val dateArry = date.split("-")
        val year = dateArry[0].substring(0,4)
        val month = dateArry[0].substring(4,6)
        val dayTime = dateArry[0].substring(6,8) + dateArry[1]

        val batchFilePath = "$filePath/${year}/${month}/${dayTime}.json"

        return batchFilePath
    }
}