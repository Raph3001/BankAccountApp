package com.example.bankaccountapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class GiroAccount extends Account implements Parcelable {

    private double overDraft;

    public GiroAccount(String iban, double balance, float interest, double overDraft) {
        super(iban, balance, interest);
        this.overDraft = overDraft;
    }

    public GiroAccount(Account sus,double overDraft) {
        super(sus.getIban(), sus.getBalance(), sus.getInterest());
        this.overDraft = overDraft;
    }

    public GiroAccount(String ln) {
        super((ln.split(",")[2]), Double.parseDouble((ln.split(",")[3])), Float.parseFloat((ln.split(",")[6])));
        this.overDraft = Double.parseDouble((ln.split(",")[4]));

    }

    protected GiroAccount(Parcel in) {
        super(in);
        this.overDraft = in.readDouble();
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }

    @Override
    public String toString() {
        return "GiroAccount{" +
                "overDraft=" + overDraft +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeDouble(overDraft);
    }
}
