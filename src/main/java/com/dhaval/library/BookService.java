package com.dhaval.library;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::fromEntity)
                .toList();
    }

    public BookResponse findById(Long id) {
        return BookResponse.fromEntity(findEntityById(id));
    }

    public BookResponse save(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setAvailable(request.available());
        Book saved = bookRepository.save(book);
        return BookResponse.fromEntity(saved);
    }

    public BookResponse update(Long id, BookRequest request) {
        Book existing = findEntityById(id);
        existing.setTitle(request.title());
        existing.setAuthor(request.author());
        existing.setIsbn(request.isbn());
        existing.setAvailable(request.available());
        Book updated = bookRepository.save(existing);
        return BookResponse.fromEntity(updated);
    }

    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    private Book findEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}