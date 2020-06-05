package br.com.resource

import br.com.dto.AccountBankDto
import br.com.service.AccountBankService
import org.eclipse.microprofile.openapi.annotations.Operation
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Accountbank resource layer implements REST API.
 */
@Path("/account")
class AccountBankResource(val accountService: AccountBankService) {

    @Operation(summary = "Create an account with a user.")
    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createAccount(accountBankDto: AccountBankDto){

    }

}