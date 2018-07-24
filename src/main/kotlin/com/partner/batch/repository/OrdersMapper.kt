package com.partner.batch.repository

import com.partner.batch.dbConfig.OrdersDB
import com.partner.batch.models.OrderInfo
import org.apache.ibatis.annotations.Mapper

@OrdersDB
@Mapper
interface OrdersMapper {

    fun findOrders(): List<OrderInfo>
}