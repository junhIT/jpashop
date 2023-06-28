package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {
	
	@Autowired MemberRepository memberRepository;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testMember() throws Exception {
		// given
		Member member = new Member();
		member.setUserName("memberA");
		
		// when
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.find(saveId);
		
		// then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
		/*
		 * findMemeber == memeber -> true
		 * 같은 영속성 Context 안에서 ID값(식별자)이 같으면 같은 Entity로 식별함.
		 * 1차 Cache에 있는 항목에서 가져오기 때문에 SelectQuery조차 실행되지 않음.
		 */
		Assertions.assertThat(findMember).isEqualTo(member);	//JPA 엔티티 동일성 보장
		
	}

}
