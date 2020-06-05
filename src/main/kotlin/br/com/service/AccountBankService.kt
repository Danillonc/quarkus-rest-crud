package br.com.service

import br.com.domain.Account
import java.math.BigDecimal

/**
 * Interface defines account bank contract operations.
 */
interface AccountBankService {

    fun persist(account: Account): Account

    fun getBalance(account: Account): BigDecimal

    fun getCashAccount(account: Account): Account

    fun getOverdrawn(account: Account): BigDecimal
}