package com.example.book_management.controller

import com.example.book_management.dto.book.BookCreateRequest
import com.example.book_management.dto.book.BookUpdateRequest
import com.example.book_management.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    fun create(
        @RequestBody request: BookCreateRequest
    ): ResponseEntity<Long> {
        val id = bookService.create(request)
        return ResponseEntity.ok(id)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: BookUpdateRequest
    ): ResponseEntity<Void> {
        bookService.update(
            id,
            request
        )
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        val book = bookService.findById(id)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(book)
    }

    @GetMapping
    fun findByAuthorId(
        @RequestParam authorId: Long
    ): ResponseEntity<Any> {
        val books = bookService.findByAuthorId(authorId)

        return ResponseEntity.ok(books)
    }
}
