package br.com.enums

/**
 * Enum representa a account type in bank.
 */
enum class AccountTypeEnum(val type: String,val desc: String) {
    PF("PF","PESSOA_FISICA"),
    PJ("PJ","PESSOA_JURIDICA");

    companion object {
        fun getAccountTypeByType(type: String): Boolean {
            return AccountTypeEnum.values().any { it.type == type }
        }
        fun getAccountType(type: String): AccountTypeEnum {
            return AccountTypeEnum.values().find { it.type == type }!!
        }
    }
}