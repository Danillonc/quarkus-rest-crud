package br.com.dto

import br.com.validation.EnumValidator
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotEmpty

data class AccountDto (

    @get:Length(max = 14, message = "cpf length is invalid!")
    @get:NotEmpty(message = "cpf not be empty.")
    @get:CPF(message = "cpf invalid.")
    val cpf: String = "",

    @get:Length(max = 2, message = "accountType length is invalid!")
    @get:NotEmpty(message = "accountType not be empty.")
    @EnumValidator(message = "accountType is invalid!")
    val accountType: String = ""
)