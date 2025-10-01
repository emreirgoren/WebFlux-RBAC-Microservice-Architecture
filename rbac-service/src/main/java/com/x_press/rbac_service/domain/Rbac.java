package com.x_press.rbac_service.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("rbac")
@Getter
@Setter
public class Rbac {

    @Id
    private Long id;

    private String email;

    private String path;

    private String method;

}
