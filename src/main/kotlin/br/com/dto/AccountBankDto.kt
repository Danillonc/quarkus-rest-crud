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

        @get:NotEmpty(message = "Cpf not be empty.")
        @get:CPF(message = "Cpf invalid.")
        val cpf: String = ""

)