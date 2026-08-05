package com.example.book_management.controller

import com.example.book_management.dto.author.AuthorCreateRequest
import com.example.book_management.dto.author.AuthorUpdateRequest
import com.example.book_management.service.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authors")
class AuthorController(
    private val authorService: AuthorService
) {

    @PostMapping
    fun create(
        @RequestBody request: AuthorCreateRequest
    ): ResponseEntity<Long> {
        val id = authorService.create(request)
        return ResponseEntity.ok(id)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: AuthorUpdateRequest
    ): ResponseEntity<Void> {
        authorService.update(
            id,
            request
        )
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        val author = authorService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(author)
    }
}
