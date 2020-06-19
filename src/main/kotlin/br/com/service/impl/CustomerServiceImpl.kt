package br.com.service.impl

import br.com.domain.Customer
import br.com.repository.CustomerRepository
import br.com.service.CustomerService
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository): CustomerService {

    override fun persist(customer: Customer): Customer = customerRepository.save(customer)

    override fun findByCpf(cpf: String): Customer? {
        try {
             return customerRepository.findByCpf(cpf)
        }catch (e: Exception){
             return null
        }

    }

    override fun findById(id: String): Customer? {
        TODO("Not yet implemented")
    }
}