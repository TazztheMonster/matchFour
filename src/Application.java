import bots.FelixBot;
import game.Game;
import game.OutputMode;

public class Application {

    public static void main(String[] args) {

        int bot1Win = 0;
        int bot2Win = 0;
        int draw = 0;

        Game game = new Game(new FelixBot(), new FelixBot(), OutputMode.NONE);

        for (int i = 0; i < 100; i++) {
            switch (game.startRound(i % 2 == 1)) {
                case 1 -> bot1Win++;
                case 2 -> bot2Win++;
                case 0 -> draw++;
            }
        }

        System.out.printf("Bot1: %d\nBot2: %d\nDraw: %d\n", bot1Win, bot2Win, draw);

    }

}
