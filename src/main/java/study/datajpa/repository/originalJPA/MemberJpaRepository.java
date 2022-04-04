package study.datajpa.repository.originalJPA;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
/****
 *순수 JPA로만 작성해보기 
 */
public class MemberJpaRepository {

    /**
     * 기본 CRUD
     * 저장
     * 변경 => 변경감지 사용(코드 짜 줄 필요 없음)
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

    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByUsername(String username) {
       return em.createNamedQuery("Member.findByUsername", Member.class)
                        .setParameter("username", username)
                        .getResultList();
    }

    public List<Member> findByPage(int age, int offset, int limit) { //무엇을 기준(age)으로 어디부터(offset) 어디까지 자를 것인지.(limit)
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                        .setParameter("age", age)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    //벌크성 수정 쿼리
    public int bulkAgePlus(int age) {
        int resultCount = em.createQuery(
                        "update Member m set m.age = m.age + 1" +
                                "where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
        return resultCount;
    }

}
