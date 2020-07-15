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

    override fun persist(cpf: String, accountType: String): Response<Void> {
        var response = Response<Void>()
        var account: Account? = null

        try {
            customerRepository.findByCpf(cpf)?.let {
                account = if (AccountTypeEnum.isPF(accountType)) AccountTypeEnum.PF.accountType(it) else AccountTypeEnum.PJ.accountType(it)
            } ?: response.addMessage(Messages.CUSTOMER_NOT_FOUND)

            response.messages.ifEmpty {
                accountBankRepository.save(account)
                response.addMessage(Messages.OK)
            }
        } catch (e: Exception) {
            logger.error("Error to persist a account => :" + e.message)
        }

        return response
    }


    override fun getAccountInfo(accountNumber: Int, branchNumber: Int): Response<AccountBankDto> {
        var response = Response<AccountBankDto>()
        try {
            accountBankRepository.findByAccountNumberAndBranchNumber(accountNumber, branchNumber)?.let {
                response.data = convertToDto(it)
                response.addMessage(Messages.ACCOUNT_INFO_OK)
            } ?: response.addMessage(Messages.ACCOUNT_NOT_FOUND)
        } catch (e: Exception) {
            logger.info("Error to get balance for account param => :" + e.message)
        }
        return response
    }

    override fun getCashAccount(account: Account): Response<BigDecimal> {
        TODO("Veriricar o uso de thread pois não podem ocorrer mais de 1 saque no mesmo momento")
    }

    override fun getOverdrawn(account: Account): Response<BigDecimal> {
        TODO("Not yet implemented")
    }


    private fun convertToDto(account: Account): AccountBankDto = AccountBankDto(account.type.name, account.balance!!, account.overdrawn!!, account.accountNumber, account.branchNumber)

    private fun convertToAccount(accountDto: AccountBankDto, customer: Customer): Account = Account(accountDto.accountNumber, accountDto.branchNumber, accountDto.balance, accountDto.overdrawn, AccountTypeEnum.getAccountType(accountDto.accountType), customer)

}