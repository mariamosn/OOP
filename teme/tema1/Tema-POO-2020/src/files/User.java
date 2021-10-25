package files;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Information about an user
 */
public class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Number of ratings
     */
    private final ArrayList<String> rated = new ArrayList<>();

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public User(final UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.favoriteMovies = user.getFavoriteMovies();
        this.history = user.getHistory();
    }
    /**
     * Getter for the username of an user
     */
    public String getUsername() {
        return username;
    }
    /**
     * Getter for the watch history of an user
     */
    public Map<String, Integer> getHistory() {
        return history;
    }
    /**
     * Getter for the subscription type
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }
    /**
     * Getter for the list of favorite shows
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }
    /**
     * Getter for the list of rated shows
     */
    public ArrayList<String> getRated() {
        return rated;
    }
    /**
     * Getter for the number of ratings
     */
    public int getRatingsNum() {
        return rated.size();
    }
    /**
     * The method returns an User based on the username
     * @param name = the username
     * @param dataBase = the database where the users are stored
     */
    public static User getRightUser(final ModifiableDB dataBase, final String name) {
        List<User> users = dataBase.getUsers();
        User user = null;

        // search the right user in the database
        for (User tempuser
                : users) {
            if (tempuser.getUsername().equals(name)) {
                user = tempuser;
                break;
            }
        }
        if (user == null) {
            System.out.println("Invalid user!");
        }
        return user;
    }
}
