package files;

import fileio.Input;
import fileio.Writer;
import fileio.ActorInputData;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class ModifiableDB {
    /**
     * the original input
     */
    private Input input;
    private Writer fileWriter;
    private JSONArray arrayResult;
    /**
     * List of actors
     */
    private List<Actor> actorsData;
    /**
     * List of users
     */
    private List<User> usersData;
    /**
     * List of movies
     */
    private List<Movie> moviesData;
    /**
     * List of serials aka tv shows
     */
    private List<Serial> serialsData;


    public ModifiableDB() {
        this.input = null;
        this.actorsData = null;
        this.usersData = null;
        this.moviesData = null;
        this.serialsData = null;
    }

    public ModifiableDB(final Input input, final Writer fileWriter, final JSONArray arrayResult) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;
        actors();
        users();
        movies();
        serials();
    }

    public ModifiableDB(final ModifiableDB original) {
        this.input = original.input;
        this.fileWriter = original.fileWriter;
        this.arrayResult = original.arrayResult;
        actorsData = new ArrayList<Actor>(original.actorsData);
        usersData = new ArrayList<User>(original.usersData);
        moviesData = new ArrayList<Movie>(original.moviesData);
        serialsData = new ArrayList<Serial>(original.serialsData);
    }

    /**
     * Getter for the original input, without any changes
     */
    public Input getInput() {
        return input;
    }
    /**
     * Getter for the list of actors
     */
    public List<Actor> getActors() {
        return actorsData;
    }
    /**
     * Getter for the list of users
     */
    public List<User> getUsers() {
        return usersData;
    }
    /**
     * Getter for the list of movies
     */
    public List<Movie> getMovies() {
        return moviesData;
    }
    /**
     * Getter for the list of serials
     */
    public List<Serial> getSerials() {
        return serialsData;
    }
    /**
     * Getter for the Writer
     */
    public Writer getFileWriter() {
        return fileWriter;
    }
    /**
     * Getter for the JSONArray used for output
     */
    public JSONArray getArrayResult() {
        return arrayResult;
    }
    /**
     * Used in the constructor to set the list of actors
     */
    private void actors() {
        actorsData = new ArrayList<Actor>();
        for (ActorInputData actor
                : input.getActors()) {
            actorsData.add(new Actor(actor));
        }
    }
    /**
     * Used in the constructor to set the list of users
     */
    private void users() {
        usersData = new ArrayList<User>();
        for (UserInputData user
                : input.getUsers()) {
            usersData.add(new User(user));
        }
    }
    /**
     * Used in the constructor to set the list of movies
     */
    private void movies() {
        moviesData = new ArrayList<Movie>();
        for (MovieInputData movie
                : input.getMovies()) {
            moviesData.add(new Movie(movie));
        }
    }
    /**
     * Used in the constructor to set the list of serials
     */
    private void serials() {
        serialsData = new ArrayList<Serial>();
        for (SerialInputData serial
                : input.getSerials()) {
            serialsData.add(new Serial(serial));
        }
    }
}

