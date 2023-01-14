package com.example.bankaccountapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private String iban;
    private double balance;
    private float interest;

    public Account(String iban, double balance, float interest) {
        this.iban = iban;
        this.balance = balance;
        this.interest = interest;
    }

    protected Account(Parcel in) {
        iban = in.readString();
        balance = in.readDouble();
        interest = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iban);
        dest.writeDouble(balance);
        dest.writeFloat(interest);
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", interest=" + interest +
                '}';
    }
}
