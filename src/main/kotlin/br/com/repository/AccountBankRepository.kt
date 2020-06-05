package br.com.repository

import br.com.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Interface defines a repository layer to account entity.
 */
interface AccountBankRepository: JpaRepository<Account, Long> {
}