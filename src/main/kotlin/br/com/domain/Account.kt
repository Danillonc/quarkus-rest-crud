package br.com.domain

import br.com.enums.AccountTypeEnum
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import javax.persistence.*

/**
 * Represents account bank entity.
 */
@Entity
data class Account(

        var accountNumber: Int,

        var branchNumber: Int,

        var balance: BigDecimal? = BigDecimal.ZERO,

        var overdrawn: BigDecimal? = BigDecimal.ZERO,

        @Enumerated(EnumType.STRING)
        var type: AccountTypeEnum,

        @JsonBackReference
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "customer_id")
        var customer: Customer? = null,

        @JsonIgnore
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0

)