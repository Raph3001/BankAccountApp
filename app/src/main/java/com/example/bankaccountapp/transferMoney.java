package com.example.bankaccountapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankaccountapp.BL.IOAccess;
import com.example.bankaccountapp.pojos.Account;
import com.example.bankaccountapp.pojos.GiroAccount;
import com.example.bankaccountapp.pojos.StudentAccount;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class transferMoney extends AppCompatActivity {
    private TextView iban, balance, aval, title;
    private EditText edIBAN, edAmount;
    private Button goBack, leaveAct;
    private AutoCompleteTextView autoCompleteTextView;
    private List<String> listOfIBANs;
    private String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);

        LayoutInflater linf;
        LinearLayout rr;

        linf = (LayoutInflater) getApplicationContext().getSystemService(this.LAYOUT_INFLATER_SERVICE);
        linf = LayoutInflater.from(this);
        rr = (LinearLayout) findViewById(R.id.amongus);
        final View v = linf.inflate(R.layout.banklettransfer, null);
        rr.addView(v);

        iban = findViewById(R.id.tvIBAN);
        balance = findViewById(R.id.tvBalance);
        aval = findViewById(R.id.tvAvailable);
        title = findViewById(R.id.tvTypeTitle);
        autoCompleteTextView = findViewById(R.id.actv);
        edAmount = findViewById(R.id.edAmount);
        leaveAct = findViewById(R.id.goBack);

        leaveAct.setOnClickListener(c -> {
            finish();
        });


        Intent intent = getIntent();
        this.listOfIBANs = intent.getStringArrayListExtra("IBANList");
        autoCompleteTextView.setThreshold(1);//will start working from first character
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,listOfIBANs));//setting the adapter data into the AutoCompleteTextView
        autoCompleteTextView.setTextColor(Color.RED);

        int position = intent.getIntExtra("position", 0);
        goBack = findViewById(R.id.btBack);


        System.err.println("The position is " + position);

        if (IOAccess.accountListTotal.get(position) instanceof GiroAccount) {
            GiroAccount giroAccount = (GiroAccount) IOAccess.accountListTotal.get(position);
            title.setText("Giro Account");
            balance.setText(giroAccount.getBalance() + "");
            if (giroAccount.getBalance() < 0) balance.setTextColor(Color.rgb(255,0,0));
            aval.setText(String.format("%.2f",(giroAccount.getBalance() + giroAccount.getOverDraft())));
            iban.setText(giroAccount.getIban());
            goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickGiro(giroAccount);
                }
            });
        } else {
            title.setText("Student Account");
            StudentAccount studentAccount = (StudentAccount) IOAccess.accountListTotal.get(position);
            balance.setText(studentAccount.getBalance() + "");
            aval.setText(studentAccount.isDebitCard() + "");
            iban.setText(studentAccount.getIban());
            goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickStudent(studentAccount);
                }
            });
        }

        /*try {
            GiroAccount giroAccount = intent.getParcelableExtra("girAc");
            title.setText("Giro Account");
            Toast.makeText(this, giroAccount.getBalance() + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            try {
                title.setText("Student Account");
                System.err.println("Error occured 1 " + e.getMessage());
                StudentAccount studentAccount = intent.getParcelableExtra("stuAc");

            } catch (Exception c) {
                System.err.println("Error occured" + c.getMessage());
                Toast.makeText(this, "Error ocured", Toast.LENGTH_SHORT).show();
            }
        }

        for (int i = 0; i < IOAccess.accountList.size(); i++) {
            if (IOAccess.accountList.get(i).getIban().equals(account.getIban())) {
                if (IOAccess.isGiro(IOAccess.accountList.get(i))) {

                } else {

                }
            }
        }*/




    }

    public void clickStudent(StudentAccount studentAccount) {

        String iban = autoCompleteTextView.getText().toString();

        if (edAmount.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Double.parseDouble(edAmount.getText().toString()) > Double.parseDouble(balance.getText().toString())) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!iban.equals("")) {
            if (listOfIBANs.contains(iban) && !iban.contentEquals(this.iban.getText())) {
                double tugrik = Double.parseDouble(edAmount.getText().toString());
                System.out.println(tugrik);

                closeActivity(studentAccount.getIban());

            } else {
                Toast.makeText(this, "Please enter valid IBAN", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter valid IBAN", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickGiro(GiroAccount giroAccount) {

        if (edAmount.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Double.parseDouble(edAmount.getText().toString()) > /*(Double.parseDouble(balance.getText().toString()) + */(Double.parseDouble(aval.getText().toString()))) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        String iban = autoCompleteTextView.getText().toString();
        if (!iban.equals("")) {
            if (listOfIBANs.contains(iban) && !iban.contentEquals(this.iban.getText())) {
                double tugrik = Double.parseDouble(edAmount.getText().toString());
                System.out.println(tugrik);

                closeActivity(giroAccount.getIban());

            } else {
                Toast.makeText(this, "Please enter valid IBAN", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter valid IBAN", Toast.LENGTH_SHORT).show();
        }

    }

    public void closeActivity(String startIBAN) {
        Intent intent = getIntent();
        intent.putExtra("ibanFrom", startIBAN);
        intent.putExtra("ibanTo", autoCompleteTextView.getText().toString());
        intent.putExtra("amount", Double.parseDouble(edAmount.getText().toString()));
        setResult(1, intent);
        finish();
    }

}