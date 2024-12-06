package com.tmkcomputers.csms.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active = true;

    @Column(name = "entityType", nullable = true)
    private String entityType;

    @Column(name = "entityId", nullable = true)
    private Long entityId;
}