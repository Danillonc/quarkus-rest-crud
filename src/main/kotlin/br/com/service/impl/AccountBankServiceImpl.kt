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
            logger.error("Error to get info for account  => :" + e.message)
        }
        return response
    }

    override fun getCashAccount(accountNumber: Int, branchNumber: Int, cash: BigDecimal): Response<Void> {
        var response = Response<Void>()
        var newAccount: Account? = null
        try {
            accountBankRepository.findByAccountNumberAndBranchNumber(accountNumber, branchNumber)?.let {
                newAccount = verifyAccountFunds(it, cash, response)
            } ?: response.addMessage(Messages.ACCOUNT_NOT_FOUND)

            if (response.messages.isEmpty()) {
                accountBankRepository.save(newAccount)
                response.addMessage(Messages.OK)
            }
        } catch (e: Exception) {
            logger.error("Error to get cash from account => :" + e.message)
        }
        return response
    }

    override fun sendCash(accountNumber: Int, branchNumber: Int, cash: BigDecimal): Response<Void> {
        var response = Response<Void>()
        var newAccount: Account? = null
        try {
            accountBankRepository.findByAccountNumberAndBranchNumber(accountNumber, branchNumber)?.let {
                newAccount = depositCashAccount(it, cash)
            } ?: response.addMessage(Messages.ACCOUNT_NOT_FOUND)

            if (response.messages.isEmpty()) {
                accountBankRepository.save(newAccount)
                response.addMessage(Messages.OK)
            }
        } catch (e: Exception) {
            logger.error("Error to send cash to account => :" + e.message)
        }
        return response
    }

    /**
     * Function responsivle to verify funds in account to get a cash.
     */
    private fun verifyAccountFunds(account: Account, cash: BigDecimal, response: Response<Void>): Account {
        val newBalance: BigDecimal?
        val negativeBalance: BigDecimal?
        if (account.balance?.compareTo(BigDecimal.ZERO) == 1 && (account.balance?.compareTo(cash) == 1 || account.balance?.compareTo(cash) == 0)) {
            newBalance = account.balance?.subtract(cash)
            account.balance = newBalance
        } else if (account.overdrawn?.compareTo(BigDecimal.ZERO) == 1 && (account.overdrawn?.compareTo(cash) == 1 || account.overdrawn?.compareTo(cash) == 0)) {
            newBalance = account.overdrawn?.subtract(cash)
            negativeBalance = account.balance?.subtract(cash)
            account.overdrawn = newBalance
            account.balance = negativeBalance
        } else {
            response.addMessage(Messages.INSUFFICIENT_FUNDS)
        }
        return account
    }

    private fun depositCashAccount(account: Account, cash: BigDecimal): Account {
        val newBalance: BigDecimal?
        val newOverdrawn: BigDecimal?
        if (account.balance?.compareTo(BigDecimal.ZERO) == 0 || account.balance?.compareTo(BigDecimal.ZERO) == 1) {
            account.balance = cash
        } else if (account.balance?.compareTo(BigDecimal.ZERO) == -1) {
            newBalance = account.balance?.plus(cash)
            newOverdrawn = account.overdrawn?.minus(cash)
            account.balance = newBalance
            account.overdrawn = newOverdrawn
        }
        return account
    }

    private fun convertToDto(account: Account): AccountBankDto = AccountBankDto(account.type.name, account.balance!!, account.overdrawn!!, account.accountNumber, account.branchNumber)

    private fun convertToAccount(accountDto: AccountBankDto, customer: Customer): Account = Account(accountDto.accountNumber, accountDto.branchNumber, accountDto.balance, accountDto.overdrawn, AccountTypeEnum.getAccountType(accountDto.accountType), customer)

}