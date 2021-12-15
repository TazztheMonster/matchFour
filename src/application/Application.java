package application;

import bots.*;
import game.Competition;
import game.GameBoard;

public class Application {

    public static void main(String[] args) {

        Competition competition = new Competition(new AssholeBot(), new EuloBot(), new ExampleBot(), new PlebBot(), new RandomColumnFillerBot());
        competition.compete(50);
        System.out.println(competition.getResultsAsString());


    }

}
