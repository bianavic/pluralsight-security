package com.example.security;

import com.example.security.models.Authority;
import com.example.security.models.Message;
import com.example.security.models.User;
import com.example.security.repositories.AuthorityRepository;
import com.example.security.repositories.MessageRepository;
import com.example.security.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.function.Function;

@Transactional
@Component
@Log4j2
public class Runner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final MessageRepository messageRepository;
    private final UserDetailsService userDetailsService;

    public Runner(UserRepository userRepository, AuthorityRepository authorityRepository, MessageRepository messageRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.messageRepository = messageRepository;
        this.userDetailsService = userDetailsService;
    }

    private void authenticate(String cpf) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(cpf);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // install some data

        Authority user = this.authorityRepository.save(new Authority("USER")),
                admin = this.authorityRepository.save(new Authority("ADMIN"));

        User maria = this.userRepository.save(new User("11111111111","maria@maria.com", "password", admin, user));
        Message messageForMaria = this.messageRepository.save(new Message("hi Maria!", maria));

        User joao = this.userRepository.save(new User("22222222222","joao@joao.com", "password", user));
        Message messageForJoao = this.messageRepository.save(new Message("hi Joao!", joao));

        log.info("maria: " + maria.toString());
        log.info("joao: " + joao.toString());

        attemptAccess(maria.getCpf(), joao.getCpf(), messageForJoao.getId(), this.messageRepository::findByIdRolesAllowed);

        attemptAccess(maria.getCpf(), joao.getCpf(), messageForJoao.getId(), this.messageRepository::findByIdSecured);

        attemptAccess(maria.getCpf(), joao.getCpf(), messageForJoao.getId(), this.messageRepository::findByIdPreAuthorize);

        // attemptAccess(maria.getCpf(), joao.getCpf(), messageForJoao.getId(), this.messageRepository::findByIdPostAuthorize);
    }

    private void attemptAccess(String adminUser,
                               String regularUser,
                               Long msgId,
                               Function<Long, Message> fn) {
        authenticate(adminUser);
        log.info("result for maria:" + fn.apply(msgId));

        try {
            authenticate(regularUser);
            log.info("result for joao:" + fn.apply(msgId));
        } catch (Throwable ex) {
            log.error("oops! couldn't obtain the result for Joao");
        }

    }
}
