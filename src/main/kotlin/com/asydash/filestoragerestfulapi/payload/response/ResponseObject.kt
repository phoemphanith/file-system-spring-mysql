package com.asydash.filestoragerestfulapi.payload.response

import org.springframework.stereotype.Component

@Component
class ResponseObject(var status: Int ?= 0, var message: String ?= null) {
    fun success() = ResponseObject(200, "Success")
    fun notFound() = ResponseObject(404, "Error object not found")
}