package bots;

/**
 * @author Daniel Nitschke
 */
public class PlebBot implements MatchFourBot {
	private static Direction[] directions = new Direction[] {
			Direction.HORIZONTAL, Direction.VERTICAL, Direction.DIAGONAL1, Direction.DIAGONAL2
		};
	
	private final static char[] PLAYER_ICONS = { 'X', 'O' };
	private final static char EMPTY_ICON = ' ';
	
	private boolean newRound = true;

	/**
	 * This will be called every time, your bot has to do a turn.
	 *
	 * @param board is the gameBoard where your bot has to do its move.
	 *              board[HEIGHT][WIDTH]
	 * @return the column where your bot wants to place it's stone.
	 */
	public int turn(char[][] board, char icon) {
    	
		// Simple yolo, go for the middle! This is always a great start
		if(this.newRound) {
			this.newRound = false;
			return board[0].length/2;
		}
		char enemyIcon = icon == PLAYER_ICONS[0] ? PLAYER_ICONS[1] : PLAYER_ICONS[0];
		
		
		EvaluatedDirection myIdealDirection = computeBestMove(board, icon);

		if (myIdealDirection == null) {
			return (int)(Math.random() * board.length);
		}
		
		int targetX = myIdealDirection.x;
		int targetY = 0;
		
		while (targetY < board.length - 1 && board[targetY + 1][targetX] == EMPTY_ICON) {
			targetY++;
		}
		char[][] boardAfterMe = board.clone();
		boardAfterMe[targetY][targetX] = icon;
		
		EvaluatedDirection enemyIdealDirection = computeBestMove(board, enemyIcon);
		
		if (enemyIdealDirection == null) {
			return targetX;
		}
		
		if (myIdealDirection.getValue() >= 3) {
			targetX = myIdealDirection.getX();
		} else if (enemyIdealDirection.getValue() >= 2) {
			targetX = enemyIdealDirection.getX();
		}
		else {
			targetX = myIdealDirection.getX();
		}
		return targetX;
	}

	/**
	 * This will be called every time, a game ends.
	 *
	 * @param won is true if your bot won the round or false if he loses or plays a
	 *            draw.
	 */
	public void won(boolean won) {
		// TODO: buy bottles of the most expensive Champagner and deliver via UDP
		this.newRound = true;
	}

	/**
	 * @return the name of your Bot. This has to match with your class and file
	 *         name. (Adding a space or make some letters uppercase is allowed.)
	 */
	public String getBotName() {
		return "PlebBot";
	}
	
	public static void print(char[][] array) {
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[y].length; x++) {
				System.out.print(array[y][x]);
			}
			System.out.println();
		}
	}

	private EvaluatedDirection computeBestMove(char[][] board, char icon) {
		EvaluatedDirection[] evaluatedDirectionsPerX = new EvaluatedDirection[board[0].length];
		int bestX = -1;
		
		for (int x = 0; x < board[0].length; x++) { 
			if (board[0][x] != EMPTY_ICON) {
				continue;
			}
			for (int y = 0; y < board.length; y++) {
				if (y < board.length - 1 && board[y + 1][x] == EMPTY_ICON) {
					continue;
				}
				evaluatedDirectionsPerX[x] = evaluateDirection(x, y, board, icon);
				
				bestX = bestX < 0 ? x : (evaluatedDirectionsPerX[x].getValue() > evaluatedDirectionsPerX[bestX].getValue() ? x : bestX);
				break;
			}
		}
		if (bestX < 0) {
			return null;
		}
		evaluatedDirectionsPerX[bestX].setX(bestX);
		return evaluatedDirectionsPerX[bestX];
	}

	private EvaluatedDirection evaluateDirection(int x, int y, char[][] board, char icon) {
		int[] values = new int[directions.length];
		final int MAX_Y = board.length;
		final int MAX_X = board[0].length;
		final int[] stepDirs = new int[] { 1, -1 };
		
		int bestIndex = -1;
		
		for (int i = 0; i < directions.length; i++) {
			
			for (int stepDir : stepDirs) {
				int step = 1;
				int offset = step++ * stepDir;
				int dx = x + directions[i].x() * offset;
				int dy = y + directions[i].y() * offset;
				while (dx >= 0 && dx < MAX_X && dy >= 0 && dy < MAX_Y && board[dy][dx] == icon) {
					values[i]++;

					offset = step++ * stepDir;
					dx = x + directions[i].x() * offset;
					dy = y + directions[i].y() * offset;
				}
			}
			bestIndex = bestIndex == -1 ? i : (values[i] > values[bestIndex] ? i : bestIndex);
		}
		return new EvaluatedDirection(directions[bestIndex], values[bestIndex]);
	}
	
	public class EvaluatedDirection {
		private Direction direction;
		private int x;
		private int value;
		
		public EvaluatedDirection(Direction direction, int value) {
			this.direction = direction;
			this.value = value;
		}
		
		public Direction getDirection() {
			return this.direction;
		}
		
		public int getX() {
			return this.x;
		}
		
		public int getValue() {
			return this.value;
		}
		
		public void setX(int x) {
			this.x = x;
		}
	}

	public enum Direction {
		HORIZONTAL(1, 0), VERTICAL(0, 1), DIAGONAL1(1, 1), DIAGONAL2(1, -1);

		private int dx, dy;

		private Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		private int x() {
			return this.dx;
		}

		private int y() {
			return this.dy;
		}
	}

}