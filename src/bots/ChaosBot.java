/**
 * 
 */
package bots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Andre
 *
 */
public class ChaosBot implements MatchFourBot {

    private char[][] board;
    private char icon;
    private char enemyIcon;
    private int counter = 0;

    private HashMap<Integer, List<Integer>> values = new HashMap<>();

    @Override
    public int turn(char[][] board, char icon) {
        for (int i = 0; i < 7; i++) {
            List<Integer> values = new ArrayList<>();
            this.values.put(i, values);
        }
        this.board = board;
        this.icon = icon;
        if (this.icon == 'X') {
            this.enemyIcon = 'C';
        } else {
            this.enemyIcon = 'X';
        }
        int col = this.checkWinProCol(board);
        if (col != -1) {
            return col;
        }
        int sum = 0;
        int key = 0;
        col = 0;
        for (List<Integer> values : this.values.values()) {
            int sumOfValue = values.stream().reduce(0, (subtotal, element) -> subtotal + element);
            if (values.stream().reduce(0, (subtotal, element) -> subtotal + element) > sum) {
                sum = sumOfValue;
                col = key;
            }
            key++;
        }

        return col;
    }

    private int checkWinProCol(char[][] board) {
        for (int col = 0; col < board.length; col++) {
            int row = this.placeStoneInCol(col);
            if (row != -1) {
                if (this.checkWin(row, col, this.icon)) {
                    return col;
                }
                this.board[row][col] = this.enemyIcon;
                if (this.checkWin(row, col, this.enemyIcon)) {
                    return col;
                }
                this.board[row][col] = ' ';
            }
        }
        return -1;
    }

    private boolean checkWin(int row, int col, char icon) {
        if (this.checkCol(col, row, icon) || this.checkRow(col, icon) || this.checkRightDown(row, col, icon)
                || this.checkLeftDown(row, col, icon)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLeftDown(int row, int col, char icon) {
        int x = row;
        int y = col;
        while (x > 0 && y < 6) {
            x--;
            y++;
        }
        while (x < 7 && y > 0) {
            if (this.board[x++][y--] == icon) {
                this.counter++;
                if (this.counter == 4) {
                    return true;
                }
                if (this.counter == 3 && icon == this.icon) {
                    this.values.get(col).add(1);
                }
            } else {
                this.counter = 0;
            }
        }
        this.counter = 0;
        return false;
    }

    private boolean checkRightDown(int row, int col, char icon) {
        int x = row;
        int y = col;
        while (x > 0 && y > 0) {
            x--;
            y--;
        }
        while (x < 7 && y < 7) {
            if (this.board[x++][y++] == icon) {
                this.counter++;
                if (this.counter == 4) {
                    return true;
                }
                if (this.counter == 3 && icon == this.icon) {
                    this.values.get(col).add(1);
                }
            } else {
                this.counter = 0;
            }
        }
        this.counter = 0;
        return false;
    }

    private boolean checkRow(int col, char icon) {
        for (int x = 0; x < this.board.length; x++) {
            if (this.board[x][col] == icon) {
                this.counter++;
                if (this.counter == 4) {
                    return true;
                }
                if (this.counter == 3 && icon == this.icon) {
                    this.values.get(col).add(1);
                }
            } else {
                this.counter = 0;
            }
        }
        this.counter = 0;
        return false;
    }

    private boolean checkCol(int col, int row, char icon) {
        for (int x = 0; x < this.board.length; x++) {
            if (this.board[row][x] == icon) {
                this.counter++;
                if (this.counter == 4) {
                    return true;
                }
                if (this.counter == 3 && icon == this.icon) {
                    this.values.get(col).add(1);
                }
            } else {
                this.counter = 0;
            }
        }
        this.counter = 0;
        return false;
    }

    private int placeStoneInCol(int col) {
        for (int row = this.board.length - 1; row > 0; row--) {
            if (this.board[row][col] == ' ') {
                this.board[row][col] = this.icon;
                return row;
            }
        }
        return -1;
    }

    @Override
    public void won(boolean won) {

    }

    @Override
    public String getBotName() {

        return "Chaos Bot";
    }

}
