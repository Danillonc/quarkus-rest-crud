package br.com.repository

import br.com.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Repository layer to customer DAO operations.
 */
@Repository
interface CustomerRepository: CrudRepository<Customer, Long> {
    fun findByCpf(cpf: String): Customer?
}