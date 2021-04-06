package com.chris.dev.SftpServerApplication.service.impl;

import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.FileHandle;
import org.apache.sshd.server.subsystem.sftp.Handle;
import org.apache.sshd.server.subsystem.sftp.SftpEventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SftpEventListenerImpl implements SftpEventListener {

    @Override
    public void opening(ServerSession session, String remoteHandle, Handle localHandle) throws IOException {

    }

    @Override
    public void writing(
            ServerSession session, String remoteHandle, FileHandle localHandle,
            long offset, byte[] data, int dataOffset, int dataLen)
            throws IOException {
    }

}
