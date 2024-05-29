import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class SudokuServer extends UnicastRemoteObject implements SudokuIntrfc {
  protected SudokuServer() throws RemoteException {
    super();
  }

  @Override
  public int[][] generatePuzzle() throws RemoteException {
    int[][] board = {
        { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
        { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
        { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
        { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
        { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
        { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
        { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
        { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
        { 0, 0, 0, 0, 8, 0, 0, 7, 9 }
    };
    return board;
  }

  @Override
  public boolean checkSolution(int board[][]) throws RemoteException {
    int[][] solut = {
        { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
        { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
        { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
        { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
        { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
        { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
        { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
        { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
        { 3, 4, 5, 2, 8, 6, 1, 7, 9 }
    };
    for (int i = 0; i < solut.length; i++) {
      for (int j = 0; j < solut.length; j++) {
        if (board[i][j] != solut[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String args[]) {
    try {
      SudokuIntrfc sdk = new SudokuServer();
      Registry rgstr = LocateRegistry.createRegistry(1099);
      rgstr.rebind("Sudoku", sdk);
      System.out.println("Server is ready.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
