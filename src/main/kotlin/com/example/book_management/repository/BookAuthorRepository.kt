package com.example.book_management.repository

import com.example.book_management.jooq.tables.BookAuthors
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookAuthorRepository(
    private val dsl: DSLContext
) {

    fun create(
        bookId: Long,
        authorId: Long
    ) {
        dsl.insertInto(BookAuthors.BOOK_AUTHORS)
            .set(BookAuthors.BOOK_AUTHORS.BOOK_ID, bookId)
            .set(BookAuthors.BOOK_AUTHORS.AUTHOR_ID, authorId)
            .execute()
    }

    fun deleteByBookId(
        bookId: Long
    ) {
        dsl.deleteFrom(BookAuthors.BOOK_AUTHORS)
            .where(BookAuthors.BOOK_AUTHORS.BOOK_ID.eq(bookId))
            .execute()
    }

    fun findAuthorIdsByBookId(
        bookId: Long
    ): List<Long> {
        return dsl.select(BookAuthors.BOOK_AUTHORS.AUTHOR_ID)
            .from(BookAuthors.BOOK_AUTHORS)
            .where(BookAuthors.BOOK_AUTHORS.BOOK_ID.eq(bookId))
            .fetch(BookAuthors.BOOK_AUTHORS.AUTHOR_ID)
            .mapNotNull { it }
    }
}
