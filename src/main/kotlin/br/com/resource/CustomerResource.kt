package br.com.resource

import br.com.domain.Customer
import br.com.dto.AccountBankDto
import br.com.dto.CustomerDto
import br.com.response.Response
import br.com.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.validation.ConstraintViolation
import javax.validation.Validator

/**
 * Customer resource layer implements REST API.
 */

@RestController
@RequestMapping("/customer")
class CustomerResource(val customerService: CustomerService) {

    @Inject
    @field: Default
    lateinit var validator: Validator

    @PostMapping("/create")
    fun createCustomer(@RequestBody customerDto: CustomerDto): ResponseEntity<Response<CustomerDto>>{
        val response: Response<CustomerDto> = Response<CustomerDto>()
        val result: Set<ConstraintViolation<CustomerDto>> = this.validator.validate(customerDto)

        //using hibernate validator because quarkus 1.5 doesn't support BindingResult spring.
        if(!result.isEmpty()){
            for(erro in result) response.erros.add(erro.message)
            return ResponseEntity.badRequest().body(response)
        }

        var customer: Customer = convertoToCustomer(customerDto)
        customer = customerService.persist(customer)

        return ResponseEntity.ok().body(response)
    }

    private fun convertoToCustomer(customerDto: CustomerDto): Customer = Customer(customerDto.name, customerDto.cpf, customerDto.email, customerDto.surname, customerDto.birthday)
}