package com.example.security.models;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "authorities")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String cpf;
    private String email;
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<Authority> authorities = new ArrayList<>();


    public User(String cpf, String email, String password, Set<Authority> authorities) {
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.authorities.addAll(authorities);
    }

    public User(String cpf, String email, String password) {
        this (cpf, email, password, new HashSet<>());
    }

    public User(String cpf, String email, String password, Authority ... authorities) {
        this (cpf, email, password, new HashSet<>(Arrays.asList(authorities)));
    }
}
