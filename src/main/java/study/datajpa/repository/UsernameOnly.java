package study.datajpa.repository;

////Projections 인터페이스만 만들면 스프링데이터 jpa가 프록시로 구현체를 만들어서 뭐리 날려줌
public interface UsernameOnly {
    String getUsername();
}
