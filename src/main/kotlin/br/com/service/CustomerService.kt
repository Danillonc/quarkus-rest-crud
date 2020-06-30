package br.com.service

import br.com.domain.Customer
import br.com.dto.CustomerDto
import br.com.response.Response

/**
 * Interface defines customer service contracts operations.
 */
interface CustomerService {

    fun persist(customerDto: CustomerDto): Response<CustomerDto>

    fun findByCpf(cpf: String): Response<CustomerDto>

    fun findById(id: String): Customer?

    fun findAll(): MutableIterable<Customer>?

    fun update(cpf: String, customerDto: CustomerDto): Response<CustomerDto>
}