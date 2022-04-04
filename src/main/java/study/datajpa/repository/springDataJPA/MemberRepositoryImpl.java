package study.datajpa.repository.springDataJPA;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

//사용자 정의 인터페이스 구현 클래스
/**
 * 사용자 정의 구현 클래스
 * 규칙: 리포지토리 인터페이스 이름 + Impl
 * 스프링 데이터 JPA가 인식해서 스프링 빈으로 등록
 *
 * 사용자 정의 인터페이스 명 + Impl 방식도 지원한다.
 * 예를 들어서 위 예제의 MemberRepositoryImpl 대신에 MemberRepositoryCustomImpl 같이 구현해도
 * 된다.
 * **/
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}