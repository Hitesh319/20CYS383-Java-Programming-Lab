package com.hitesh.amrita;

import java.io.*;
import java.net.Socket;

abstract class File_Client_Transfer {
    abstract void saveFile(byte[] fileData, String filename);
    abstract void sendFile(String filename);
}

interface File_Client_Transfer_Listener {
    void onFileSent(String filename);
    void onFileSaved(String filename);
}

// com.hitesh.amrita.FileTransferClient class
class FileTransferClient extends File_Client_Transfer {
    private FileTransferListener listener;


    void saveFile(byte[] fileData, String filename) {
    }


    void sendFile(String filename) {
        // Implement file sending using socket programming
        try {
            Socket socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            File file = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            outputStream.close();
            socket.close();

            // Notify listener
            if (listener != null) {
                listener.onFileSent(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setListener(FileTransferListener listener) {
        this.listener = listener;
    }
}
public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        String filename = "C:\\Users\\hites\\Desktop\\Sent.txt";
        String serverFilename = "C:\\Users\\hites\\Desktop\\recieved.txt";

        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(serverFilename);
            FileInputStream fileInputStream = new FileInputStream(filename);
            OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            fileInputStream.close();
            socket.close();

            System.out.println("File sent successfully: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}