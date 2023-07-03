package jpabook.jpashop;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;

@Repository	// SpringBean 등록
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;
	
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
