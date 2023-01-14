package com.example.bankaccountapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bankaccountapp.BL.BankletAdapter;
import com.example.bankaccountapp.BL.IOAccess;
import com.example.bankaccountapp.pojos.Account;
import com.example.bankaccountapp.pojos.GiroAccount;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvBanklets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IOAccess.fillAccounts(this);
        rvBanklets = findViewById(R.id.rvBanklets);

        rvBanklets.setLayoutManager(new LinearLayoutManager(this));
        rvBanklets.setAdapter(new BankletAdapter(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_a:
                Toast.makeText(this, "Option a clicked", Toast.LENGTH_SHORT).show();
                rvBanklets.setAdapter(new BankletAdapter(this, true));
                break;

            case R.id.it_b:
                Toast.makeText(this, "Option b clicked", Toast.LENGTH_SHORT).show();
                rvBanklets.setAdapter(new BankletAdapter(this, false));
                break;
            case R.id.it_c:
                Toast.makeText(this, "Option c clicked", Toast.LENGTH_SHORT).show();
                rvBanklets.setAdapter(new BankletAdapter(this));
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        startActivityForResult(intent, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println(data.getStringExtra("ibanTo"));
            double amount = data.getDoubleExtra("amount", 0);
            String accTo = data.getStringExtra("ibanTo");
            String accFrom = data.getStringExtra("ibanFrom");
            for (Account acc:
                    IOAccess.accountListStatic) {
                if (acc.getIban().equals(accTo)) acc.setBalance(acc.getBalance()+amount);
                if (acc.getIban().equals(accFrom)) acc.setBalance(acc.getBalance() - amount);
            }
            rvBanklets.setAdapter(new BankletAdapter(this));
        } catch (Exception e) {

        }

        }
}