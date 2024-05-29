import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SudokuIntrfc extends Remote {
  int[][] generatePuzzle() throws RemoteException;
  boolean checkSolution(int board[][]) throws RemoteException;
}
