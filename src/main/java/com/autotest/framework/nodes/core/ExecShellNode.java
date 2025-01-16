package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Data
public class ExecShellNode extends StepNode {
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String cmd;

    public ExecShellNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                         String ip, Integer port, String username, String password, String cmd) {
        super(stepSeq, stepName, autoTestContext);
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.cmd = cmd;
    }

    @Override
    public void run() throws Exception {
        log.info("===========>执行步骤: {}", stepName);
        if (skip()) {
            return;
        }
        log.info("ip: {}, port: {}, username: {}, password: {}", ip, port, username, password);
        log.info("cmd: {}", cmd);
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, ip, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(30000);
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(cmd);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        String result = IOUtils.toString(in, StandardCharsets.UTF_8);
        log.info("命令执行结果: {}", result);
        channelExec.disconnect();
        session.disconnect();
        log.info("命令执行结果: {}", result);
    }
}
