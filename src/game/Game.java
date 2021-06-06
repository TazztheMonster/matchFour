package game;

import bots.MatchFourBot;

public class Game{

    private MatchFourBot bot1;
    private char BOT1ICON = 'X';
    private MatchFourBot bot2;
    private char BOT2ICON = 'O';
    private OutputMode outputMode;
    private GameBoard gameBoard;

    /**
     *
     * @param bot1 is the stating bot. (X)
     * @param bot2 is the second bot.  (O)
     * As default game.OutputMode BASIC is selected.
     */
    public Game(MatchFourBot bot1, MatchFourBot bot2) {
        this(bot1, bot2, OutputMode.BASIC);
    }

    /**
     *
     * @param bot1 is the stating bot. (X)
     * @param bot2 is the second bot.  (O)
     * @param outputMode defines the output on the console.
     */
    public Game(MatchFourBot bot1, MatchFourBot bot2, OutputMode outputMode) {
        this.bot1 = bot1;
        this.bot2 = bot2;
        this.outputMode = outputMode;
    }

    /**
     *
     * @return 0 for draw, 1 for bot1 win, 2 for bot2 win and 999 if everything is going to hell.
     */
    public int startRound(boolean reversedStart) {
        this.gameBoard = new GameBoard();
        for (int i = 0; i <= 60; i++) {
            if((i % 2 == 0) != reversedStart) {
                this.gameBoard.place(this.BOT1ICON, this.bot1.turn(this.gameBoard.getBoard(), this.BOT1ICON));
            } else {
                this.gameBoard.place(this.BOT2ICON, this.bot2.turn(this.gameBoard.getBoard(), this.BOT2ICON));
            }
            if(this.gameBoard.isFinished()) {
                winningOutput();
                if (this.gameBoard.getWinningIcon() == this.BOT1ICON) {
                    this.bot1.won(true);
                    this.bot2.won(false);
                    return 1;
                } else if (gameBoard.getWinningIcon() == this.BOT2ICON) {
                    this.bot1.won(false);
                    this.bot2.won(true);
                    return 2;
                } else {
                    return 999;
                }
            }
        }
        drawOutput();
        this.bot1.won(false);
        this.bot2.won(false);
        return 0;
    }

    private void winningOutput() {
        if (this.outputMode == OutputMode.FULL || this.outputMode == OutputMode.BASIC) {
            System.out.println(gameBoard.getWinningIcon() + " won!");
        }
        if (this.outputMode == OutputMode.FULL) {
            System.out.println(gameBoard.toString());
        }
    }

    private void drawOutput() {
        if (this.outputMode == OutputMode.FULL || this.outputMode == OutputMode.BASIC) {
            System.out.println("Draw!");
        }
        if (this.outputMode == OutputMode.FULL) {
            System.out.println(gameBoard.toString());
        }
    }

    public OutputMode getOutputMode() {
        return this.outputMode;
    }

    public void setOutputMode(OutputMode outputMode) {
        this.outputMode = outputMode;
    }

}
