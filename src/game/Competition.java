package game;

import bots.MatchFourBot;
import java.util.Arrays;

public class Competition {

    OutputMode outputMode;
    MatchFourBot[] allBots;
    int[] points;

    public Competition(MatchFourBot... allBots) {
        this.allBots = allBots;
        points = new int[allBots.length];
        Arrays.fill(this.points, 0);
        this.outputMode = outputMode;
    }

    public void compete(int rounds) {
        for (int bot1 = 0; bot1 < this.allBots.length; bot1++) {
            for (int bot2 = bot1+1; bot2 < this.allBots.length; bot2++) {
                Game game = new Game(this.allBots[bot1], this.allBots[bot2], OutputMode.NONE);
                for (int i = 0; i < rounds*2; i++) {
                    int winner = game.startRound(i%2==0);
                    switch (winner) {
                        case 0:
                            this.points[bot1] += 1;
                            this.points[bot2] += 1;
                            break;
                        case 1:
                            this.points[bot1] += 3;
                            break;
                        case 2:
                            this.points[bot2] += 3;
                            break;
                    }
                }
            }
        }
    }

    public String getResultsAsString() {
        this.sort();
        int nameLength = 0;
        for (MatchFourBot bot : allBots) {
            if (bot.getBotName().length() > nameLength) {
                nameLength = bot.getBotName().length();
            }
        }
        nameLength++;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.allBots.length; i++) {
            sb.append(this.allBots[i].getBotName()).append(" ".repeat(nameLength - this.allBots[i].getBotName().length())).append("=> " ).append(this.points[i]).append("\n");
        }
        return sb.toString();
    }

    private void sort() {
        MatchFourBot[] newBots = new MatchFourBot[this.allBots.length];
        int[] newPoints = new int[this.points.length];

        for(int j = 0; j < this.points.length; j++) {
            int highestValue = -1;
            int position = -1;
            for (int i = 0; i < this.points.length; i++) {
                if (this.points[i] > highestValue) {
                    highestValue = this.points[i];
                    position = i;
                }
            }
            newPoints[j] = this.points[position];
            newBots[j] = this.allBots[position];
            this.points[position] = -2;
        }

        this.allBots = newBots;
        this.points = newPoints;
    }


}
