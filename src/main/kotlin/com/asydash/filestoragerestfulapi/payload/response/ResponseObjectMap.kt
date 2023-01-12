package com.asydash.filestoragerestfulapi.payload.response

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ResponseObjectMap {
    @Autowired
    lateinit var responseObject: ResponseObject
    fun responseCodeWithMessage(code: Int?, message: String): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        response["response"] = ResponseObject(code, message)
        return response
    }

    fun responseObj(obj: Any?, totalElement: Long): MutableMap<String, Any>{
        val response: MutableMap<String, Any> = HashMap()
        if(obj != null){
            response["response"] = responseObject.success()
            response["result"] = obj
            response["length"] = totalElement
        }else{
            response["response"] = responseObject.notFound()
        }
        return response
    }

    fun responseObj(obj: Any?): MutableMap<String, Any>{
        val response: MutableMap<String, Any> = HashMap()
        if(obj != null){
            response["response"] = responseObject.success()
            response["result"] = obj
        }else{
            response["response"] = responseObject.notFound()
        }
        return response
    }
}