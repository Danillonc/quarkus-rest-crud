package br.com.resource

import br.com.dto.AccountBankDto
import br.com.dto.AccountDto
import br.com.response.Response
import br.com.service.AccountBankService
import br.com.service.CustomerService
import org.eclipse.microprofile.openapi.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.validation.ConstraintViolation
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


    @PostMapping("/create")
    fun createAccount(@RequestBody accountDto: AccountDto): ResponseEntity<Response<Void>> {
        var response = Response<Void>()
        val result: Set<ConstraintViolation<AccountDto>> = this.validator.validate(accountDto)

        //using hibernate validator because quarkus 1.5 doesn't support BindingResult spring.
        if (!result.isEmpty()) {
            for (erro in result) response.addMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), erro.message)
            return ResponseEntity.badRequest().body(response)
        }

        response = accountService.persist(accountDto.cpf, accountDto.accountType)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/info/{accountNumber}/{branchNumber}")
    fun AccountInfo(@PathVariable accountNumber: Int, @PathVariable branchNumber: Int): ResponseEntity<Response<AccountBankDto>> {
        var response: Response<AccountBankDto> = accountService.getAccountInfo(accountNumber, branchNumber)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/cash/{accountNumber}/{branchNumber}/{value}")
    fun getCash() {
    }


}