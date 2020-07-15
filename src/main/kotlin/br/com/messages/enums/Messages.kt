package br.com.messages.enums

import org.springframework.http.HttpStatus

enum class Messages(val status: HttpStatus, val message: String) {
    OK(HttpStatus.OK, "Success!"),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not found!"),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "Account not found!"),
    CPF_NOT_FOUND(HttpStatus.NOT_FOUND, "Cpf not found!"),
    ACCOUNT_INFO_OK(HttpStatus.OK, "Get account info with success!");

    companion object {
        fun getMessage(messages: Messages): String {
            return StringBuilder().append("Status: ").append(messages.status).append(" Message: ").append(messages.message).toString()
        }
    }
}