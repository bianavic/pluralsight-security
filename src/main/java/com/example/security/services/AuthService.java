/**
 *

package com.example.security.services;

import com.example.security.models.Message;
import com.example.security.models.User;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service("authz")
public class AuthService {

    public boolean check(Message msg, User user) {
        log.info("checking " + user.getCpf() + "..");
        return msg.getTo().getId().equals(user.getId());
    }
}
 */