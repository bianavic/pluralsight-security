package com.example.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "users")
@Data
public class Authority {

    @Id
    @GeneratedValue
    private Long id;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(String authority, Set<User> users) {
        this.authority = authority;
        this.users.addAll(users);
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.PERSIST})
    @JoinTable(name = "authority_user",
            joinColumns = @JoinColumn (name = "authority_id"),
            inverseJoinColumns = @JoinColumn (name = "user_id"))
    private List<User> users = new ArrayList<>();

}
