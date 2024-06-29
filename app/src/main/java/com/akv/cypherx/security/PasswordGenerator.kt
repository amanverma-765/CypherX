package com.akv.cypherx.security

import java.security.SecureRandom

fun generatePassword(length: Int): String {
    val allowedChars =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}\\|;:<>/?"
    val random = SecureRandom()
    return buildString {
        repeat(length) {
            val randomIndex = random.nextInt(allowedChars.length)
            append(allowedChars[randomIndex])
        }
    }
}