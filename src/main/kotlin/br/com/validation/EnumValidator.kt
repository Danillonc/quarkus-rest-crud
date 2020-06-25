package br.com.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * Annotation to validate input account bank type.
 */
@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [AccountTypeEnumValidator::class])
annotation class EnumValidator (
        val message: String = "AccountType is invalid.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)