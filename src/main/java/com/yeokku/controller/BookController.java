package com.yeokku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeokku.model.dto.Book;
import com.yeokku.model.service.BookService;

@CrossOrigin("*")
@RequestMapping("/book")
@RestController
public class BookController {
	
	@Autowired
	BookService bookSevice;
	
	@GetMapping("/list/{country}")
	public List<Book> getBooks(@PathVariable String country) {
		
		final String SERVICE_KEY = "c747ae14f1a86c0cb8501feeeadfac48aac79fe0bf861740785adff5b72703e8";
		String urlStr = "https://www.nl.go.kr/NL/search/openApi/cip.do?key=" 
				+ SERVICE_KEY
				+ "&kwd="+country+"&apiType=xml";

		
		//xml sax parsing
		
		
	
		return null;
	}

}
