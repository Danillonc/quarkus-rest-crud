package br.com.response

import br.com.messages.ApiError
import br.com.messages.enums.Messages
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

/**
 * Generic class to response data.
 */
class Response<T>() {

    @JsonProperty("messages")
    var messages: ArrayList<ApiError> = arrayListOf()
    @JsonIgnore
    var status: HttpStatus? = null
    var data: T? = null

    /**default constructor */
    constructor(messages: ArrayList<ApiError>,status: HttpStatus?,data: T?): this(){
        this.messages = messages
        this.status = status
        this.data = data
    }


    fun addMessage(message: Messages) {
        addMessage(message.status.value(), message.message)
        this.status = message.status
    }

    fun addMessage(code: Int, message: String){
        this.messages.add(ApiError(code.toString(), message))
    }
}