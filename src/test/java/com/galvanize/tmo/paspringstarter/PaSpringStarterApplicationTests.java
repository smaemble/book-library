package com.galvanize.tmo.paspringstarter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.tmo.paspringstarter.data.model.Book;
import com.galvanize.tmo.paspringstarter.data.repositories.BookRepository;
import com.galvanize.tmo.paspringstarter.model.BookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaSpringStarterApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BookRepository bookRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldAddBookWithJsonResponseCreated() throws Exception {
		BookRequest request = createBookRequest();
		MvcResult mvcResult = mockMvc.perform(post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		Book bookResponse = objectMapper.readValue(content, new TypeReference<Book>() {});
		assertNotNull(bookResponse);
		assertNotNull(bookResponse.getId());
		assertEquals(bookResponse.getAuthor(), request.getAuthor());
		assertEquals(bookResponse.getTitle(), request.getTitle());
		assertEquals(bookResponse.getYearPublished(), request.getYearPublished());
	}

	@Test
	public void shouldFindAllBooksWithJsonResponseOk() throws Exception {
		addBook();
		MvcResult mvcResult = mockMvc.perform(get("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		List<Book> books = objectMapper.readValue(content, new TypeReference<List<Book>>() {});
		assertNotNull(books);
		assertEquals(books.size(), 1);
	}

	@Test
	public void shouldDeleteAllBooksWithJsonResponseNoContent() throws Exception {
		addBook();
		mockMvc.perform(delete("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isNoContent())
				.andReturn();

		List<Book> all = bookRepository.findAll();
		assertTrue(all.isEmpty());
	}

	private void addBook() {
		BookRequest request = createBookRequest();
		Book book = new Book();
		book.setTitle(request.getTitle());
		book.setAuthor(request.getAuthor());
		book.setYearPublished(request.getYearPublished());
		bookRepository.save(book);
	}

	@Test
	void isHealthy() throws Exception {
		mockMvc.perform(get("/health"))
				.andExpect(status().isOk());
	}

	public BookRequest createBookRequest(){
		BookRequest book = new BookRequest();
		book.setAuthor("Barack Obama");
		book.setTitle("The Legacy of Hope");
		book.setYearPublished(2008);
		return book;
	}
}
