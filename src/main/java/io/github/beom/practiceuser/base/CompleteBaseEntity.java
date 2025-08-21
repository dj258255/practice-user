package io.github.beom.practiceuser.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class CompleteBaseEntity {

    // Audit 정보 (생성자, 수정자)
    @CreatedBy
    @Column(name = "createdBy", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modifiedBy")
    private String modifiedBy;

    // Timestamp 정보 (생성일, 수정일)
    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;

    // Soft Delete 정보
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    // Soft Delete 메서드들
    public void markDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restoreDeleted() {
        this.deletedAt = null;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
