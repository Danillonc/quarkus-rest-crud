package br.com.service

import br.com.domain.Account
import br.com.dto.AccountBankDto
import br.com.response.Response
import java.math.BigDecimal

/**
 * Interface defines account bank contract operations.
 */
interface AccountBankService {

    fun persist(accountBankDto: AccountBankDto): Response<Void>

    fun getBalance(account: Account): BigDecimal

    fun getCashAccount(account: Account): Account

    fun getOverdrawn(account: Account): BigDecimal
}