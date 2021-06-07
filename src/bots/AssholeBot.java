package bots;

import java.util.Arrays;

/**
 * 
 * @author Fabian Duerkop
 *
 */
public class AssholeBot implements MatchFourBot {

    private int[][] boardCellValues = new int[7][7];
    private char[][] board = new char[7][7];
    private char myIcon;

    public AssholeBot() {
        
    }

    @Override
    public int turn(char[][] board, char icon) {
        this.board = board;
        this.myIcon = icon;
        for (int i = 0; i < boardCellValues.length; i++) {
            Arrays.fill(this.boardCellValues[i], 0);
        }

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {

                if (isCellMine(icon, x, y)) {
                    
                    checkHorizontal(x, y);
                    checkVertical(x, y);
                }
            }
        }
        blockEnemy();

        // Look up best column to place
        int bestColumn = 0;
        int highestValue = Integer.MIN_VALUE;
        for(int y = 0; y < this.boardCellValues.length; y++)
        {
            for(int x = 0; x < this.boardCellValues[y].length; x++) {
                if(this.boardCellValues[y][x] > highestValue) {
                    bestColumn = x;
                    highestValue = this.boardCellValues[y][x];
                }
            }
        }
        return bestColumn;
    }

    @Override
    public void won(boolean won) {
    }

    @Override
    public String getBotName() {
        return "Asshole Bot";
    }
    
    
    private void checkHorizontal(int x, int y) {
        for(int i = x-1; i > 0 && x - i < 4; i--) {
            if(isCellAvailable(i, y)) {
                int value = -getCellRoundsRequired(i, y);

                value += getMyCellCountHorizontal(i, y);
                
                this.boardCellValues[y][i] += value;
            }
            else {
                continue;
            }
        }
        for(int i = x+1; i < this.board[0].length && i - x < 4; i++) {
            if(isCellAvailable(i, y)) {
                int value = -getCellRoundsRequired(i, y);

                value += getMyCellCountHorizontal(i, y);
                
                this.boardCellValues[y][i] += value;
            }
            else {
                continue;
            }
        }
    }
    
    private void checkVertical(int x, int y) {
        for(int i = y-1; i > 0 && y - i < 4; i--) {
            if(isCellAvailable(x, i)) {
                int value = -getCellRoundsRequired(x, i);
                
                
                value+= getMyCellCountBelow(x, i);
                
                this.boardCellValues[i][x] += value;
            }
            else {
                
                break;
            }
        }

    }
    
    private boolean isCellMine(char icon, int x, int y) {
        boolean retMine = false;
        if (this.board[y][x] == icon) {
            retMine = true;
        }
        return retMine;
    }

    private boolean isCellAvailable(int x, int y) {
        boolean retAvailable = true;
        if (this.board[y][x] != ' ') {
            retAvailable = false;
        }
        return retAvailable;
    }

    private int getCellRoundsRequired(int x, int y) {
        int retRoundsRequired = 0;
        for (int i = y; i < 7; i++) {
            if (this.board[i][x] == ' ') {
                retRoundsRequired++;
            }
        }
        return retRoundsRequired-1;
    }
    
    private int getMyCellCountBelow(int x, int y) {
        int myCellCount = 0;
        for (int i = 6; i > y; i--) {
            if (this.board[i][x] == this.myIcon) {
                myCellCount++;
            } else if (this.board[i][x] != ' ') {
                myCellCount = 0;
            }
        }
        return myCellCount;
    }
    
    private int getMyCellCountHorizontal(int x, int y) {
        int myCellCountRight = 0;
        for (int i = 6; i > x; i--) {
            if (this.board[y][i] == this.myIcon) {
                myCellCountRight++;
            } else if (this.board[y][i] != ' ') {
                myCellCountRight = 0;
            }
        }
        
        int myCellCountLeft = 0;
        for (int i = 0; i < x; i++) {
            if (this.board[y][i] == this.myIcon) {
                myCellCountLeft++;
            } else if (this.board[y][i] != ' ') {
                myCellCountLeft = 0;
            }
        }
        
        return myCellCountRight + myCellCountLeft;
    }
    
    private void blockEnemy() {
        for(int y = 0; y < this.board.length; y++) {
            for(int x = 0; x < this.board[y].length; x++) {
                // check x
                int enemyCellCnt = 0;
                for(int i = -3; i < 4; i++) {
                    if(i + x < 0 || i + x >= this.board[y].length) {
                        continue;
                    }
                    // is enemy cell
                    if(this.board[y][x+i] != this.myIcon && this.board[y][x+i] != ' ') {
                        enemyCellCnt++;
                    } else {
                        enemyCellCnt = 0;
                    }
                    
                    if(enemyCellCnt >= 3 && isCellAvailable(x, y)) {
                        int k = getCellRoundsRequired(x, y);
                        if(Math.abs(k) % 2 == 0) {
                            this.boardCellValues[y][x] = 99;                            
                        }
                    }
                    
                }
                
                enemyCellCnt = 0;
                for(int i = -3; i < 4; i++) {
                    if(i + y < 0 || i + y >= this.board.length) {
                        continue;
                    }
                    // is enemy cell
                    if(this.board[y+i][x] != this.myIcon && this.board[y+i][x] != ' ') {
                        enemyCellCnt++;
                    } else {
                        enemyCellCnt = 0;
                    }
                    
                    if(enemyCellCnt >= 3 && isCellAvailable(x, y)) {
                        this.boardCellValues[y][x] = 99;
                    }
                    
                }
                
                enemyCellCnt = 0;
                for(int i = -3; i < 4; i++) {
                    if(i + y < 0 || i + y >= this.board.length) {
                        continue;
                    } else if( i + x < 0 || i + x >= this.board[i+y].length ) {
                        continue;
                    }
                    
                    // is enemy cell
                    if(this.board[y+i][x+i] != this.myIcon && this.board[y+i][x+i] != ' ') {
                        enemyCellCnt++;
                    } else {
                        enemyCellCnt = 0;
                    }
                    
                    if(enemyCellCnt >= 3 && isCellAvailable(x, y)) {
                        this.boardCellValues[y][x] = 99;
                    }
                }
                
                enemyCellCnt = 0;
                for(int i = -3; i < 4; i++) {
                    if(i + y < 0 || i + y >= this.board.length) {
                        continue;
                    } else if( x-i < 0 || x-i >= this.board[i+y].length ) {
                        continue;
                    }
                    
                    // is enemy cell
                    if(this.board[y+i][x-i] != this.myIcon && this.board[y+i][x-i] != ' ') {
                        enemyCellCnt++;
                    } else {
                        enemyCellCnt = 0;
                    }
                    
                    if(enemyCellCnt >= 3 && isCellAvailable(x, y)) {
                        this.boardCellValues[y][x] = 99;
                    }
                }
            }
        }
    }

}
