package br.com.dto

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * Class represents data tranfer object beteween layers.
 */
data class AccountBankDto (
        @get:NotEmpty(message = "Name not be empty.")
        @get:Length(min = 3, max = 30, message = "The name need beteween 3 and 30 characteres.")
        val name: String = "",
        @get:NotEmpty(message = "Email not be empty.")
        @get:Length(min = 5, max = 200, message = "Email need beteween 5 and 200 characteres.")
        @get:Email(message = "Email invalid.")
        val email: String = "",
        @get:NotEmpty(message = "Cpf not be empty.")
        @get:CPF(message = "Cpf invalid.")
        val cpf: String? = "",

        val surname: String? = null,
        val birthday: String? = null
)