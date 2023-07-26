package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
// class OrderServiceTest { >> public 안붙혀주니까 junitTest 실행 안됨;;
public class OrderServiceTest {
	
	@Autowired EntityManager em;
	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;

	@Test
	public void 상품주문() throws Exception {
		// given
		Member member = createMember();
		
		Book book = createBook("시골JPA", 10000, 10);
		
		int orderCount = 2;
		
		// when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		// then
		Order order = orderRepository.findOne(orderId);
		
		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
		assertEquals("주문 가격은 가격 * 수량이다.", 10000*orderCount, order.getTotalPrice());
		assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
	}

	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception {
		// given
		Member member = createMember();
		Item item = createBook("시골JPA", 10000, 10);
		
		int orderCount = 11;
		
		// when
		orderService.order(member.getId(), item.getId(), orderCount);
		
		// then
		fail("재고 수량 부족 예외가 발생해야 한다.");
	}
	
	@Test
	public void 주문취소() throws Exception {
		// given
		Member member = createMember();
		Book book = createBook("시골 JPA", 10000, 10);
		
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		// when
		orderService.cancelOrder(orderId);
		
		// then
		Order order = orderRepository.findOne(orderId);
		
		assertEquals("주문 취소 시 상태는 CANCEL이다.", OrderStatus.CANCEL, order.getStatus());
		assertEquals("주문 취소된 상품의 개수만큼 재고가 증가되어야 한다.", 10, book.getStockQuantity());
	}
	
	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "깅기", "123-123"));
		em.persist(member);
		return member;
	}
	
	private Book createBook(String name, int price, int stockQuantity) {
		Book book= new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}


}
