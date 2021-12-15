package game;

import bots.MatchFourBot;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Competition {

    MatchFourBot[] allBots;
    int[] points;
    int[] wins;
    int[] draw;
    int[] lost;
    int roundsPlayed;

    public Competition(MatchFourBot... allBots) {
        this.allBots = allBots;
        points = new int[allBots.length];
        Arrays.fill(this.points, 0);
        wins = new int[allBots.length];
        Arrays.fill(this.wins, 0);
        draw = new int[allBots.length];
        Arrays.fill(this.draw, 0);
        lost = new int[allBots.length];
        Arrays.fill(this.lost, 0);
    }

    public void compete(int rounds) {
        this.roundsPlayed = rounds * 2;
        for (int bot1 = 0; bot1 < this.allBots.length; bot1++) {
            for (int bot2 = bot1+1; bot2 < this.allBots.length; bot2++) {
                Game game = new Game(this.allBots[bot1], this.allBots[bot2], OutputMode.NONE);
                for (int i = 0; i < rounds*2; i++) {
                    int winner = game.startRound(i%2==0);
                    switch (winner) {
                        case 0:
                            this.points[bot1] += 1;
                            this.points[bot2] += 1;
                            this.draw[bot1]++;
                            this.draw[bot2]++;
                            break;
                        case 1:
                            this.points[bot1] += 3;
                            this.wins[bot1]++;
                            this.lost[bot2]++;
                            break;
                        case 2:
                            this.points[bot2] += 3;
                            this.wins[bot2]++;
                            this.lost[bot1]++;
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
        DecimalFormat df = new DecimalFormat("#.0");
        for (int i = 0; i < this.allBots.length; i++) {
            int playedRounds = this.wins[i] + this.draw[i] + this.lost[i];
            double roundsWonPercent = ((double) this.wins[i]) / playedRounds * 100;
            sb.append(this.allBots[i].getBotName())
                    .append(new String(new char[nameLength - this.allBots[i].getBotName().length()]).replace("\0", " ")).append("=> " ).append(this.points[i])
                    .append("p  \t| W:").append(this.wins[i])
                    .append("\tD:").append(this.draw[i])
                    .append("\tL:").append(this.lost[i])
                    .append("     \t| WR: ").append(df.format(roundsWonPercent)).append("% ")
                    .append("\n");
        }
        return sb.toString();
    }

    private void sort() {
        MatchFourBot[] newBots = new MatchFourBot[this.allBots.length];
        int[] newPoints = new int[this.points.length];
        int[] newWins = new int[this.wins.length];
        int[] newDraws = new int[this.draw.length];
        int[] newLoses = new int[this.lost.length];

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
            newWins[j] = this.wins[position];
            newDraws[j] = this.draw[position];
            newLoses[j] = this.lost[position];
            this.points[position] = -2;
        }

        this.allBots = newBots;
        this.points = newPoints;
        this.wins = newWins;
        this.draw = newDraws;
        this.lost = newLoses;
    }


}
