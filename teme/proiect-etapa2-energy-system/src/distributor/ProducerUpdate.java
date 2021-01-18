package distributor;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public final class ProducerUpdate implements Observer {
    private boolean isUpdated;

    public ProducerUpdate() {
        this.isUpdated = true;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.isUpdated = true;
    }

    public boolean getIsUpdated() {
        return isUpdated;
    }

    /**
     * The method resets the isUpdated flag.
     */
    public void reset() {
        this.isUpdated = false;
    }
}
