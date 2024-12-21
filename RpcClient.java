// RpcClient.java
import java.io.*;
import java.net.*;

public class RpcClient {
    public static void main(String[] args) {
        try {
            // Connect to the server on localhost and port 9090
            Socket socket = new Socket("localhost", 9090);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send two numbers to the server
            out.writeInt(5);
            out.writeInt(10);
            out.flush();

            // Get the result from the server
            int result = in.readInt();
            System.out.println("Result of addition: " + result);

            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
