package com.chris.dev.SftpServerApplication.service.impl;

import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PasswordAuthenticatorImpl implements PasswordAuthenticator {

    //TODO read and set properties from yaml
    private Environment env;

    @Autowired
    public PasswordAuthenticatorImpl(Environment env){
        this.env = env;
    }

    //TODO Implement Auth-Strategy
    @Override
    public boolean authenticate(String s, String s1, ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
        return true;
    }
}
