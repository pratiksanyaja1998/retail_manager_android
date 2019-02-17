package retailmanager.spyhunter272.com.room.model;

public class InvoiceOverview {

    private float total;

    private int count;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceOverview(float total) {
        this.total = total;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }


}
