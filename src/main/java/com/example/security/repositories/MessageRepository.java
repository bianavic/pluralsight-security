package com.example.security.repositories;

import com.example.security.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.security.RolesAllowed;

public interface MessageRepository extends JpaRepository<Message, Long> {

    String QUERY = "select m from Message m where m.id = ?1";

    @Query(QUERY)
    @RolesAllowed("ROLE_ADMIN")
    Message findByIdRolesAllowed(Long id);

    @Query(QUERY)
    @Secured("ROLE_ADMIN")
    Message findByIdSecured(Long id);
}
