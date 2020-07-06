package br.com.resource

import br.com.dto.CustomerDto
import br.com.response.Response
import br.com.service.CustomerService
import org.springframework.http.HttpStatus
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
        var response: Response<CustomerDto> = Response()
        val result: Set<ConstraintViolation<CustomerDto>> = this.validator.validate(customerDto)

        //using hibernate validator because quarkus 1.5 doesn't support BindingResult spring.
        if (!result.isEmpty()) {
            for (erro in result) response.addMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), erro.message)
            return ResponseEntity.badRequest().body(response)
        }

        response = customerService.persist(customerDto)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/{cpf}")
    fun getCustomer(@PathVariable cpf: String): ResponseEntity<Response<CustomerDto>> {
        var response: Response<CustomerDto> = Response()
        response = customerService.findByCpf(cpf)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/all")
    fun getAllCustomers(): ResponseEntity<Response<List<CustomerDto>>> {
        var response: Response<List<CustomerDto>> = Response()
        response = customerService.findAll()
        return ResponseEntity.ok().body(response)
    }

    @PutMapping("/update/{cpf}")
    fun updateCustomer(@PathVariable cpf: String, @RequestBody customerDto: CustomerDto): ResponseEntity<Response<CustomerDto>> {
        var response: Response<CustomerDto> = Response()
        val result: Set<ConstraintViolation<CustomerDto>> = this.validator.validate(customerDto)

        //using hibernate validator because quarkus 1.5 doesn't support BindingResult spring.
        if (result.isNotEmpty()) {
            for (erro in result) response.addMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), erro.message)
            return ResponseEntity.badRequest().body(response)
        }

        response = customerService.update(cpf, customerDto)
        return ResponseEntity.ok().body(response)
    }


}