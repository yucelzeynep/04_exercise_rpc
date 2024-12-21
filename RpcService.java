// RpcService.java
import java.io.*;
import java.net.*;

public class RpcService {
    // This method will be invoked by the client to add two numbers
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        try {
            // Create a server socket on port 9090
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("RPC Server is running on port 9090...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Create input and output streams
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                // Read the numbers from the client
                int a = in.readInt();
                int b = in.readInt();

                // Create an instance of RpcService to use the add method
                RpcService rpcService = new RpcService();
                int result = rpcService.add(a, b);

                // Send the result back to the client
                out.writeInt(result);
                out.flush();

                // Close the connection
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
