package br.com.service

import br.com.domain.Customer

/**
 * Interface defines customer service contracts operations.
 */
interface CustomerService {

    fun persist(customer: Customer): Customer

    fun findByCpf(cpf: String): Customer?

    fun findById(id: String): Customer?
}