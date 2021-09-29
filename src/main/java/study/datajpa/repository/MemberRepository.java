package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

/**
 * -----MemberJpaRepository를 SpringDataJpa화 한 리포지토리입니다.-----
 * 1. interface로 만들어야 합니다.
 * 2. JpaRepository<타입,PK(id)>을 상속 받아야 합니다.
 * **/
public interface MemberRepository extends JpaRepository<Member, Long> {
}
