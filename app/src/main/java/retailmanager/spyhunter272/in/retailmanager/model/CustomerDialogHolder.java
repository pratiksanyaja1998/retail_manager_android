package retailmanager.spyhunter272.in.retailmanager.model;

import android.util.Log;

public class CustomerDialogHolder {

    boolean email;
    boolean mobile;
    boolean baddr;
    boolean saddr;

    public CustomerDialogHolder(boolean email, boolean mobile, boolean baddr, boolean saddr) {
        this.email = email;
        this.mobile = mobile;
        this.baddr = baddr;
        this.saddr = saddr;
    }

    public boolean isEmail() {
        return email;
    }

    public boolean isMobile() {
        return mobile;
    }

    public boolean isBaddr() {
        return baddr;
    }

    public boolean isSaddr() {
        return saddr;
    }



}
