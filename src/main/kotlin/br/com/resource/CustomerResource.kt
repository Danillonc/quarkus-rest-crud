package br.com.resource

import br.com.domain.Customer
import br.com.dto.AccountBankDto
import br.com.dto.CustomerDto
import br.com.response.Response
import br.com.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun createCustomer(@RequestBody customerDto: CustomerDto): ResponseEntity<Response<CustomerDto>> {
        val response: Response<CustomerDto> = Response<CustomerDto>()
        val result: Set<ConstraintViolation<CustomerDto>> = this.validator.validate(customerDto)

        //using hibernate validator because quarkus 1.5 doesn't support BindingResult spring.
        if (!result.isEmpty()) {
            for (erro in result) response.erros.add(erro.message)
            return ResponseEntity.badRequest().body(response)
        }

        var customer: Customer = Customer().convertToEntity(customerDto)
        customer = customerService.persist(customer)

        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/{cpf}")
    fun getCustomer(@PathVariable cpf: String): ResponseEntity<Response<CustomerDto>> {
        val response: Response<CustomerDto> = Response<CustomerDto>()
        var customer: Customer? = customerService.findByCpf(cpf)
        response.data = CustomerDto().convertToDto(customer)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/all")
    fun getAllCustomers(): ResponseEntity<Response<List<CustomerDto>>> {
        val response: Response<List<CustomerDto>> = Response<List<CustomerDto>>()
        val customers: List<CustomerDto>? = customerService.findAll()?.map { CustomerDto(it.name, it.cpf, it.email, it.surname, it.birthday, it.account) }
        response.data = customers
        return ResponseEntity.ok().body(response)
    }

    /**Extension function to convert entity to DTO **/
    fun CustomerDto.convertToDto(customer: Customer?) = CustomerDto(name = customer?.name!!, cpf = customer.cpf, email = customer.email, surname = customer.surname, birthday = customer.birthday, accounts = customer.account)

    /**Extension function to convert DTO to entity **/
    fun Customer.convertToEntity(customerDto: CustomerDto) = Customer(name = customerDto.name, cpf = customerDto.cpf, email = customerDto.email, surname = customerDto.surname, birthday = customerDto.birthday)

}