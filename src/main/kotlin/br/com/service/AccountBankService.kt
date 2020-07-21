package br.com.service

import br.com.domain.Account
import br.com.dto.AccountBankDto
import br.com.response.Response
import java.math.BigDecimal

/**
 * Interface defines account bank contract operations.
 */
interface AccountBankService {

    /**
     * Function responsible to create an account.
     * @param cpf - Unique customer identifier.
     * @param accountType - Type of account to create.
     */
    fun persist(cpf: String, accountType: String): Response<Void>

    /**
     * Function responsible do get info from account by params.
     * @param accountNumber - Number of account
     * @param branchNumber - Branch number of account.
     */
    fun getAccountInfo(accountNumber: Int, branchNumber: Int): Response<AccountBankDto>

    /**
     * Function responsible to get specified cash by param.
     * @param accountNumber - Number of account.
     * @param branchNumber - Branch number of account.
     * @param cash - Specified value to get from account.
     */
    fun getCashAccount(accountNumber: Int, branchNumber: Int, cash: BigDecimal): Response<Void>

    /**
     * Function responsible to get specified cash by param.
     * @param accountNumber - Number of account.
     * @param branchNumber - Branch number of account.
     * @param cash - Specified value to send to account.
     */
    fun sendCash(accountNumber: Int, branchNumber: Int, cash: BigDecimal): Response<Void>
}