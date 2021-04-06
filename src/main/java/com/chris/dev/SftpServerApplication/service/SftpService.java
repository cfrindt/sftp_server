package com.chris.dev.SftpServerApplication.service;

import org.apache.sshd.server.SshServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Service
public class SftpService{

    private SshServer sshserver;

    @Autowired
    public SftpService(SshServer sshServer){
        this.sshserver = sshServer;
    }

    @PostConstruct
    private void initServer(){
        try {
            sshserver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void disconnectServer(){
        try {
            this.sshserver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
