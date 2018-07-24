package com.partner.batch.batchConfig

import com.google.gson.GsonBuilder
import org.springframework.batch.item.file.transform.LineAggregator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun makeFilePath(path : String) : String {

    val current = LocalDateTime.now()

    val monthFormatter = DateTimeFormatter.ofPattern("MM")
    val fileFormatter = DateTimeFormatter.ofPattern("ddHH")

    return "$path/${current.year}/${current.format(monthFormatter)}/${current.format(fileFormatter)}.json"
}


fun <T> jsonLineAggregator() : LineAggregator<T> {

    val gson = GsonBuilder().setPrettyPrinting().create()
    var isFirstObject = true

    return LineAggregator {item->

        if(isFirstObject) {
            isFirstObject = false
            gson.toJson(item)
        }else {
            ","+gson.toJson(item)
        }

    }
}