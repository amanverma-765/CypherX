package com.akv.cypherx.security


fun passwordStrength(password: String): Pair<Float, String> {

    var score = 0

    if (password.isBlank()) {
        score = 0
    } else {

        if (password.length >= 8) {
            score++
        } else {
            score--
        }
        if (password.any { it.isDigit() }) {
            score++
        }
        if (password.any { it.isLowerCase() }) {
            score++
        }
        if (password.any { it.isUpperCase() }) {
            score++
        }
        if (password.any { "!@#$%^&*()_+-=[]{};':|,.<>?".contains(it) }) {
            score++
        }
    }

    val strength = (score / 5f)

    val strengthDescription = when (strength) {
        1f -> "Very Strong"
        in 0.8f..0.99f -> "Strong"
        in 0.6f..0.79f -> "Medium"
        in 0.4f..0.59f -> "Weak"
        else -> "Very Weak"
    }

    return Pair(strength, strengthDescription)
}
