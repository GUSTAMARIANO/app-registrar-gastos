package com.example.camerax;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PrimeiraTela extends AppCompatActivity {

    Button btn_adicionar_nfe;
    TextView texto;

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
        texto = findViewById(R.id.textView);
        btn_adicionar_nfe = findViewById(R.id.btn_adicionar_nfe);
        btn_adicionar_nfe.setOnClickListener(v -> {
            IrParaProximaTela();
        });

        }
        private void IrParaProximaTela() {

//            Intent  tela  =  new  Intent( PrimeiraTela.this , MainActivity.class );
            Intent tela = new Intent(PrimeiraTela.this, MainActivity.class);
            startActivity (tela);
        };
    }

