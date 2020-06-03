package br.com.domain

import javax.persistence.Entity
import javax.persistence.Id

/**
 * This entity represents a customer infos.
 */
@Entity
data class Customer (
        val name: String? = "",
        val surname: String,
        val birthday: String,
        val email: String,
        val cpf: String? = "",
        @Id val id: Long? = null
)