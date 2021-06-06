package bots;

/**
 * @author Felix Pape
 */
public class FelixBot implements FourWinsBot {

    final String BOTNAME = "Chaos Bot";


    @Override
    public int turn(char[][] board) {
        return (int) (Math.random()*7);
    }

    @Override
    public void won(boolean won) {

    }

    @Override
    public String getBotName() {
        return BOTNAME;
    }

}
