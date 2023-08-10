package jpabook.jpashop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateServiceTest {

	@Autowired
	EntityManager em;
	
	@Test
	public void test() {
		Book book = em.find(Book.class, 1L);
		
		// TX
		book.setName("sdf");
		
		//  변경감지 = dirty Checking 
	}

}
