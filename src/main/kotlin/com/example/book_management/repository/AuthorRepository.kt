package com.example.book_management.repository

import com.example.book_management.jooq.tables.Authors
import com.example.book_management.jooq.tables.records.AuthorsRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class AuthorRepository(
    private val dsl: DSLContext
) {

    fun create(
        name: String,
        birthDate: LocalDate
    ): Long {
        return dsl.insertInto(Authors.AUTHORS)
            .set(Authors.AUTHORS.NAME, name)
            .set(Authors.AUTHORS.BIRTH_DATE, birthDate)
            .returningResult(Authors.AUTHORS.ID)
            .fetchOne()!!
            .getValue(Authors.AUTHORS.ID)!!
    }

    fun update(
        id: Long,
        name: String,
        birthDate: LocalDate
    ) {
        dsl.update(Authors.AUTHORS)
            .set(Authors.AUTHORS.NAME, name)
            .set(Authors.AUTHORS.BIRTH_DATE, birthDate)
            .where(Authors.AUTHORS.ID.eq(id))
            .execute()
    }

    fun findById(id: Long): AuthorsRecord? {
        return dsl.selectFrom(Authors.AUTHORS)
            .where(Authors.AUTHORS.ID.eq(id))
            .fetchOne()
    }
}
