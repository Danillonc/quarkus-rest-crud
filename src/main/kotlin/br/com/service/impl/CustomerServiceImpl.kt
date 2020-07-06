package br.com.service.impl

import br.com.domain.Customer
import br.com.dto.CustomerDto
import br.com.messages.ApiError
import br.com.messages.enums.Messages
import br.com.repository.CustomerRepository
import br.com.response.Response
import br.com.service.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

/**
 * Represents a service implementation layer containing business logic for contracts customer.
 */
@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {

    private val logger = LoggerFactory.getLogger(CustomerServiceImpl::class.java)

    override fun persist(customerDto: CustomerDto): Response<CustomerDto> {
        var response: Response<CustomerDto> = Response()

        try {
            var customer: Customer = Customer().convertToEntity(customerDto)
            customer = customerRepository.save(customer)
            response.data = CustomerDto().convertToDto(customer)
            response.addMessage(Messages.OK)
        } catch (e: Exception) {
            logger.error("Error to persist Customer => :" + e.message)
        }
        return response
    }


    override fun findByCpf(cpf: String): Response<CustomerDto> {
        var response: Response<CustomerDto> = Response()

        try {
            customerRepository.findByCpf(cpf)?.let { response.data = CustomerDto().convertToDto(it) }
                    ?: run { response.addMessage(Messages.CPF_NOT_FOUND) }

        } catch (e: Exception) {
            logger.error("Error to find customer by cpf => :" + e.message)
        }
        return response
    }

    override fun findById(id: String): Response<CustomerDto> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Response<List<CustomerDto>> {
        var response: Response<List<CustomerDto>> = Response()

        try {
            val customers: List<CustomerDto> = customerRepository.findAll().map { CustomerDto(it.name, it.cpf, it.email, it.surname, it.birthday, it.account) }
            response.data = customers
            response.addMessage(Messages.OK)

        } catch (e: Exception) {
            logger.error("Error to find all customer => :" + e.message)
        }
        return response

    }


    override fun update(cpf: String, customerDto: CustomerDto): Response<CustomerDto> {
        var response: Response<CustomerDto> = Response()
        var customer: Customer? = null

        try {
            customerRepository.findByCpf(cpf)?.apply {
                this.birthday = customerDto.birthday
                this.email = customerDto.email
                this.name = customerDto.name
                this.surname = customerDto.surname
                customer = this
            } ?: response.addMessage(Messages.CPF_NOT_FOUND)
            customerRepository.save(customer)
            response.data = CustomerDto().convertToDto(customer)
            response.addMessage(Messages.OK)

        } catch (e: Exception) {
            logger.error("Error to update customer => :" + e.message)
        }
        return response
    }

    override fun delete(cpf: String): Response<Void> {
        var response: Response<Void> = Response()

        try {
            customerRepository.findByCpf(cpf)?.let {
                customerRepository.delete(it)
                response.addMessage(Messages.OK)
            } ?: run { response.addMessage(Messages.CUSTOMER_NOT_FOUND) }


        } catch (e: Exception) {
            logger.error("Error to delete a customer => :" + e.message)
        }
        return response
    }

    /**Extension function to convert entity to DTO **/
    fun CustomerDto.convertToDto(customer: Customer?) = CustomerDto(name = customer?.name!!, cpf = customer.cpf, email = customer.email, surname = customer.surname, birthday = customer.birthday, accounts = customer.account)

    /**Extension function to convert DTO to entity **/
    fun Customer.convertToEntity(customerDto: CustomerDto) = Customer(name = customerDto.name, cpf = customerDto.cpf, email = customerDto.email, surname = customerDto.surname, birthday = customerDto.birthday)
}