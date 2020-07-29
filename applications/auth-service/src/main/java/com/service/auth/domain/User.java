package com.service.auth.domain;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
// TO DO: connect with database table
public class User {
    private String id;

    private String email;

    private String password;

    private String name;

    private String cpf;

    private Date birthday;

    private boolean isActive;

    private String token;

    private Date createdAt;

    private Date updatedAt;
}
