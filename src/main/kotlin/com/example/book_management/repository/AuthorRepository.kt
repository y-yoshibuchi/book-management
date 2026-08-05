package com.example.book_management.repository

import com.example.book_management.jooq.tables.Authors
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
}
