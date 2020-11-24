package files;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an user
 */
public class User {
    /**
     * User's username
     */
    private String username;
    /**
     * Subscription Type
     */
    private String subscriptionType;
    /**
     * The history of the movies seen
     */
    public Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    public ArrayList<String> favoriteMovies;
    /**
     * Number of ratings
     */
    public ArrayList<String> rated = new ArrayList<>();

    public User(String username, String subscriptionType,
                Map<String, Integer> history,
                ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public User(UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.favoriteMovies = user.getFavoriteMovies();
        this.history = user.getHistory();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }
    public int getRatingsNum() {
        return rated.size();
    }

    @Override
    public String toString() {
        return "User{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
