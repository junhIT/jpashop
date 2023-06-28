package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;

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
		member.setName("memberA");
		
		// when
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.find(saveId);
		
		// then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
		/*
		 * findMemeber == memeber -> true
		 * ���� ���Ӽ� Context �ȿ��� ID��(�ĺ���)�� ������ ���� Entity�� �ĺ���.
		 * 1�� Cache�� �ִ� �׸񿡼� �������� ������ SelectQuery���� ������� ����.
		 */
		Assertions.assertThat(findMember).isEqualTo(member);	//JPA ��ƼƼ ���ϼ� ����
		
	}

}
