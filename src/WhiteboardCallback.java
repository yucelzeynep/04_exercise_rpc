import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WhiteboardCallback extends Remote {
    void callback(int version) throws RemoteException;
}
