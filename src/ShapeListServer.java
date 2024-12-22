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
