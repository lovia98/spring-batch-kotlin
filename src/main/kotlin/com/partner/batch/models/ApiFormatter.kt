package com.partner.batch.models

enum class APIRESULT(var code: String) {

    SUCCESS("200"),
    FAIL("999"),
    ILLEGAL_ARGUMENT_ERROR("501"),
    JSON_PARSE_ERROR("502")
}

class ApiFormatter<T>  {

    var result: APIRESULT = APIRESULT.SUCCESS
    var resultCode : String = APIRESULT.SUCCESS.code
    var data : T? = null
    var errorMsg : String? = null

    constructor()
    constructor(data: T) : this() {
        this.data = data
    }

    fun exception(e: Exception) : ApiFormatter<T> {

        val exceptionName = e::class.java.canonicalName

        this.result = APIRESULT.FAIL
        this.resultCode = when(exceptionName) {
            "IllegalArgumentException" -> APIRESULT.ILLEGAL_ARGUMENT_ERROR.code
            "JsonParseException" -> APIRESULT.JSON_PARSE_ERROR.code
            else -> APIRESULT.FAIL.code
        }
        this.errorMsg = e.message

        return this
    }
}



