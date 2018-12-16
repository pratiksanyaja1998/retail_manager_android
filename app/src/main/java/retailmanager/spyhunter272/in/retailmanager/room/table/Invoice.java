package retailmanager.spyhunter272.in.retailmanager.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "customer")
public class Invoice {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int customer;

    private int gsttype; //  for  0 None / 1 GST / 2 IGST
    private int gst; // 0 5 12 18 28
    private double total;

    private float discount;

    private String desciption;
    private boolean tprchage;

    private String paymentMethrd;

    private int dd;
    private int mm;
    private int yyyy;


}
