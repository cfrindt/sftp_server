package com.chris.dev.SftpServerApplication.service.impl;

import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.common.file.root.RootedFileSystemProvider;
import org.apache.sshd.common.session.SessionContext;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;

@Component
public class FileSystemFactoryImpl implements FileSystemFactory {

    private String rootDir;
    private Path userHomeDir;

    public FileSystemFactoryImpl(){

    }

    @Override
    public Path getUserHomeDir(SessionContext sessionContext) throws IOException {
        if(this.rootDir == null){
            this.rootDir = System.getProperty("user.home");
        }
        File file = new File(rootDir+"/"+sessionContext.getUsername());
        if(!Files.exists(file.toPath())){
            file.mkdir();
        }
        return file.toPath();
    }

    @Override
    public FileSystem createFileSystem(SessionContext sessionContext) throws IOException {
        Path dir = this.getUserHomeDir(sessionContext);
        if (dir == null) {
            throw new InvalidPathException(sessionContext.getUsername(), "Cannot resolve home directory");
        } else {
            return (new RootedFileSystemProvider()).newFileSystem(dir, Collections.emptyMap());
        }
    }
}
