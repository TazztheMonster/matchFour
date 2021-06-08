package bots;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Daniel Nitschke
 */
public class RandomColumnFillerBot implements MatchFourBot {
	int column = -1;
	
	@Override
	public int turn(char[][] board, char icon) {
		if (column < 0) {
			column = (int)(Math.random() * board[0].length);
		}
		this.column = getNextColumn(board, column, icon);
		return this.column;
	}

	@Override
	public void won(boolean won) {
		this.column = -1;
	}

	@Override
	public String getBotName() {
		return "Random Column Filler Bot";
	}
	
	public int getNextColumn(char[][] board, int currentIndex, char icon) {
		int maxX = board[0].length;
		List<Integer> xList = new ArrayList<>(maxX);
		for (int i = 0; i < maxX; i++) {
			xList.add(i);
		}
		xList.remove(currentIndex);
		Collections.shuffle(xList);
		xList.add(0, currentIndex);
		for (int x : xList) {
			int count = 0;
			for (int y = 0; y < board.length; y++) {
				if (board[y][x] == icon || board[y][x] == ' ') {
					count++;
					continue;
				}
				break;
			}
			if (count >= 4) {
				return x;
			}
		}
		
		return (int)(Math.random() * maxX);
	}
}