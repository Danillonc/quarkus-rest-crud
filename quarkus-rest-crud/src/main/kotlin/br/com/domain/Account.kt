package br.com.domain

import br.com.enums.AccountTypeEnum
import javax.persistence.Entity

/**
 * Represents account bank entity.
 */
@Entity
data class Account (
    val balance: Double? = 0.00,
    val overdrawn: Double? = 0.00,
    val type: AccountTypeEnum,
    val customer: Customer
)