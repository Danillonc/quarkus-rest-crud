package br.com.enums

import br.com.domain.Account
import br.com.domain.Customer
import br.com.utils.Utils
import java.math.BigDecimal
import kotlin.random.Random

/**
 * Enum representa a account type in bank.
 */
enum class AccountTypeEnum(val type: String,val desc: String) {

    PF("PF", "PESSOA_FISICA"){
        override fun accountType(customer: Customer): Account {
            return Account(Utils.generateAccountNumber(), Utils.generateBrancNumber(), BigDecimal.ZERO, BigDecimal(2000), PF, customer)
        }
    },
    PJ("PJ","PESSOA_JURIDICA") {
        override fun accountType(customer: Customer): Account {
            return Account(Utils.generateAccountNumber(), Utils.generateBrancNumber(), BigDecimal.ZERO, BigDecimal(5000), PJ, customer)
        }
    };

    companion object {
        fun getAccountTypeByType(type: String): Boolean {
            return values().any { it.type.equals(type) }
        }
        fun getAccountType(type: String): AccountTypeEnum {
            return values().find { it.type.equals(type) }!!
        }
        fun isPF(type: String): Boolean {
            return PF.type == type
        }
    }

    abstract fun accountType(customer: Customer): Account


}