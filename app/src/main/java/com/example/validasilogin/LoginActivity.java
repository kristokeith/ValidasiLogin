package com.example.validasilogin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class LoginActivity extends AppCompatActivity {
    public static final String FILENAME = "login";

    EditText editUsername, editPassword;
    Button btLogin, btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btLogin = findViewById(R.id.action_login);
        btRegister = findViewById(R.id.action_register);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    void login(){
        File sdcard = getFilesDir();
        File file = new File(sdcard, editUsername.getText().toString());
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br =
                        new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");

            if (dataUser[1].equals(editPassword.getText().toString())) {
                simpanFileLogin();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Password Tidak Sesuai", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
    void simpanFileLogin(){
        String isiFile = editUsername.getText().toString() + ";" + editPassword.getText().toString();
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}