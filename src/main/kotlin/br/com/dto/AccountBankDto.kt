package br.com.dto

import br.com.validation.EnumValidator
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal
import javax.validation.constraints.*

/**
 * Class represents data tranfer object beteween layers.
 */
data class AccountBankDto(

        val accountType: String,
        val balance: BigDecimal,
        val overdrawn: BigDecimal,
        val accountNumber: Int,
        val branchNumber: Int

)