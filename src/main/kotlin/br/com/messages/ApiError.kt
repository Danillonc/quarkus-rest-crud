package br.com.messages

import org.springframework.http.HttpStatus

/**
 * Class to define api errors
 */
data class ApiError (
        val code: String,
        val message: String
)