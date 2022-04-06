package study.datajpa.entity.auditing;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 *  순수 JPA로 Auditing 적용하기
 * **/
@MappedSuperclass //진짜 상속 괸계가 아닌, data만 공유하도록? 하는.
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) // 실숙로 값을 변경하지 않아도 DB에는 반영되지 않도록 한다.
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @PrePersist //저장하기 전에 실행되도록
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now; //처음 저장일과 맞춰둔다.(null값을 넣어 놓지 않기 위해)
    }

    @PreUpdate //업데이트 하기 전에 실행되도록
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
