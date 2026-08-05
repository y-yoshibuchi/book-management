package com.example.book_management.dto.book

import com.example.book_management.domain.PublicationStatus
import com.example.book_management.dto.author.AuthorResponse

data class BookResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val publicationStatus: PublicationStatus,
    val authors: List<AuthorResponse>
)
