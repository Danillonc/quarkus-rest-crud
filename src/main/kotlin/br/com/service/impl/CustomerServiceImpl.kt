package br.com.service.impl

import br.com.domain.Customer
import br.com.dto.CustomerDto
import br.com.messages.ApiError
import br.com.repository.CustomerRepository
import br.com.response.Response
import br.com.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.transaction.Transactional

/**
 * Represents a service implementation layer containing business logic for contracts customer.
 */
@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {


    override fun persist(customerDto: CustomerDto): Response<CustomerDto> {
        var response: Response<CustomerDto> = Response()
        val customer: Customer = Customer().convertToEntity(customerDto)
        response.data = CustomerDto().convertToDto(customerRepository.save(customer))
        return response
    }


    override fun findByCpf(cpf: String): Response<CustomerDto> {
        var response: Response<CustomerDto> = Response()

        customerRepository.findByCpf(cpf)?.let { response.data = CustomerDto().convertToDto(it) }
                ?: response.erros.add(ApiError(HttpStatus.NOT_FOUND, "CPF not found.").toString())

        return response
    }

    override fun findById(id: String): Customer? {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableIterable<Customer>? = customerRepository.findAll()


    override fun update(cpf: String, customerDto: CustomerDto): Response<CustomerDto> {
        var response: Response<CustomerDto> = Response()
        var customer: Customer? = null
        customerRepository.findByCpf(cpf)?.apply {
            this.birthday = customerDto.birthday
            this.email = customerDto.email
            this.name = customerDto.name
            this.surname = customerDto.surname
            customer = this
        } ?: response.erros.add(ApiError(HttpStatus.NOT_FOUND, "CPF not found.").toString())
        response.data = CustomerDto().convertToDto(customer)
        return response
    }

    /**Extension function to convert entity to DTO **/
    fun CustomerDto.convertToDto(customer: Customer?) = CustomerDto(name = customer?.name!!, cpf = customer.cpf, email = customer.email, surname = customer.surname, birthday = customer.birthday, accounts = customer.account)

    /**Extension function to convert DTO to entity **/
    fun Customer.convertToEntity(customerDto: CustomerDto) = Customer(name = customerDto.name, cpf = customerDto.cpf, email = customerDto.email, surname = customerDto.surname, birthday = customerDto.birthday)
}