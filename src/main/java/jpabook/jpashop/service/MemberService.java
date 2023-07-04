package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터 변경 시 Transactional = 필수!
// 영속성 Context를 refresh, dirthcheck를 안하고.. DB에 따라서 리소스를 적게 사용
// 단순 조회의 경우 readOnly = true를 넣어주면 좋다.
//@AllArgsConstructor	// 4. Field의 모든 값을 설정하는 생성자 생성
@RequiredArgsConstructor	// 5. final만 가지는 Field의 값을 설정하는 생성자 생성. (선호)
public class MemberService {
	
	// 1. Spring이 SpringBean에 등록되어 있는 Repository를 Injection (Refield Injection)
//	@Autowired
	private final MemberRepository memberRepository; // -> (3)생성자 Injection의 경우 final 권장. 생성자에 파라미터 설정이 없을 경우 오류 

	/*
	 *  2. SetterInjection
	 *  장점 : Test의 경우 Mock같은 걸 주입할 수 있음.     
	 *  단점 : Runtime에 memberRepository가 변경될 수 있음 (서버가 가동된 이후)                                 
	 */
//	@Autowired
//	public void setMemberRespository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	/*
	 *	3. 생성자 Injection
	 *	SetterInjection의 단점을 보완.
	 *	테스트케이스의 경우 생성 시점에 의존관계 명확하게 정의할 수 있음.
	 */
//	@Autowired --> Autowired 생략 가능. (생성자가 1개일 경우에는 Spring이 알아서 Autowired해줌)
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	/**
	 *  회원 가입
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	/**
	 * 중복회원 체크 로직 
	 */
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByNames(member.getName());
		
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	/**
	 *  회원 전체 조회
	 */

	@Transactional(readOnly = true)	
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/**
	 * 회원 단건 조회
	 */
	public Member findOne(long memberId) {
		return memberRepository.findOne(memberId);
	}

}
