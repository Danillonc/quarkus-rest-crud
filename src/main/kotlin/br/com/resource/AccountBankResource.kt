package br.com.resource

import br.com.domain.Account
import br.com.domain.Customer
import br.com.dto.AccountBankDto
import br.com.enums.AccountTypeEnum
import br.com.response.Response
import br.com.service.AccountBankService
import br.com.service.CustomerService
import com.fasterxml.jackson.databind.util.BeanUtil
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.servers.Server
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.validation.ConstraintViolation
import javax.validation.Valid
import javax.validation.Validation
import javax.validation.Validator


/**
 * Accountbank resource layer implements REST API.
 */

@RestController
@RequestMapping("/account")
class AccountBankResource(val accountService: AccountBankService, val customerService: CustomerService) {

    @Inject
    @field: Default
    lateinit var validator: Validator

    @Operation(summary = "API")
    @PostMapping("/create")
    fun createAccount(@RequestBody accountBankDto: AccountBankDto): ResponseEntity<Response<AccountBankDto>> {
        val response: Response<AccountBankDto> = Response<AccountBankDto>()
        val result: Set<ConstraintViolation<AccountBankDto>> = this.validator.validate(accountBankDto)

        //using hibernate validator because quarkus 1.5 doesn't support BindingResult spring.
        if (!result.isEmpty()) {
            for (erro in result) response.erros.add(erro.message)
            return ResponseEntity.badRequest().body(response)
        }

        val customer: Customer? = customerService.findByCpf(accountBankDto.cpf)
        validateCustomer(customer, response)
        if (!response.erros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
        }

        var account: Account = convertToAccount(accountBankDto, customer!!)
        account = accountService.persist(account)

        response.data = convertToDto(account)
        return ResponseEntity.ok().body(response)
    }

    private fun convertToDto(account: Account): AccountBankDto = AccountBankDto(account.customer?.cpf!!)

    private fun convertToAccount(accountDto: AccountBankDto, customer: Customer): Account = Account(BigDecimal.ZERO, BigDecimal.ZERO, AccountTypeEnum.getAccountType(accountDto.accountType), customer)

    private fun validateCustomer(customer: Customer?, response: Response<AccountBankDto>) {
        if (customer == null) {
            response.erros.add("Customer not found.")
        }
    }

}