package br.com.utils

import kotlin.random.Random

/**
 * Class responsible to define static utils methods.
 */
class Utils {

     companion object {

         private val numberPool : List<Char> = ('0'..'2') + ('3'..'5') + ('6'..'9')
         private const val ACCOUNT_NUMBER_LEN = 4
         private const val BRANCH_NUMBER_LEN = 6

         fun generateAccountNumber(): Int {
             return (1..ACCOUNT_NUMBER_LEN)
                     .map { i -> Random.nextInt(0, numberPool.size) }
                     .map(numberPool::get)
                     .joinToString("").toInt()
         }

         fun generateBrancNumber(): Int {
             return (1..BRANCH_NUMBER_LEN)
                     .map { i -> Random.nextInt(0, numberPool.size) }
                     .map(numberPool::get)
                     .joinToString("").toInt()
         }
     }
}