package com.example.security.repositories;

import com.example.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByCpf(String cpf);
}
