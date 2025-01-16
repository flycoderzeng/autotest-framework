package test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;
import java.io.OutputStream;

public class SuRootTest {

    public static final int SESSION_TIMEOUT = 60000;

    public static final int CHANNEL_TIMEOUT = 60*20*1000;

    public static void main(String[] args) throws Exception {
        String host = "192.168.23.49";
        String user = "xing";
        String password = "olym9918";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setTimeout(SESSION_TIMEOUT);
            session.setPassword(password);

            // Disable strict host key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            Channel channel = session.openChannel("shell");
            channel.connect(CHANNEL_TIMEOUT);

            InputStream inputStream = channel.getInputStream();
            OutputStream outputStream = channel.getOutputStream();

            // Send commands
            sendCommand(outputStream, "ls -lh");
            // Read the output
            readOutput(inputStream);
            sendCommand(outputStream, "whoami");

            // Read the output
            readOutput(inputStream);
            sendCommand(outputStream, "sudo su - root");

            // Read the output
            readOutput(inputStream);
            sendCommand(outputStream, "123456");

            // Read the output
            readOutput(inputStream);
            sendCommand(outputStream, "whoami");

            // Read the output
            readOutput(inputStream);
            sendCommand(outputStream, "ls -lh");

            // Read the output
            readOutput(inputStream);

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(OutputStream outputStream, String command) throws Exception {
        outputStream.write((command + "\n").getBytes());
        outputStream.flush();
        Thread.sleep(1000); // Wait for command to execute (adjust as needed)
    }

    private static void readOutput(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while (inputStream.available() > 0 && (bytesRead = inputStream.read(buffer)) > 0) {
            System.out.print(new String(buffer, 0, bytesRead));
        }
    }
}
