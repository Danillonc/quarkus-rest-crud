package br.com.service

import br.com.domain.Account
import br.com.dto.AccountBankDto
import br.com.response.Response
import java.math.BigDecimal

/**
 * Interface defines account bank contract operations.
 */
interface AccountBankService {

    fun persist(cpf: String, accountType: String): Response<Void>

    fun getAccountInfo(accountNumber: Int, branchNumber: Int): Response<AccountBankDto>

    fun getCashAccount(accountNumber: Int, branchNumber: Int, cash: BigDecimal): Response<Void>

    fun getOverdrawn(account: Account): Response<BigDecimal>
}