package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    /**
     * 기본 CRUD
     * 저장
     * 변경 변경감지 사용
     * 삭제
     * 전체 조회
     * 단건 조회
     * 카운트
     * **/

    @PersistenceContext //영속성 컨텍스트 이용
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public List<Member> findAll() { //전체 조회를 할땐 jpql(객체 대상 쿼리)을 사용해야 한다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();//--getResultList-->결과를 리스트로 반환한다.
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult(); //--getSingleResult-->결과를 하나만 반환한다.
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
