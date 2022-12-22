package com.glc.bookservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class BookServiceApplicationTests {

	private MockMvc mvc;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookController bookController;

	private JacksonTester<Book> jsonBook;
	private JacksonTester<Collection<Book>> jsonBooks;

	@BeforeEach
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	void contextLoads() {
	}

	// AC1: When I enter the title, author, year of publication, and length of the
	// book into the UI and hit submit, my book will saved to the list.
	@Test
	public void canCreateANewBook() throws Exception {
		Book book = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		mvc.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBook.write(book).getJson()))
				.andExpect(status().isOk());
	}

	// AC2: When I click “View All Books” the application will display a list of all
	// the books in my list.
	@Test
	public void canGetAllBooks() throws Exception {
		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		Book book2 = new Book(2, "It", "Stephen King", 1986, 1138);
		Collection<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		when(bookRepository.getAllBooks()).thenReturn(books);
		mvc.perform(get("/books/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonBooks.write(books).getJson()));
	}

	// AC3: When I click the checkbox next to a book, and then press the "Delete
	// Book" button, the application will remove the book from my list
	@Test
	public void canDeleteBook() throws Exception {
		// Book book = new Book(1, "Shikwa", "Hassan Haider", 2022, 1200);
		// when(bookRepository.deleteBook(5)).thenReturn("Book successfully delete!");
		when(bookRepository.deleteBook(5)).thenReturn(true);
		mvc.perform(delete("/books/5"))
			.andExpect(status().isOk())
			.andExpect(content().string("Book successfully deleted!"));
	}

	// AC4: When I click the checkbox next to a book, and then press the “Update
	// Book” button, the application will allow me to update any of the information
	// about the book.
	@Test
	public void canUpdateBookInfo() throws Exception {
		Book book = new Book(1, "Jawab Shikwa", "Hassan Haider", 2022, 1300);
		when(bookRepository.updateBook(eq(1), any())).thenReturn(true);
		mvc.perform(put("/books/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBook.write(book).getJson()))
				.andExpect(status().isOk());

		when(bookRepository.updateBook(eq(2), any())).thenReturn(false);
		mvc.perform(put("/books/update/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBook.write(book).getJson()))
				.andExpect(status().isNotFound());
	}

}
