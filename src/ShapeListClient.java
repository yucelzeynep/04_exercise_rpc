import java.rmi.Naming;
import java.util.Vector;

public class ShapeListClient {
    public static void main(String[] args) {
        try {
            ShapeList shapeList = (ShapeList) Naming.lookup("//localhost/ShapeList");
            Vector<Shape> shapes = shapeList.allShapes();
            System.out.println("Shapes received: " + shapes.size());
            int version = shapeList.getVersion();
            System.out.println("Version: " + version);
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
