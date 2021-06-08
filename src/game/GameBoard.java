package game;

import java.util.Arrays;
import java.util.Collections;

public class GameBoard {

    private char[][] board;
    private final int HEIGHT = 7;
    private final int WIDTH = 7;
    private boolean finished;
    private char winningIcon;

    public GameBoard() {
        this.board = new char[this.HEIGHT][this.WIDTH];
        Arrays.stream(this.board).forEach(e -> Arrays.fill(e, ' '));
        this.finished = false;
        this.winningIcon = ' ';
    }

    /**
     * @return the game board as char array. 0/0 is the top left corner.
     */
    public char[][] getBoard() {
        char[][] boardCopy = new char[board[0].length][];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = board[i].clone();
        }
        return boardCopy;
    }

    public boolean place(char player, int position) {
        if (this.finished || position > this.WIDTH-1) {
            return false;
        }
        for (int i = this.HEIGHT - 1; i >= 0; i--) {
            if (this.board[i][position] == ' ') {
                this.board[i][position] = player;
                checkForWinn();
                return true;
            }
        }
        return false;
    }

    private boolean checkForWinn() {
        int[] dirX = new int[] {1, 1, 0, 1};
        int[] dirY = new int[] {0, 1, 1, -1};

        for (int y = 0; y < this.HEIGHT; y++) {
            for (int x = 0; x < this.WIDTH; x++) {
                if (this.board[y][x] == ' ') {
                    continue;
                }
                for (int dir = 0; dir < dirX.length; dir++) {

                    int step = 1;
                    int dx, dy;
                    while ((dx = x + dirX[dir] * step) < this.WIDTH && dx >= 0
                            && (dy = y + dirY[dir] * step) < this.HEIGHT && dy >= 0
                            && board[y][x] == board[dy][dx]) {
                        if (step++ >= 3) {
                            this.winningIcon = this.board[y][x];
                            this.finished = true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(char[] row : this.board) {
            for (char field : row) {
                sb.append(field).append(" | ");
            }
            sb.delete(sb.lastIndexOf(" | "), sb.length());
            sb.append("\n");
            sb.append(String.join("", Collections.nCopies(this.WIDTH * 4 - 3, "-")));
            sb.append("\n");
        }
        sb.delete(sb.lastIndexOf("\n"), sb.length());
        sb.delete(sb.lastIndexOf("\n"), sb.length());
        return sb.toString();
    }

    public boolean isFinished() {
        return finished;
    }

    public char getWinningIcon() {
        return winningIcon;
    }

}
