package br.com.repository

import br.com.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Interface defines a repository layer to account entity.
 */
@Repository
interface AccountBankRepository: CrudRepository<Account, Long> {

    /**
     * Function responsible to retrieve an account by params.
     * @param accountNumber - Identifies account
     * @param branchNumber - Identifies bank
     */
    fun findByAccountNumberAndBranchNumber(accountNumber: Int, branchNumber: Int): Account?

}