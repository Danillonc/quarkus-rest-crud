package br.com.service

import br.com.domain.Account

/**
 * Interface defines account bank contract operations.
 */
interface AccountBankService {

    fun persist(account: Account): Account

    fun getBalance(account: Account): Double

    fun getCashAccount(account: Account): Account

    fun getOverdrawn(account: Account): Double
}