package bots;

/**
 * @author Felix Pape
 */
public class ExampleBot implements MatchFourBot {

    final String BOTNAME = "Example Bot";


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
