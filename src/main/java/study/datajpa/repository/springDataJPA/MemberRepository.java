package study.datajpa.repository.springDataJPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
/**
 * reviewing_20220403
 */

/**
 * -----MemberJpaRepository를 SpringDataJpa화 한 리포지토리입니다.-----
 * 1. interface로 만들어야 합니다.
 * 2. JpaRepository<엔티티 타입,식별자 타입(PK 타입)>을 상속 받아야 합니다.
 * 3. SpringDataJpa는 @Repository 애노테이션 생략 가능 합니다.
 * **/

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    //------------스프링 데이터 JPA에 기본적으로 포함 되어있지 않은 메서드는 인터페이스 커스텀한다.---------------//

    //엔티티 쿼리
    
    /**--인터페이스 커스텀~따로 메서드를 정의 해주지 않아도 이름만 잘 정의 해주면 관례에 따라
     쿼리 메소드 필터 조건
    스프링 데이터 JPA 공식 문서 참고: (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
    springdatajpa가 알아서 쿼리 날려준다.--**/
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    
    /**
     * **실무에서 잘 안쓰게 된다.**
     *
     * 스프링 데이터 JPA로 Named 쿼리 호출하기
     * -스프링 데이터 JPA는 선언한 "도메인 클래스 + .(점) + 메서드 이름"으로 Named 쿼리를 찾아서 실행한다
     * 따라서 이 관례를 따라주었다면  @Query(name = "Member.findByUsername") 생략 가능하다.
     *
     * **/
    @Query(name = "Member.findByUsername") //Member클래스의 Member.findByUsername라는 별칭을 가진 쿼리를 날리는 것.
    List<Member> findByUsername(@Param("username") String username);

    /***
     * 직접 쿼리 정의하기--메서드에 JPQL 쿼리 작성하기 (정적쿼리)
     * (cf.동적쿼리는 queryDSL을 사용한다.)
     * */
    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    
    //DTO 조회 쿼리

    /**
     * 단순히 값 하나를 조회
     * **/
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();




    //컬렉션 파라미터 바인딩-Collection 타입으로 in절 지원 - 이름 기반으로 찾기.(위치 기반으로는 쓰지 않는다.)
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    //스프링 데이터 JPA는 유연한 반환 타입 지원
    List<Member> findListByUsername(String name); //컬렉션
    Member findMemberByUsername(String name); //단건
    Optional<Member> findOptionalByUsername(String name); //단건 Optional

    //스프링 데이터 JPA 페이징과 정
    Page<Member> findByAge(int age, Pageable pageable);

    //스프링 데이터 JPA를 사용한 벌크성 수정 쿼리
    @Modifying(clearAutomatically = true) //.executeUpdate();을 실행 하도록. (이거 안넣으면 ~result 문이 나가게 된다.)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
    
    //==EntityGraph,, 스프링 jpa는 쿼리 쓰는 걸 최대한 줄여주는 역할을 하는데, fetch를 쓸 때마다 쿼리를 짜야하면 효율이 떨어지고 그 외 이유들 때문에 이를 쓴다//

    //fetch조인-쌩쿼리로 날리기
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();
    
    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL도 짜고 + 엔티티 그래프도 넣기
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 쿼리에서 특히 편리하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(String username);

    //JPA Hint
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    //JPA Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);

    /**Projections
     * 엔티티 대신에 DTO를 편리하게 조회할 때 사용
     * 예를 들어 전체 엔티티가 아니라 만약 회원 이름만 딱 조회하고 싶은 경우
     * ***/
    List<UsernameOnly> findProjectionsByUsername(String username);
}
