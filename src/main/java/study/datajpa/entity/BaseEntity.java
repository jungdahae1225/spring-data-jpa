package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 *스프링 데이터 Auditing 적용
 *
 * > 참고: 실무에서 대부분의 엔티티는 등록시간, 수정시간이 필요하지만, 등록자, 수정자는 없을 수도 있다.
 * 그래서 다음과 같이 Base 타입을 분리하고, 원하는 타입을 선택해서 상속한다
 * >>public class BaseEntity extends BaseTimeEntity 이렇게!!
 * **/
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {
    @CreatedDate

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate //마지막 수정 날짜 찍기
    private LocalDateTime lastModifiedDate;

    //등록자 수정자 남기기
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;//마지막 수정자 찍기
}
