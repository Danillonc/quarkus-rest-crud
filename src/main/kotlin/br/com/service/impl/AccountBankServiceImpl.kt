package br.com.service.impl

import br.com.domain.Account
import br.com.repository.AccountBankRepository
import br.com.service.AccountBankService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception
import java.math.BigDecimal

/**
 * Represents a service implementation layer containing business logic for contracts account bank.
 */
@Service
class AccountBankServiceImpl(val accountBankRepository: AccountBankRepository): AccountBankService {

    private val logger = LoggerFactory.getLogger(AccountBankServiceImpl::class.java)

    override fun persist(account: Account): Account = accountBankRepository.save(account)

    override fun getBalance(account: Account): BigDecimal {
        var accBalance: Account? = null
        try {
            accBalance = accountBankRepository.findById(account.id).orElseThrow()
        }catch (e: Exception){
            logger.info("Dados da conta bancária não encontrado para retorno do saldo .")
        }
        return accBalance?.balance?: BigDecimal.ZERO
    }

    override fun getCashAccount(account: Account): Account {
        TODO("Veriricar o uso de thread pois não podem ocorrer mais de 1 saque no mesmo momento")
    }

    override fun getOverdrawn(account: Account): BigDecimal {
        TODO("Not yet implemented")
    }
}