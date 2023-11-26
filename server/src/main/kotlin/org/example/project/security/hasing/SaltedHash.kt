package org.example.project.security.hasing

data class SaltedHash(
    val hash: String,
    val salt: String
)