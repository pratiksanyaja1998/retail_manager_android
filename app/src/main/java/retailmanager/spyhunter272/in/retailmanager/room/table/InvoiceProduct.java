package retailmanager.spyhunter272.in.retailmanager.room.table;

import android.arch.persistence.room.PrimaryKey;

public class InvoiceProduct {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String hsn;

    private String barcode;

    private double price;

    private int qty;

    private int invoiceId;

}
