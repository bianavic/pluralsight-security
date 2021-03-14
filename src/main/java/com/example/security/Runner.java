package com.example.security;

import com.example.security.models.Authority;
import com.example.security.models.Message;
import com.example.security.models.User;
import com.example.security.repositories.AuthorityRepository;
import com.example.security.repositories.MessageRepository;
import com.example.security.repositories.UserRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static java.rmi.server.LogStream.log;

@Transactional
@Component
@Log4j2
public class Runner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final MessageRepository messageRepository;

    public Runner(UserRepository userRepository, AuthorityRepository authorityRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // install some data

        Authority user = this.authorityRepository.save(new Authority("USER")),
                admin = this.authorityRepository.save(new Authority("ADMIN"));

        User maria = this.userRepository.save(new User("11111111111","maria@maria.com", "passowrd", admin, user));
        Message messageForMaria = this.messageRepository.save(new Message("hi Maria!", maria));

        User joao = this.userRepository.save(new User("22222222222","joao@joao.com", "passowrd", user));

        log.info("maria: " + maria.toString());
        log.info("joao " + joao.toString());

    }
}
