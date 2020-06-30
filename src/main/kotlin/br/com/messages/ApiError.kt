package br.com.messages

import org.springframework.http.HttpStatus

/**
 * Class to define api errors
 */
data class ApiError (
        val status: HttpStatus,
        val message: String,
        val errors: List<String> = emptyList()
)