package br.com.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * This entity represents a customer infos.
 */
@Entity
data class Customer (

        var name: String = "",

        var cpf: String = "",

        var email: String = "",

        var surname: String? = "",

        var birthday: String? = "",

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @JsonManagedReference
        @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        var account: List<Account> = emptyList()

)