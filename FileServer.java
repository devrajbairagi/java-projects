import java.io.*;
import java.net.*;

public class FileServer {
    private static final int PORT = 5000;
    private static final String SAVE_DIR = "received_files/"; // Directory to save received files

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT + "...");

            while (true) {
                Socket socket = serverSocket.accept(); // Accept client connection
                System.out.println("Client connected: " + socket.getInetAddress());

                // Handle client file transfer in a new thread
                new Thread(new FileReceiver(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Thread to handle file transfer from a client
class FileReceiver implements Runnable {
    private Socket socket;

    public FileReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream)
        ) {
            // Read file name from client
            String fileName = dataInputStream.readUTF();
            System.out.println("Receiving file: " + fileName);

            // Read file data and save it
            File saveDir = new File("received_files");
            if (!saveDir.exists()) saveDir.mkdir(); // Create directory if not exists
            
            File file = new File(saveDir, fileName);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("File received and saved: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // Close connection
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
