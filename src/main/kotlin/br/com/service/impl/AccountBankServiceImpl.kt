package br.com.service.impl

import br.com.domain.Account
import br.com.domain.Customer
import br.com.dto.AccountBankDto
import br.com.enums.AccountTypeEnum
import br.com.messages.ApiError
import br.com.messages.enums.Messages
import br.com.repository.AccountBankRepository
import br.com.repository.CustomerRepository
import br.com.response.Response
import br.com.service.AccountBankService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.lang.Exception
import java.math.BigDecimal

/**
 * Represents a service implementation layer containing business logic for contracts account bank.
 */
@Service
class AccountBankServiceImpl(val accountBankRepository: AccountBankRepository, val customerRepository: CustomerRepository) : AccountBankService {

    private val logger = LoggerFactory.getLogger(AccountBankServiceImpl::class.java)

    override fun persist(accountBankDto: AccountBankDto): Response<Void> {
        var response: Response<Void> = Response()
        val customer: Customer?
        var account: Account? = null
        customerRepository.findByCpf(accountBankDto.cpf)?.let {
            customer = it
            account = convertToAccount(accountBankDto, customer)
        } ?: response.addMessage(Messages.CUSTOMER_NOT_FOUND)

        response.messages.ifEmpty {
            accountBankRepository.save(account)
            response.addMessage(Messages.OK)
        }

        return response
    }

    override fun getBalance(account: Account): BigDecimal {
        var accBalance: Account? = null
        try {
            accBalance = accountBankRepository.findById(account.id).orElseThrow()
        } catch (e: Exception) {
            logger.info("Dados da conta bancária não encontrado para retorno do saldo .")
        }
        return accBalance?.balance ?: BigDecimal.ZERO
    }

    override fun getCashAccount(account: Account): Account {
        TODO("Veriricar o uso de thread pois não podem ocorrer mais de 1 saque no mesmo momento")
    }

    override fun getOverdrawn(account: Account): BigDecimal {
        TODO("Not yet implemented")
    }


    private fun convertToDto(account: Account): AccountBankDto = AccountBankDto(account.customer?.cpf!!)

    private fun convertToAccount(accountDto: AccountBankDto, customer: Customer): Account = Account(accountDto.balance, accountDto.overdrawn, AccountTypeEnum.getAccountType(accountDto.accountType), customer)

}