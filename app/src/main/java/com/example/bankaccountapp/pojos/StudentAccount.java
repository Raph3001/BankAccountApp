package com.example.bankaccountapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentAccount extends Account implements Parcelable {

    private boolean debitCard;

    public StudentAccount(String iban, double balance, float interest, boolean debitCard) {
        super(iban, balance, interest);
        this.debitCard = debitCard;
    }

    public StudentAccount(String ln) {
        super((ln.split(",")[2]), Double.parseDouble((ln.split(",")[3])), Float.parseFloat((ln.split(",")[6])));
        this.debitCard = Boolean.parseBoolean(ln.split(",")[5]);
    }

    protected StudentAccount(Parcel in, boolean debitCard) {
        super(in);
        this.debitCard = debitCard;
    }

    public boolean isDebitCard() {
        return debitCard;
    }

    public void setDebitCard(boolean debitCard) {
        this.debitCard = debitCard;
    }

    @Override
    public String toString() {
        return "StudentAccount{" +
                "debitCard=" + debitCard +
                '}';
    }
}
