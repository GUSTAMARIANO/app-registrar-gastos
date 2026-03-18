package com.example.camerax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btnpicture;
    ImageView imageView;

    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnpicture = findViewById(R.id.btncamera_id);
        imageView = findViewById(R.id.imageview1);


        //------------------------------------------------------------------------------

        //Variavéis responsaveis por armazena a foto
        File photoFile = new File(getExternalFilesDir(null), "photo.jpg");
        Uri photoURI = FileProvider.getUriForFile(
                this,
                getPackageName() + ".provider",
                photoFile
        );

        //Ativação do metodo após o evento de click
        btnpicture.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            cameraLauncher.launch(cameraIntent);
        });



        // Metodo responsavel por exibir a foto
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK) {

                        //Intent data = result.getData();

                            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);

                    }
                }
        );

        //---------------------------------------------------------------------------------


    }
}