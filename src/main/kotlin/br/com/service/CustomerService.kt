package br.com.service

import br.com.domain.Customer
import br.com.dto.CustomerDto
import br.com.response.Response

/**
 * Interface defines customer service contracts operations.
 */
interface CustomerService {

    /**
     * Function responsible to implement a business to persist a Customer.
     * @param customerDto - Payload object to create a customer.
     */
    fun persist(customerDto: CustomerDto): Response<CustomerDto>

    /**
     * Function responsible to implement a business to a Customer by param.
     * @param cpf - Param that identifies a Customer.
     */
    fun findByCpf(cpf: String): Response<CustomerDto>

    /**
     * Function responsible to implement a business to find a customer by param.
     * @param id - Id of Customer on DB.
     */
    fun findById(id: String): Response<CustomerDto>

    /**
     * Function responsible to implement a business to find all customers.
     */
    fun findAll(): Response<List<CustomerDto>>

    /**
     * Function responsible to implement a business to update a Customer.
     * @param cpf - Param that identifies the Customer.
     */
    fun update(cpf: String, customerDto: CustomerDto): Response<CustomerDto>

    /**
     * Function responsible to implement a business to delete a Customer.
     * @param cpf - Param that identifies the Customer.
     */
    fun delete(cpf: String): Response<Void>
}