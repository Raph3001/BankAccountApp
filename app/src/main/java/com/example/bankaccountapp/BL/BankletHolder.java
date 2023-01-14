package com.example.bankaccountapp.BL;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankaccountapp.MainActivity;
import com.example.bankaccountapp.R;
import com.example.bankaccountapp.pojos.GiroAccount;
import com.example.bankaccountapp.pojos.StudentAccount;
import com.example.bankaccountapp.transferMoney;

import java.util.ArrayList;
import java.util.List;

public class BankletHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView tvTitle, tvIBAN, tvBalance, tvAvail;
    private ImageView ivImg;
    private List<String> IBANList = new ArrayList<>();
    private Context context;
    private int position = 0;
    private GiroAccount giroAccount = new GiroAccount("1", 0, 0, 1);
    private StudentAccount studentAccount = new StudentAccount("1", 0, 0, false);

    public BankletHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        tvTitle = itemView.findViewById(R.id.tvTypeTitle);
        tvIBAN = itemView.findViewById(R.id.tvIBAN);
        tvBalance = itemView.findViewById(R.id.tvBalance);
        tvAvail = itemView.findViewById(R.id.tvAvailable);
        ivImg = itemView.findViewById(R.id.ivTypeImage);
        itemView.setOnClickListener(this);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvIBAN() {
        return tvIBAN;
    }

    public void setTvIBAN(TextView tvIBAN) {
        this.tvIBAN = tvIBAN;
    }

    public TextView getTvBalance() {
        return tvBalance;
    }

    public void setTvBalance(TextView tvBalance) {
        this.tvBalance = tvBalance;
    }

    public TextView getTvAvail() {
        return tvAvail;
    }

    public void setTvAvail(TextView tvAvail) {
        this.tvAvail = tvAvail;
    }

    public ImageView getIvImg() {
        return ivImg;
    }

    public void setIvImg(ImageView ivImg) {
        this.ivImg = ivImg;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setGiroAccount(GiroAccount giroAccount) {
        this.giroAccount = giroAccount;
    }

    public void setStudentAccount(StudentAccount studentAccount) {
        this.studentAccount = studentAccount;
    }

    @Override
    public void onClick(View view) {
        changeActivity();
    }

    private void changeActivity() {
        Intent intent = new Intent(context, transferMoney.class);
        for (int i = 0; i < IOAccess.accountListTotal.size(); i++) {
            IBANList.add(IOAccess.accountListTotal.get(i).getIban());
        }
        intent.putStringArrayListExtra("IBANList", (ArrayList<String>) IBANList);
        //intent.putExtra("isgiro", IOAccess.isGiro(IOAccess.accountList.get(position)));
        intent.putExtra("position", position);
        System.out.println("The value of position is " + position);


        /*if (!giroAccount.getIban().equals("1")) {
            System.out.println("is giro? " + (giroAccount instanceof GiroAccount));
            intent.putExtra("girAc", giroAccount);
            context.startActivity(intent);
        } else if (!studentAccount.getIban().equals("1")) {
            System.out.println("is student? " + (studentAccount instanceof StudentAccount));
            intent.putExtra("stuAc", studentAccount);
            context.startActivity(intent);
        } else {
            return;
        }*/


        context.startActivity(intent);
        //context.startActivityForResult(intent, 2);
    }



}
