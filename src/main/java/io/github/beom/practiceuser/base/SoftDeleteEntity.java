package io.github.beom.practiceuser.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class SoftDeleteEntity {

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

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
