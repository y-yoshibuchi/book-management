package com.example.book_management.dto.book

import com.example.book_management.domain.PublicationStatus

data class BookUpdateRequest(
    val title: String,
    val price: Int,
    val publicationStatus: PublicationStatus,
    val authorIds: List<Long>
)
