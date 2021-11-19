package com.example.ejercicio213;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class LogIn_Activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("usuarios");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText username = findViewById(R.id.etmail);
        EditText password = findViewById(R.id.password);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("usuarios")
                        .whereEqualTo("Username", username.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.get("Password").equals(password.getText().toString())) {
                                            Intent it = new Intent(LogIn_Activity.this, MainActivity.class);
                                            startActivity(it);
                                        }
                                        break;
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        final boolean[] crear = {false};
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("usuarios").document(username.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                crear[0] = false;
                            } else {
                                crear[0] = true;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos", Toast.LENGTH_LONG).show();
                            crear[0] = false;
                        }
                    }
                });

                if (crear[0]) {
                    Toast.makeText(getApplicationContext(), "Usuario creado correctamente", Toast.LENGTH_LONG).show();
                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("Username", username.getText().toString());
                    data1.put("Password", password.getText().toString());
                    users.document(username.getText().toString()).set(data1);
                } else {
                    Toast.makeText(getApplicationContext(), "Ya existe ese usuario", Toast.LENGTH_LONG).show();
                }
                username.setText("");
                password.setText("");
            }
        });
    }
}