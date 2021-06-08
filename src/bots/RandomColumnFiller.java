package bots;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Daniel Nitschke
 */
public class RandomColumnFiller implements MatchFourBot {
	int column = -1;
	
	@Override
	public int turn(char[][] board, char icon) {
		if (column < 0) {
			this.column = getNextColumn(board, icon);
		}
		return 0;
	}

	@Override
	public void won(boolean won) {
		this.column = -1;
	}

	@Override
	public String getBotName() {
		return "Random Column Filler Bot";
	}
	
	public int getNextColumn(char[][] board, char icon) {
		int maxX = board[0].length;
		List<Integer> xList = new ArrayList<>(maxX);
		for (int i = 0; i < maxX; i++) {
			xList.add(i);
		}
		Collections.shuffle(xList);
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