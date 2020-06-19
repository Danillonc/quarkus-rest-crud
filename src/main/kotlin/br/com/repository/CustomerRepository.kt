package br.com.repository

import br.com.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository layer to customer DAO operations.
 */
@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
    fun findByCpf(cpf: String): Customer?
}