package com.chris.dev.SftpServerApplication.configuration;

import com.chris.dev.SftpServerApplication.service.impl.FileSystemFactoryImpl;
import com.chris.dev.SftpServerApplication.service.impl.PasswordAuthenticatorImpl;
import com.chris.dev.SftpServerApplication.service.impl.SftpEventListenerImpl;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.util.threads.CloseableExecutorService;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.scp.ScpCommandFactory;
import org.apache.sshd.server.shell.InteractiveProcessShellFactory;
import org.apache.sshd.server.subsystem.sftp.SftpEventListener;
import org.apache.sshd.server.subsystem.sftp.SftpFileSystemAccessor;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.Collections;

@Configuration
public class SftpServerConfiguration {

    private Environment environment;
    private PasswordAuthenticator passwordAuthenticator;
    private SftpEventListener sftpEventListener;

    @Autowired
    public SftpServerConfiguration(Environment environment, PasswordAuthenticatorImpl passwordAuthenticator, SftpEventListenerImpl sftpEventListener){
        this.environment = environment;
        this.passwordAuthenticator = passwordAuthenticator;
        this.sftpEventListener = sftpEventListener;
    }

    /*@Bean
    public FileSystemFactoryImpl getfileSystemFactory(){
        return new FileSystemFactoryImpl("");
    }*/

    @Bean
    public SshServer configureServer(FileSystemFactoryImpl fileSystemFactory){
        SftpFileSystemAccessor sftpFileSystemAccessor;
        SftpSubsystemFactory factory = new SftpSubsystemFactory.Builder()
                .build();
        factory.addSftpEventListener(sftpEventListener);
        SshServer sshServer = SshServer.setUpDefaultServer();
        //File file = new File(System.getProperty("user.home"));
        //sshServer.setFileSystemFactory(new VirtualFileSystemFactory(file.toPath()));
        sshServer.setFileSystemFactory(fileSystemFactory);
        sshServer.setPort(2222);
        sshServer.setPasswordAuthenticator(this.passwordAuthenticator);
        sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("hostkey.ser").toPath()));
        sshServer.setShellFactory(new InteractiveProcessShellFactory());
        sshServer.setCommandFactory(new ScpCommandFactory());
        sshServer.setSubsystemFactories(Collections.singletonList(factory));
        return sshServer;
    }

}
