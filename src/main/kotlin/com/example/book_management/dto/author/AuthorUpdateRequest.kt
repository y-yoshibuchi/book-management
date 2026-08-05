package com.example.book_management.dto.author

import java.time.LocalDate

data class AuthorUpdateRequest(
    val name: String,
    val birthDate: LocalDate
)
