import java.io.*;
import java.net.*;

public class FileClient {
    private static final String SERVER_IP = "127.0.0.1"; // Change to server's IP if needed
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        String filePath = "test.txt"; // Change to the file you want to send

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             FileInputStream fileInputStream = new FileInputStream(filePath);
             OutputStream outputStream = socket.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            
            File file = new File(filePath);
            System.out.println("Sending file: " + file.getName());

            // Send file name first
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.flush();

            // Send file data in chunks
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
