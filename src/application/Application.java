package application;

import bots.EuloBot;
import bots.ExampleBot;
import game.Competition;

public class Application {

    public static void main(String[] args) {

        Competition competition = new Competition(new ExampleBot(), new EuloBot());
        competition.compete(50);
        System.out.println(competition.getResultsAsString());


    }

}
