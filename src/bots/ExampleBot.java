package bots;

/**
 * @author Felix Pape
 */
public class ExampleBot implements MatchFourBot {

    final String BOTNAME = "Example Bot";


    @Override
    public int turn(char[][] board, char icon) {
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
              0 | - - - - - - -
              1 | - - - - - - -
              2 | - - - - - - -
              3 | - - - - - - -
            > 4 | - - - x o - -
          /   5 | - - - o x - -
        /     6 | - x o x o - -
 board [] [] --> +---------------
                  0 1 2 3 4 5 6
     */

}
