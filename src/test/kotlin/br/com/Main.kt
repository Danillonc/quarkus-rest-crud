package br.com

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.random.nextLong


class Main {

    private val len = 4
    private val charPool : List<Char> = ('0'..'2') + ('3'..'5') + ('6'..'9')



    @Test
    fun teste(){
        val randomString = (1..len)
                .map { i -> Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
        print(randomString)
    }

}