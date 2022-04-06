package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //파이널로 된 필드 생성자 만들어줌
public class MemberService {

    //final로 하면 컴파일 시점에 체크를 할수있다
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional //따로 어노테이션을 두면 우선권을 가져서 얘가 적용된다
    public Long join(Member member) {

        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPRION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //한 건 조회
    public Member findOnd(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
