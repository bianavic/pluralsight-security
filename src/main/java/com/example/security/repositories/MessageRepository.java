package com.example.security.repositories;

import com.example.security.models.Message;
import com.example.security.models.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

public interface MessageRepository extends JpaRepository<Message, Long> {

    String QUERY = "select m from Message m where m.id = ?1";

    @Query(QUERY)
    @RolesAllowed("ROLE_ADMIN")
    Message findByIdRolesAllowed(Long id);

    @Query(QUERY)
    @Secured("ROLE_ADMIN")
    Message findByIdSecured(Long id);

    @Query(QUERY)
    @PreAuthorize("hasRole('ADMIN')")
    Message findByIdPreAuthorize(Long id);

    @Query(QUERY)
    @PostAuthorize("@authz.check(returnObject, principal?.user)")
    Message findByIdPostAuthorize(Long id);

}

@Log4j2
@Service("authz")
class AuthService {

    public boolean check(Message msg, User user) {
        log.info("checking " + user.getCpf() +"...");
        return msg.getTo().getId().equals(user.getId());
    }
}