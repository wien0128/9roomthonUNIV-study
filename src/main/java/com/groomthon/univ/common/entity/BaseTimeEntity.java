package com.groomthon.univ.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * <h3>BaseTimeEntity - JPA</h3>
 * <p>Entity 생성/수정 시간 자동 관리 공통 클래스, 엔티티들이 상속하여 공통 필드(생성/수정 시각) 재사용
 * 과 코드 중복 방지</p>
 * <ul>
 *     <li>{@code @MappedSuperclass}: JPA Entity 클래스들이 {@code BaseTimeEntity}를 상속할
 *     경우 {@code createdAt}과 {@code updatedAt} 필드를 자동으로 컬럼으로 인식</li>
 *     <li>{@code @EntityListeners(AuditingEntityListener.class)}: 엔티티의 생성/수정 이벤트를
 *     감지하여 자동으로 시간을 기록 -> JPA Auditing</li>
 *     <li>{@code @EnableJpaAuditing}: application 클래스에 해당 어노테이션을 추가해 
 *     JPA Auditing 어노테이션들을 활성화</li>
 *     <li>{@code createdAt()}: 엔티티 처음 생성 시각 자동으로 기록</li>
 *     <li>{@code @Column(updatable = false)}: 엔티티 수정 시 해당 필드는 불변 보장 = 생성 시각 고정</li>
 *     <li>{@code updatedAt()}: 엔티티 마지막 수정 시각 자동으로 기록</li>
 * </ul>
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    protected LocalDateTime updatedAt;
}
