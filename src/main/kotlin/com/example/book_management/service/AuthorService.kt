package com.example.book_management.service

import com.example.book_management.dto.author.AuthorCreateRequest
import com.example.book_management.dto.author.AuthorResponse
import com.example.book_management.dto.author.AuthorUpdateRequest
import com.example.book_management.jooq.tables.records.AuthorsRecord
import com.example.book_management.repository.AuthorRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {

    fun create(request: AuthorCreateRequest): Long {
        validateName(request.name)
        validateBirthDate(request.birthDate)

        return authorRepository.create(
            request.name,
            request.birthDate
        )
    }

    fun update(
        id: Long,
        request: AuthorUpdateRequest
    ) {
        validateName(request.name)
        validateBirthDate(request.birthDate)

        getAuthor(id)

        authorRepository.update(
            id,
            request.name,
            request.birthDate
        )
    }

    fun findById(id: Long): AuthorResponse {
        val author = getAuthor(id)

        return AuthorResponse(
            id = author.id!!,
            name = author.name!!,
            birthDate = author.birthDate!!
        )
    }

    private fun getAuthor(
        id: Long
    ): AuthorsRecord {
        return authorRepository.findById(id)
            ?: throw IllegalArgumentException(
                "著者が存在しません"
            )
    }

    private fun validateName(
        name: String
    ) {
        if (name.isBlank()) {
            throw IllegalArgumentException(
                "著者名は必須です"
            )
        }
    }

    private fun validateBirthDate(
        birthDate: LocalDate
    ) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw IllegalArgumentException(
                "生年月日は現在日以前である必要があります"
            )
        }
    }
}
