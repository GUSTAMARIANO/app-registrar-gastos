package com.example.camerax;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class tela_cadastro extends AppCompatActivity {

    private EditText edit_nome,edit_email,edit_senha;
    private Button btn_cadastrar;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    String usuarioId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Função para linkar componentes
        IniciarComponentes();

        //Evento de click
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    //Exbir mensagens ao usuario
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE );
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else{
                    CadastrarUsuario(v);
                }
            }
        });
    }

    private void CadastrarUsuario(View v){
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Conexão com banco de dados
                    SalvarUsuario();

                    //Exbir mensagens ao usuario
                    Snackbar snackbar = Snackbar.make(v,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE );
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                    IrParaTela_Login();
                } else{
                    //Tratamento de erros
                    String erro;
                    try {
                        throw task.getException();

                    } catch(FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres";

                    } catch (FirebaseAuthUserCollisionException e){
                        erro = "Esse email já foi cadastrado";

                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail inválido";

                    } catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }

                    //Exbir mensagens ao usuario
                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE );
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void SalvarUsuario(){
        String nome = edit_nome.getText().toString();

        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);

        usuarioId =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = dataBase.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("dataBase","Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.d("dataBase_error", "Falha ao salvar os dados" + e.toString());
                    }
                });
    }

    private void IrParaTela_Login() {
        Intent tela = new Intent(tela_cadastro.this, TelaLogin.class);
        startActivity(tela);
        finish();
    }

    private void IniciarComponentes(){
        edit_email = findViewById(R.id.edit_email_cdt);
        edit_nome = findViewById(R.id.edit_nome);
        edit_senha = findViewById(R.id.edit_senha_cdt);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
    }
}