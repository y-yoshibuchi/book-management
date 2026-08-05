package com.example.book_management.service


import com.example.book_management.dto.author.AuthorCreateRequest
import com.example.book_management.dto.author.AuthorResponse
import com.example.book_management.dto.author.AuthorUpdateRequest
import com.example.book_management.repository.AuthorRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {

    fun create(request: AuthorCreateRequest): Long {
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
        validateBirthDate(request.birthDate)

        authorRepository.update(
            id,
            request.name,
            request.birthDate
        )
    }

    fun findById(id: Long): AuthorResponse? {
        return authorRepository.findById(id)
            ?.let {
                AuthorResponse(
                    id = it.id!!,
                    name = it.name!!,
                    birthDate = it.birthDate!!
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
