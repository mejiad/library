package com.evoltech.library.model.base;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class BaseJpaEntity<ID> implements Persistable<ID> {

    private String guid;
    private LocalDateTime created;
    private LocalDateTime modified;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
    private void markNotNew(){
        this.isNew = false;
    }

    @PrePersist
    void onCreate() {
        markNotNew();
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        this.setGuid(uuid);
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }

}
