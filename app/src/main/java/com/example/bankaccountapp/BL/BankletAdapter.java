package com.example.bankaccountapp.BL;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankaccountapp.R;
import com.example.bankaccountapp.pojos.Account;
import com.example.bankaccountapp.pojos.GiroAccount;
import com.example.bankaccountapp.pojos.StudentAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankletAdapter extends RecyclerView.Adapter<BankletHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Account> accountList = new ArrayList<>();

    public BankletAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        accountList = IOAccess.accountListTotal;
        IOAccess.accountListStatic = IOAccess.accountListTotal;
    }

    public BankletAdapter(Context context, boolean filterGirus) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        accountList = IOAccess.accountListTotal;

        if (filterGirus) {
            IOAccess.accountListStatic = IOAccess.accountListTotal;
            accountList = accountList.stream().filter(IOAccess::isGiro).collect(Collectors.toList());
            IOAccess.accountListStatic = accountList;
        } else {
            accountList = accountList.stream().filter(IOAccess::isNotGiro).collect(Collectors.toList());
            IOAccess.accountListStatic = IOAccess.accountListTotal;
            IOAccess.accountListStatic = accountList;
        }

    }

    @NonNull
    @Override
    public BankletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.banklet, parent, false);
        return new BankletHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BankletHolder holder, int position) {
        holder.getTvBalance().setTextColor(Color.rgb(0,255,0));
        holder.setPosition(position);
        holder.getTvIBAN().setText(accountList.get(position).getIban());
        holder.getTvBalance().setText(String.format("%.2f", accountList.get(position).getBalance()));

        if (accountList.get(position).getBalance() < 0) {
            holder.getTvBalance().setTextColor(Color.rgb(255,0,0));
        }

        if (IOAccess.isGiro(accountList.get(position))) {
            holder.setGiroAccount((GiroAccount) accountList.get(position));
            holder.getIvImg().setImageResource(R.drawable.ic_baseline_attach_money_24);
            holder.getTvTitle().setText("Giro-Account");
            holder.getTvAvail().setText(String.format("%-30s %17s", (context.getResources().getString(R.string.avail)), ((GiroAccount) accountList.get(position)).getOverDraft() + ""));
        } else {
            holder.setStudentAccount((StudentAccount) accountList.get(position));
            holder.getIvImg().setImageResource(R.drawable.ic_baseline_school_24);
            holder.getTvTitle().setText("Student-Account");
            if (((StudentAccount) accountList.get(position)).isDebitCard()) {
                holder.getTvAvail().setText("Debit Card");
            } else {
                holder.getTvAvail().setText("No Debit Card");
            }
            int bal = Integer.parseInt(Math.round(accountList.get(position).getBalance()) + "");
            System.err.println(bal);
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }



}
