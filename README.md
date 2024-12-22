
### Project Folder Structure
1. **Create Project Folder**
   - Open your terminal (e.g. zsh) and navigate to the directory where you want to create your project.
   - Create a folder for your project:
     ```bash
     mkdir exercise_rmi
     cd exercise_rmi
     ```

2. **Create Subfolders**
   - Create folders for your source code and compiled classes:
     ```bash
     mkdir src
     mkdir bin
     ```

### Naming Files
3. **Create Interface Files**
   - In the `src` folder, create the following files:
     - `Shape.java`
     - `ShapeList.java`
     - `GraphicalObject.java`
     - `WhiteboardCallback.java`

4. **Create Servant Classes**
   - Create the following server implementation files in the `src` folder:
     - `ShapeListServant.java`
     - `ShapeServant.java`
     - `ShapeListServer.java`

5. **Create Client Class**
   - Create the client file in the `src` folder:
     - `ShapeListClient.java`

### Code Structure
6. **Insert Code into Files**
   - Here’s the code for each file:

**Shape.java**
```java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Shape extends Remote {
    int getVersion() throws RemoteException;
    GraphicalObject getAllState() throws RemoteException;
}
```

**ShapeList.java**
```java
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ShapeList extends Remote {
    Shape newShape(GraphicalObject g) throws RemoteException;
    Vector<Shape> allShapes() throws RemoteException;
    int getVersion() throws RemoteException;
}
```

**GraphicalObject.java**
```java
import java.io.Serializable;

public class GraphicalObject implements Serializable {
    // Add properties for type, position, color, etc.
    private String shapeType;
    // Additional properties...

    public GraphicalObject(String shapeType) {
        this.shapeType = shapeType;
    }

    // Getters and Setters...
}
```

**WhiteboardCallback.java**
```java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WhiteboardCallback extends Remote {
    void callback(int version) throws RemoteException;
}
```

**ShapeListServant.java**
```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ShapeListServant extends UnicastRemoteObject implements ShapeList {
    private Vector<Shape> theList;
    private int version;

    public ShapeListServant() throws RemoteException {
        theList = new Vector<>();
        version = 0;
    }

    public Shape newShape(GraphicalObject g) throws RemoteException {
        version++;
        Shape s = new ShapeServant(g, version);
        theList.add(s);
        return s;
    }

    public Vector<Shape> allShapes() throws RemoteException {
        return theList;
    }

    public int getVersion() throws RemoteException {
        return version;
    }
}
```

**ShapeServant.java**
```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShapeServant extends UnicastRemoteObject implements Shape {
    private GraphicalObject graphicalObject;
    private int version;

    public ShapeServant(GraphicalObject g, int version) throws RemoteException {
        this.graphicalObject = g;
        this.version = version;
    }

    public int getVersion() throws RemoteException {
        return version;
    }

    public GraphicalObject getAllState() throws RemoteException {
        return graphicalObject;
    }
}
```

**ShapeListServer.java**
```java
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ShapeListServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Create RMI registry on port 1099
            ShapeList shapeList = new ShapeListServant();
            Naming.rebind("//localhost/ShapeList", shapeList);
            System.out.println("ShapeList server ready");
        } catch (Exception e) {
            System.out.println("ShapeList server error: " + e.getMessage());
        }
    }
}
```

**ShapeListClient.java**
```java
import java.rmi.Naming;
import java.util.Vector;

public class ShapeListClient {
    public static void main(String[] args) {
        try {
            ShapeList shapeList = (ShapeList) Naming.lookup("//localhost/ShapeList");
            Vector<Shape> shapes = shapeList.allShapes();
            System.out.println("Shapes received: " + shapes.size());
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
```

### Setting Up the Environment
7. **Compile the Code**
   - Navigate to the `src` folder and compile all the Java files:
     ```bash
     cd src
     javac -d ../bin *.java
     ```

8. **Run the RMI Registry**
   - In a separate terminal (keeping the `exercise_rmi` project open), run the RMI registry:
     ```bash
     rmiregistry
     ```
If you see a warning, ignore it (Ctrl + C).

9. **Run the Server**
   - In the original terminal, navigate to the `bin` folder and run the server:
     ```bash
     cd ../bin
     java ShapeListServer
     ```

10. **Run the Client**
    - In another terminal, run the client:
      ```bash
      cd ../bin
      java ShapeListClient
      ```

### Testing Locally
- To test the application:
  - When you run the client after the server is running, it should successfully connect to the server and retrieve the list of shapes (initially empty).
  - You can add print statements in the server and client code to track interactions between them.
  - You can also modify the client to add shapes by invoking the `newShape` method on the server to see if the shapes are being stored and retrieved correctly.
