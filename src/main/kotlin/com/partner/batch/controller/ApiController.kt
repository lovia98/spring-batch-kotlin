package com.partner.batch.controller

import com.partner.batch.models.ApiFormatter
import com.partner.batch.models.OrdersData
import com.partner.batch.service.OrdersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PartnerApiController {

    @Autowired
    lateinit var ordersService: OrdersService


    @GetMapping("/order/search/{day}/{hour}")
    fun ordersList(
            @PathVariable(name="day") day : String,
            @PathVariable(name="hour") hour : String
    ) : ApiFormatter<OrdersData> {
        return ApiFormatter<OrdersData>(ordersService.ordersList("$day-$hour"))
    }

    @ExceptionHandler(value = Exception::class)
    fun <T> exceptionHandle(e: Exception) : ApiFormatter<T>{

        return ApiFormatter<T>().exception(e)
    }
}