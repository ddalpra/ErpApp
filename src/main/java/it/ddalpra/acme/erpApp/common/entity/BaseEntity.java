package it.ddalpra.acme.erpApp.common.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    @Column(name = "creation_user", updatable = false)
    private String creationUser;

    @Column(name = "modification_user")
    private String modificationUser;
}