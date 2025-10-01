package com.xpress.user_service.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "users")
public class User extends AbstractAuditingEntity{

    @Id
    @Column("id")
    private Long id;

    @Column("login")
    private String login;

    @Column("email")
    private String email;

    @Column("password_hash")
    private String passwordHash;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("activated")
    private boolean activated = true;

    @Column("lang_key")
    private String langKey;

    @Column("user_type")
    private String userType;

}
