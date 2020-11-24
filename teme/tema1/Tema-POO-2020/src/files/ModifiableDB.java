package files;

import fileio.*;
import fileio.Input;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModifiableDB {
    /**
     * Original input
     */
    public Input input;
    public Writer fileWriter;
    public JSONArray arrayResult;
    /**
     * List of actors
     */
    public List<Actor> actorsData;
    /**
     * List of users
     */
    public List<User> usersData;
    /**
     * List of movies
     */
    public List<Movie> moviesData;
    /**
     * List of serials aka tv shows
     */
    public List<Serial> serialsData;


    public ModifiableDB() {
        this.input = null;
        this.actorsData = null;
        this.usersData = null;
        this.moviesData = null;
        this.serialsData = null;
    }

    public ModifiableDB(Input input, Writer fileWriter, JSONArray arrayResult) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;
        actors();
        users();
        movies();
        serials();
    }

    public ModifiableDB(ModifiableDB original) {
        this.input = original.input;
        this.fileWriter = original.fileWriter;
        this.arrayResult = original.arrayResult;
        actorsData = new ArrayList<Actor>(original.actorsData);
        usersData = new ArrayList<User>(original.usersData);
        moviesData = new ArrayList<Movie>(original.moviesData);
        serialsData = new ArrayList<Serial>(original.serialsData);
        //Collections.copy(actorsData, original.actorsData);
        // users();
        //movies();
        //serials();
    }

    public Input getInput() { return input; }

    public List<Actor> getActors() {
        return actorsData;
    }

    public List<User> getUsers() {
        return usersData;
    }

    public List<Movie> getMovies() {
        return moviesData;
    }

    public List<Serial> getSerials() {
        return serialsData;
    }

    private void actors() {
        actorsData = new ArrayList<Actor>();
        for (ActorInputData actor:
             input.getActors()) {
            actorsData.add(new Actor(actor));
        }
    }

    private void users() {
        usersData = new ArrayList<User>();
        for (UserInputData user:
                input.getUsers()) {
            usersData.add(new User(user));
        }
    }

    private void movies() {
        moviesData = new ArrayList<Movie>();
        for (MovieInputData movie:
                input.getMovies()) {
            moviesData.add(new Movie(movie));
        }
    }

    private void serials() {
        serialsData = new ArrayList<Serial>();
        for (SerialInputData serial:
                input.getSerials()) {
            serialsData.add(new Serial(serial));
        }
    }
}

