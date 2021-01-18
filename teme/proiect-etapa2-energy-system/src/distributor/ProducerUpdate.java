package distributor;

import java.util.Observable;
import java.util.Observer;

public class ProducerUpdate implements Observer {
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

    public void reset() {
        this.isUpdated = false;
    }
}
