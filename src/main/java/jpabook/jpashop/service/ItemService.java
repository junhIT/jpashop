package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);
	}
	
	// Transactional에 의해서 commit이 되면 jpa는 flush를 날림. 영속성 Context에서 변경된 값을 확인하여(변경감지) update Query 실행
	@Transactional
	public void updateitem(Long itemId, String name, int price, int stockQuantity) {
		Item findItem = itemRepository.findOne(itemId);
		findItem.setName(name);
		findItem.setPrice(price);
		findItem.setStockQuantity(stockQuantity);
	}
	
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}
}
