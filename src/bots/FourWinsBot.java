package bots;

public interface FourWinsBot {

    int turn(char[][] board);

    void won(boolean won);

    String getBotName();

}
