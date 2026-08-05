package com.example.book_management.repository

import com.example.book_management.domain.PublicationStatus
import com.example.book_management.jooq.tables.Books
import com.example.book_management.jooq.tables.records.BooksRecord
import com.example.book_management.jooq.tables.BookAuthors
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(
    private val dsl: DSLContext
) {

    fun create(
        title: String,
        price: Int,
        publicationStatus: PublicationStatus
    ): Long {
        return dsl.insertInto(Books.BOOKS)
            .set(Books.BOOKS.TITLE, title)
            .set(Books.BOOKS.PRICE, price)
            .set(
                Books.BOOKS.PUBLICATION_STATUS,
                publicationStatus.name
            )
            .returningResult(Books.BOOKS.ID)
            .fetchOne()!!
            .getValue(Books.BOOKS.ID)!!
    }

    fun update(
        id: Long,
        title: String,
        price: Int,
        publicationStatus: PublicationStatus
    ) {
        dsl.update(Books.BOOKS)
            .set(Books.BOOKS.TITLE, title)
            .set(Books.BOOKS.PRICE, price)
            .set(
                Books.BOOKS.PUBLICATION_STATUS,
                publicationStatus.name
            )
            .where(Books.BOOKS.ID.eq(id))
            .execute()
    }

    fun findById(id: Long): BooksRecord? {
        return dsl.selectFrom(Books.BOOKS)
            .where(Books.BOOKS.ID.eq(id))
            .fetchOne()
    }

    fun findByAuthorId(authorId: Long): List<BooksRecord> {
        return dsl
            .select(Books.BOOKS.fields().toList())
            .from(Books.BOOKS)
            .join(BookAuthors.BOOK_AUTHORS)
            .on(BookAuthors.BOOK_AUTHORS.BOOK_ID.eq(Books.BOOKS.ID))
            .where(BookAuthors.BOOK_AUTHORS.AUTHOR_ID.eq(authorId))
            .fetchInto(BooksRecord::class.java)
    }
}
