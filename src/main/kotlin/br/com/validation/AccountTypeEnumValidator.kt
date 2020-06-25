package br.com.validation

import br.com.enums.AccountTypeEnum
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * Implememtation to validate input data to account type bank in request.
 */
class AccountTypeEnumValidator: ConstraintValidator<EnumValidator, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean = AccountTypeEnum.getAccountTypeByType(value!!)

}
