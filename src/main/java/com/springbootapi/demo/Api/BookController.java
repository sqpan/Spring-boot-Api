package com.springbootapi.demo.Api;

import com.springbootapi.demo.Exception.InvalidBookException;
import com.springbootapi.demo.Exception.NotFoundException;
import com.springbootapi.demo.domain.Book;
import com.springbootapi.demo.domain.DTO.BookDTO;
import com.springbootapi.demo.service.BookService;
import com.springbootapi.demo.utils.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * get all book list
     * @return
     */
    @GetMapping("/books")
    public ResponseEntity listAllBooks() {
        List<Book> books = bookService.findAllBooks();
        if (books.isEmpty()) {
            throw new NotFoundException("Book list not exist!");
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    /**
     * get one book by id
     * @param id
     * @return
     */
    @GetMapping("/onebook")
    public ResponseEntity getOneBook(@RequestParam Long id) {
        Book books = bookService.getBookById(id);
        if (books == null) {
            throw new NotFoundException(String.format("Book by id %s not exist!", id));
        }
        return new ResponseEntity<Object>(books, HttpStatus.OK);
    }

    /**
     * create a book
     * @param bookDTO
     * @return
     */
    @PostMapping("/savebook")
    public ResponseEntity saveBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidBookException("Invalid Book input", bindingResult);
        }
        Book book = bookDTO.convertToBook();
        Book book1 = bookService.saveBook(book);
        return new ResponseEntity(book1, HttpStatus.CREATED);
    }

    /**
     * update a book info
     * @param id
     * @param bookDTO
     * @return
     */
    @PutMapping("/updatebook")
    public ResponseEntity updateBook(@RequestParam Long id,
                                     @Valid @RequestBody BookDTO bookDTO,
                                     BindingResult bindingResult) {
        Book currentBook = bookService.getBookById(id);
        if (currentBook == null) {
            throw new NotFoundException(String.format("Update target %s not found", id));
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidBookException(String.format("Update target book %s invalid", id), bindingResult);
        }
        bookDTO.convertToBook(currentBook);
        Book newBook = bookService.updateBook(currentBook);
        return new ResponseEntity(newBook, HttpStatus.OK);
    }



    /**
     * delete one book
     * @param id
     * @return
     */
    @DeleteMapping("/deletebook")
    public ResponseEntity deleteBook(@RequestParam Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * delete all books
     * @return
     */
    @DeleteMapping("/deleteall")
    public ResponseEntity deleteAll() {
        bookService.deleteAllBooks();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
