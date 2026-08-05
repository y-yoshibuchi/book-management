package com.example.book_management.dto.author

import java.time.LocalDate

data class AuthorCreateRequest(
    val name: String,
    val birthDate: LocalDate
)
