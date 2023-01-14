package com.example.bankaccountapp.BL;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.bankaccountapp.pojos.Account;
import com.example.bankaccountapp.pojos.GiroAccount;
import com.example.bankaccountapp.pojos.StudentAccount;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class IOAccess {
    public static List<Account> accountListStatic = new ArrayList<>();
    public static List<Account> accountListTotal = new ArrayList<>();

    public static ArrayList<Account> fillAccounts(Context context) {
        List<Account> accountList = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("account_data.csv");
            accountList = new BufferedReader(new InputStreamReader(is)).lines()
                    .skip(1)
                    .map(c -> {
                        String [] properties = c.split(",");
                        if (properties[1].equals("GIRO")) {
                            return new GiroAccount(c);
                        }
                        return new StudentAccount(c);
            })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        accountListStatic = accountList;
        accountListTotal = accountList;
        return (ArrayList<Account>) accountList;
    }

    public static boolean isGiro(Account account) {
        try {
            GiroAccount giroAccount = (GiroAccount) account;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNotGiro(Account account) {
        try {
            GiroAccount giroAccount = (GiroAccount) account;
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}
