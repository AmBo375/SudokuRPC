import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class SudokuClient {
  private SudokuIntrfc srvc;
  private JTextField[][] cells;
  private JFrame sudoku;

  public SudokuClient() {
    try {
      Registry rgstr = LocateRegistry.getRegistry("localhost", 1099);
      srvc = (SudokuIntrfc) rgstr.lookup("Sudoku");
      initGame();
      initPuzzle();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void initGame() {
    sudoku = new JFrame("Sudoku");
    sudoku.setSize(550, 450);
    sudoku.setLayout(new GridLayout(10, 9));
    cells = new JTextField[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        cells[i][j] = new JTextField();
        sudoku.add(cells[i][j]);
      }
    }
    JButton check = new JButton("Check");
    sudoku.add(check);
    check.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        checkSolution();
      }
    });
    sudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    sudoku.setVisible(true);
  }

  private void initPuzzle() {
    try {
      int[][] puzzle = srvc.generatePuzzle();
      for (int i = 0; i < puzzle.length; i++) {
        for (int j = 0; j < puzzle.length; j++) {
          if (puzzle[i][j] != 0) {
            cells[i][j].setText(String.valueOf(puzzle[i][j]));
            cells[i][j].setEditable(false);
            cells[i][j].setBackground(Color.lightGray);
          } else {
            cells[i][j].setText("");
            cells[i][j].setEditable(true);
            cells[i][j].setBackground(Color.white);
          }
        }
      }

    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  private void checkSolution() {
    int[][] board = new int[9][9];
    try {
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
          String num = cells[i][j].getText();
          board[i][j] = num.isEmpty() ? 0 : Integer.valueOf(num);
        }
      }
      boolean isSolved = srvc.checkSolution(board);
      JOptionPane.showMessageDialog(sudoku, isSolved ? "Congrats!" : "Incorrect!");
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new SudokuClient();
  }
}
