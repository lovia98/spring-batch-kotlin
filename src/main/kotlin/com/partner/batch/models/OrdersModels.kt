package com.partner.batch.models

import org.apache.ibatis.type.Alias

@Alias("personDto")
data class Person(
        var lastName : String?,
        var firstName : String?
) {

    constructor() :this(null, null)
}

@Alias("orderDto")
data class OrderInfo(
        var orderId: String?,
        var orderNo: String?,
        var paymentId: String?,
        var productId: String?,
        var productName: String?,
        var orderTime : Int?
){
    constructor() :this("0", "0", "0", "0", "", 0)
}

data class OrdersData( var orderList : List<OrderInfo>)