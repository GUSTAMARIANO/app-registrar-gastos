package com.example.camerax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PrimeiraTela extends AppCompatActivity {

    Button btn_adicionar_nfe,btn_sair,btn_criar_viagem;
    TextView text_email,text_usuario;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioId,emailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_primeira_tela);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponentes();

        btn_criar_viagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ir_tela_criar_viagem();
            }
        });

        btn_adicionar_nfe.setOnClickListener(v -> {
            IrParaProximaTela();
        });

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                TelaLogin();
            }
        });

        }

        private void Ir_tela_criar_viagem(){
            Intent intent = new Intent(PrimeiraTela.this, Tela_criar_viagem.class);
            startActivity(intent);

        }
        private void IrParaProximaTela() {
            Intent tela = new Intent(PrimeiraTela.this, MainActivity.class);
            startActivity (tela);
        };

        private void TelaLogin(){
            Intent tela = new Intent(PrimeiraTela.this,TelaLogin.class);
            startActivity(tela);
            finish();
        }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // emailId = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    if (documentSnapshot != null){
                        text_usuario.setText(documentSnapshot.getString("nome"));
                        //text_email.setText(emailId);
                    }
            }
        });

    }

    private void IniciarComponentes(){
            btn_adicionar_nfe = findViewById(R.id.btn_adicionar_nfe);
            btn_sair = findViewById(R.id.btn_sair);
            btn_criar_viagem = findViewById(R.id.btn_criar_viagem);

            text_usuario = findViewById(R.id.text_usuario);

        }
    }

