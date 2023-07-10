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
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	public void 회원가입() throws Exception {
		//given -> 조건 
		Member member = new Member();
		member.setName("Kim");
		
		//when -> 실행
		Long saveId = memberService.join(member);
		
		//then -> 결과
		em.flush();
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//when
		memberService.join(member1);
//		try {
			memberService.join(member2);
//		} catch (IllegalStateException e) {
//			return;
//		} --> @Test(expected = IllegalStateException.class) 로 대체
		
		//then
		fail("예외가 발생해야 합니다.");
	}

}
