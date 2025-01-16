package com.autotest.framework.utils;

import cn.hutool.extra.ssh.ChannelType;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SshUtils {
    public static final int SESSION_TIMEOUT = 60000;

    public static final int CHANNEL_TIMEOUT = 60*20*1000;

    public static final String SUCCESS = "SUCCESS";
    public static final int MAX_CONNECT_NUM = 100;

    public static Session getSession(String host, String password) throws Exception {
        return getSession(host, "root", password);
    }

    public static Session getSession(String host, String username, String password) throws Exception {
        JSch jsch = new JSch();
        Session session = null;
        int i = 1;
        while (i < MAX_CONNECT_NUM) {
            log.info("第{}次获取session", i);
            try {
                session = jsch.getSession(username, host, 22);
                session.setPassword(password);
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                session.setConfig(sshConfig);
                session.setTimeout(SESSION_TIMEOUT);
                session.connect();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                i++;
                TimeUnit.SECONDS.sleep(3);
                continue;
            }
        }

        return session;
    }

    public static void reboot(String host, String password) throws Exception {
        Session session = null;
        try {
            session = SshUtils.getSession(host, password);
            execCmd(session, "shutdown -r now");
        } finally {
            if (session != null) {
                try {
                    session.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        TimeUnit.SECONDS.sleep(10);
    }


    public static void getOutput(InputStream inputStream, StringBuilder sb) throws IOException {
        byte[] buf = new byte[1024];
        while (inputStream.available() > 0) {
            int len = inputStream.read(buf, 0, buf.length);
            if (len < 0) {
                break;
            }
            String str = new String(buf, 0, len, StandardCharsets.UTF_8);
            System.out.print(str);
            sb.append(str);
        }
    }

    public static String execCmd(Session session, String command) {
        command = "source /etc/profile;source ~/.bashrc;" + command;
        log.info("执行命令: {}\n", command);
        ChannelExec channelExec = null;
        InputStream stdoutInputStream = null;
        InputStream stderrInputStream = null;
        StringBuilder stdoutSb = new StringBuilder();
        StringBuilder stderrSb = new StringBuilder();
        try {
            channelExec = (ChannelExec) session.openChannel(ChannelType.EXEC.getValue());
            channelExec.setCommand(command);
            channelExec.connect(CHANNEL_TIMEOUT);
            stdoutInputStream = channelExec.getInputStream();
            stderrInputStream = channelExec.getErrStream();
            Thread.sleep(500);
            int i = 0;
            while (true && i < 600) {
                getOutput(stdoutInputStream, stdoutSb);
                getOutput(stderrInputStream, stderrSb);
                if (channelExec.isClosed()) {
                    if (stdoutInputStream.available() > 0 || stderrInputStream.available() > 0) continue;
                    //log.info("exit-status: " + channelExec.getExitStatus());
                    break;
                }
                Thread.sleep(1000);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stdoutInputStream != null) {
                try {
                    stdoutInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (stderrInputStream != null) {
                try {
                    stderrInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (channelExec != null) {
                try {
                    channelExec.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return stdoutSb + "\n" + stderrSb;
    }

    public static boolean ping(String host) throws Exception {
        String cmd = "ping " + host + " -n 30 -w 50";
        log.info(cmd);
        Process pro = Runtime.getRuntime().exec(cmd);
        BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));
        String result = "";
        String line = null;
        while((line = buf.readLine()) != null) {
            log.info(line);
            result += line;
        }
        pro.waitFor();
        log.info(host + " : " + pro.exitValue());
        if(result.contains("已发送 = 30，已接收 = 30")) {
            return true;
        }
        return false;
    }

    public static boolean upload(Session session, File uploadFile, String remoteDirectory, String fileName) {
        String cmdResult = SshUtils.execCmd(session, "test -e " + remoteDirectory + "/" + fileName + " && echo 'SUCCESS'");
        if (cmdResult.contains(SUCCESS)) {
            log.info("文件: {} 已存在 {}", fileName, remoteDirectory);
            return true;
        }
        log.info("upload 文件: {}", uploadFile.getAbsolutePath());
        FileInputStream fileInputStream = null;
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            log.info("start upload channel file {}!", uploadFile.getAbsolutePath());
            channelSftp.cd(remoteDirectory);
            fileInputStream = new FileInputStream(uploadFile);
            channelSftp.put(fileInputStream, fileName);
            return true;
        } catch (Exception e) {
            log.error("SFTPClient upload file failed, {}", e.getMessage(), e);
            return false;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    log.error("File output stream close error, ", e);
                }
            }
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
        }
    }
}
