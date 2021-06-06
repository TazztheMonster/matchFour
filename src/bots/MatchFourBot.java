package bots;

public interface MatchFourBot {

    int turn(char[][] board);

    void won(boolean won);

    String getBotName();

}
