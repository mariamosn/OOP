package files;

import actor.ActorsAwards;
import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor
 */
public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;
    private double average;
    private int awardsCnt = 0;

    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.careerDescription = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
    }

    /**
     * Getter for the name of an actor
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for Filmography
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }
    /**
     * Getter for a map of awards
     * key = the name of the award
     * value = the total number of times that award has been won by the current actor
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    /**
     * Getter for the career description
     */
    public String getCareerDescription() {
        return careerDescription;
    }
    /**
     * Getter for the average
     * = the average of the ratings of the shows the actor has in their filmography
     */
    public double getAverage() {
        return average;
    }
    /**
     * Setter for average
     * @param average = the updated average
     */
    public void setAverage(final double average) {
        this.average = average;
    }
    /**
     * Getter for the total number of awards
     */
    public int getAwardsCnt() {
        return awardsCnt;
    }
    /**
     * Setter for the total number of awards
     * @param awardsCnt = the updated award count
     */
    public void setAwardsCnt(final int awardsCnt) {
        this.awardsCnt = awardsCnt;
    }
    /**
     * The method transforms the Actor into a String
     */
}
