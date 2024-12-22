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
