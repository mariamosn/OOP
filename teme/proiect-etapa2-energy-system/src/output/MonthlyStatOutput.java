package output;

import java.util.List;

public class MonthlyStatOutput {
    private int month;
    private List<Integer> distributorsIds;

    public MonthlyStatOutput(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
        if (distributorsIds != null) {
            this.distributorsIds.sort(Integer::compareTo);
        }
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
