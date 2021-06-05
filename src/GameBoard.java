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

    public char[][] getBoard() {
        return board.clone();
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

    public boolean checkForWinn() {

        for(int i = 0; i < this.WIDTH; i++) {
            for (int j = 0; j < this.HEIGHT; j++) {
                if(this.board[j][i] != ' ' && (checkForLeftWin(j, i) || checkForRightWin(j, i) || checkForUpperLeftWin(j, i) || checkForUpperRightWin(j, i) || checkForVerticalWin(j, i))) {
                    this.winningIcon = this.board[j][i];
                    this.finished = true;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkForLeftWin(int height, int width) {
        if (width <= 2) {
            return false;
        }
        for (int i = width - 1; i >= width - 3; i--) {
            if (this.board[height][i] != this.board[height][width]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForRightWin(int height, int width) {
        if (width >= this.WIDTH - 4) {
            return false;
        }
        for (int i = width + 1; i <= width + 3; i++) {
            if (this.board[height][i] != this.board[height][width]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForUpperLeftWin(int height, int width) {
        if (width <= 2 || height <= 2) {
            return false;
        }
        for (int i = width - 1, j = height - 1; i >= width - 3 && j >= height -3; i--, j--) {
            if (this.board[j][i] != this.board[height][width]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForUpperRightWin(int height, int width) {
        if (width >= this.WIDTH - 4 || height <= 2) {
            return false;
        }
        for (int i = width + 1, j = height - 1; i <= width + 3 && j >= height -3; i++, j--) {
            if (this.board[j][i] != this.board[height][width]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForVerticalWin(int height, int width) {
        if (height <= 2) {
            return false;
        }
        for (int i = height - 1; i >= height -3; i--) {
            if (this.board[i][width] != this.board[height][width]) {
                return false;
            }
        }
        return true;
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






    /*
char []   []
     /     |
    V      |
    0      |
    1      |
    2      |
    3      |
    4      |
    5      |
    6      V
      0 1 2 3 4 5 6
     */

}
