package br.com.domain

import br.com.enums.AccountTypeEnum
import java.math.BigDecimal
import javax.persistence.*

/**
 * Represents account bank entity.
 */
@Entity
data class Account (
        val balance: BigDecimal? = BigDecimal.ZERO,
        val overdrawn: BigDecimal? = BigDecimal.ZERO,
        @Enumerated(EnumType.STRING) val type: AccountTypeEnum,
        @ManyToOne val customer: Customer,
        @Id val id: Long? = null
)