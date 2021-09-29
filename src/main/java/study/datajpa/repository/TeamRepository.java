package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

/**
 * -----TeamJpaRepository를 SpringDataJpa화 한 리포지토리입니다.-----
 * 1. interface로 만들어야 합니다.
 * 2. JpaRepository<엔티티 타입,식별자 타입(PK 타입)>을 상속 받아야 합니다.
 * 3. SpringDataJpa는 @Repository 애노테이션 생략 가능 합니다.
 * **/

public interface TeamRepository extends JpaRepository<Team, Long> {
}
