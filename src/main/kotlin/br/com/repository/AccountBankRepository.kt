package br.com.repository

import br.com.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Interface defines a repository layer to account entity.
 */
@Repository
interface AccountBankRepository: JpaRepository<Account, Long> {
}