package br.com.dto

import br.com.validation.EnumValidator
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotEmpty

/**
 * Class represents data tranfer object beteween layers.
 */
data class AccountBankDto(

        @get:Length(max = 14, message = "cpf length is invalid!")
        @get:NotEmpty(message = "cpf not be empty.")
        @get:CPF(message = "cpf invalid.")
        val cpf: String = "",

        @get:Length(max = 2, message = "accountType length is invalid!")
        @get:NotEmpty(message = "accountType not be empty.")
        @EnumValidator(message = "accountType is invalid!")
        val accountType: String = "",

        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer=3, fraction=2)
        val balance: BigDecimal = BigDecimal.ZERO,

        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer=3, fraction=2)
        val overdrawn: BigDecimal = BigDecimal.ZERO

)