package com.beehive.userservice.model.entity;

import com.beehive.userservice.model.constant.AccountStatus;
import com.beehive.userservice.model.constant.ProfileCategory;
import com.commons.audit.entity.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_orders_created_date", columnList = "created_date")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_users_email", columnNames = "email"),
                @UniqueConstraint(name = "unique_users_username", columnNames = "username")
        }
)

public class User extends Auditable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "pre_computed_hash", nullable = false, updatable = false)
    private String preComputedHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_category", nullable = false)
    private ProfileCategory profileCategory;

    @Column(name = "bio")
    private String bio;

    @Column(name = "avatar_url", length = 2048)
    private String avatarURL;
}
