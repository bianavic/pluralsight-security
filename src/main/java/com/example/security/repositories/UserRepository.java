package com.example.security.repositories;

import com.example.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByCpf(String cpf);
}
