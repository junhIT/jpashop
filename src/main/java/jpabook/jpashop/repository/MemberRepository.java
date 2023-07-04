package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository	// SpringBean 등록
@RequiredArgsConstructor
public class MemberRepository {

	// SpringDataJpa가 @PersistenceContext -> @Autowired 제공.
	// 의존성 주입 설정 가능.
//	@PersistenceContext
	private final EntityManager em;
	
	public void save(Member member) {
		em.persist(member);
	}
	
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
	public List<Member> findByNames(String name) {
		return em.createQuery("select m from Member where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}
}
