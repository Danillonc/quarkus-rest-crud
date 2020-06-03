package br.com.domain

import br.com.enums.AccountTypeEnum
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Represents account bank entity.
 */
@Entity
data class Account (
    val balance: BigDecimal? = BigDecimal.ZERO,
    val overdrawn: BigDecimal? = BigDecimal.ZERO,
    val type: AccountTypeEnum,
    val customer: Customer,
    @Id val id: Long? = null
)