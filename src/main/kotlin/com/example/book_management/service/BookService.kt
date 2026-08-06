package com.example.book_management.service

import com.example.book_management.domain.PublicationStatus
import com.example.book_management.dto.book.BookCreateRequest
import com.example.book_management.dto.book.BookUpdateRequest
import com.example.book_management.dto.author.AuthorResponse
import com.example.book_management.dto.book.BookResponse
import com.example.book_management.repository.BookAuthorRepository
import com.example.book_management.repository.BookRepository
import com.example.book_management.repository.AuthorRepository
import com.example.book_management.jooq.tables.records.BooksRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val bookAuthorRepository: BookAuthorRepository,
    private val authorRepository: AuthorRepository
) {

    @Transactional
    fun create(request: BookCreateRequest): Long {
        validateTitle(request.title)
        validatePrice(request.price)
        validateAuthors(request.authorIds)

        val bookId = bookRepository.create(
            request.title,
            request.price,
            request.publicationStatus
        )

        request.authorIds.forEach { authorId ->
            bookAuthorRepository.create(
                bookId,
                authorId
            )
        }

        return bookId
    }

    @Transactional
    fun update(
        id: Long,
        request: BookUpdateRequest
    ) {
        validateTitle(request.title)
        validatePrice(request.price)
        validateAuthors(request.authorIds)

        val currentBook = bookRepository.findById(id)
            ?: throw IllegalArgumentException(
                "書籍が存在しません"
            )

        if (
            currentBook.publicationStatus == PublicationStatus.PUBLISHED.name &&
            request.publicationStatus == PublicationStatus.UNPUBLISHED
        ) {
            throw IllegalArgumentException(
                "出版済みの書籍を未出版には変更できません"
            )
        }

        bookRepository.update(
            id,
            request.title,
            request.price,
            request.publicationStatus
        )

        bookAuthorRepository.deleteByBookId(id)

        request.authorIds.forEach { authorId ->
            bookAuthorRepository.create(
                id,
                authorId
            )
        }
    }

    private fun validateAuthors(
        authorIds: List<Long>
    ) {
        if (authorIds.isEmpty()) {
            throw IllegalArgumentException(
                "書籍には最低1人の著者が必要です"
            )
        }
    }

    private fun validateTitle(
        title: String
    ) {
        if (title.isBlank()) {
            throw IllegalArgumentException(
                "書籍タイトルは必須です"
            )
        }
    }

    private fun validatePrice(
        price: Int
    ) {
        if (price < 0) {
            throw IllegalArgumentException(
                "価格は0以上である必要があります"
            )
        }
    }

    fun findById(
        id: Long
    ): BookResponse? {

        val book = bookRepository.findById(id)
            ?: return null

        return toResponse(book)
    }


    fun findByAuthorId(
        authorId: Long
    ): List<BookResponse> {

        return bookRepository.findByAuthorId(authorId)
            .map {
                toResponse(it)
            }
    }


    private fun toResponse(
        book: BooksRecord
    ): BookResponse {

        val authorIds =
            bookAuthorRepository.findAuthorIdsByBookId(book.id!!)

        val authors =
            authorRepository.findByIds(authorIds)
                .map {
                    AuthorResponse(
                        id = it.id!!,
                        name = it.name!!,
                        birthDate = it.birthDate!!
                    )
                }

        return BookResponse(
            id = book.id!!,
            title = book.title!!,
            price = book.price!!,
            publicationStatus =
                PublicationStatus.valueOf(
                    book.publicationStatus!!
                ),
            authors = authors
        )
    }
}
