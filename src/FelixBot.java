public class FelixBot implements FourWinsBot {


    @Override
    public int turn(char[][] board) {
        return (int) (Math.random()*7);
    }

    @Override
    public void won(boolean won) {

    }

}
