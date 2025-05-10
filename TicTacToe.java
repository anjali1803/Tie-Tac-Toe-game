package TIC_TAC_TOE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 580;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turn = 0;

    int scoreX = 0;
    int scoreO = 0;

    public TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("TIC-TAC-TOE");
        textLabel.setOpaque(true);

        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        updateScoreLabel();

        textPanel.setLayout(new GridLayout(2, 1));
        textPanel.add(textLabel);
        textPanel.add(scoreLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.DARK_GRAY);
        frame.add(boardPanel, BorderLayout.CENTER);

        controlPanel.setLayout(new FlowLayout());
        JButton restartButton = new JButton("Start Again");
        JButton resetScoreButton = new JButton("Reset Scores");

        controlPanel.add(restartButton);
        controlPanel.add(resetScoreButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        restartButton.addActionListener(e -> resetBoard(false)); // keep scores
        resetScoreButton.addActionListener(e -> resetBoard(true)); // reset scores

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton clickedTile = (JButton) e.getSource();
                        if (clickedTile.getText().equals("")) {
                            clickedTile.setText(currentPlayer);
                            turn++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn");
                            }
                        }
                    }
                });
            }
        }
    }

    void updateScoreLabel() {
        scoreLabel.setText("Score - X: " + scoreX + " | O: " + scoreO);
    }

    void resetBoard(boolean resetScores) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.DARK_GRAY);
                board[r][c].setForeground(Color.WHITE);
            }
        }
        gameOver = false;
        turn = 0;
        currentPlayer = playerX;
        textLabel.setText("TIC-TAC-TOE");

        if (resetScores) {
            scoreX = 0;
            scoreO = 0;
        }
        updateScoreLabel();
    }

    void checkWinner() {
        // Check rows
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().equals("") &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0].getText());
                for (int i = 0; i < 3; i++) setWinnerTile(board[r][i]);
                return;
            }
        }

        // Check columns
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().equals("") &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c].getText());
                for (int i = 0; i < 3; i++) setWinnerTile(board[i][c]);
                return;
            }
        }

        // Check diagonal
        if (!board[0][0].getText().equals("") &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0].getText());
            for (int i = 0; i < 3; i++) setWinnerTile(board[i][i]);
            return;
        }

        // Check anti-diagonal
        if (!board[0][2].getText().equals("") &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2].getText());
            setWinnerTile(board[0][2]);
            setWinnerTile(board[1][1]);
            setWinnerTile(board[2][0]);
            return;
        }

        if (turn == 9) {
            setTie();
        }
    }

    void setWinner(String winner) {
        gameOver = true;
        textLabel.setText(winner + " is the winner!");

        if (winner.equals(playerX)) scoreX++;
        else if (winner.equals(playerO)) scoreO++;

        updateScoreLabel();
    }

    void setWinnerTile(JButton tile) {
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.GRAY);
    }

    void setTie() {
        textLabel.setText("It's a tie!");
        gameOver = true;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setForeground(Color.ORANGE);
                board[r][c].setBackground(Color.GRAY);
            }
        }
    }

    // public static void main(String[] args) {
    //     new TicTacToe();
    // }
}
