package br.com.response

/**
 * Generic class to response data.
 */
data class Response<T> (
     val erros: ArrayList<String> = arrayListOf(),
     var data: T? = null
)