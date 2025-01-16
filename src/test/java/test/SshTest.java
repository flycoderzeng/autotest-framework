package test;

import com.autotest.framework.utils.SshUtils;
import com.jcraft.jsch.Session;

public class SshTest {
    public static final String SUCCESS = "SUCCESS";
    public static void main(String[] args) {
        Session session = null;
        try {
            session = SshUtils.getSession("192.168.136.22", "123456");
            String cmdResult = SshUtils.execCmd(session, "/usr/bin/test -d /data/cipher_install && /usr/bin/echo \"SUCCESS\"");
            System.out.println("aaa: " + cmdResult);
            if (!cmdResult.contains(SUCCESS)) {
                throw new RuntimeException("创建远程工作目录: 失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
