package br.com.appshow.showup.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import br.com.appshow.showup.R;

/**
 * Created by jailson on 04/02/17.
 */

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_content);

        ImageView cadastro_content_imageview_artista = (ImageView) findViewById(R.id.cadastro_content_imageview_artista);
        cadastro_content_imageview_artista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_cadastro_artista();
            }
        });

        ImageView cadastro_content_imageview_contratante = (ImageView) findViewById(R.id.cadastro_content_imageview_contratante);
        cadastro_content_imageview_contratante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_cadastro_contratante();
            }
        });
    }

    public void open_cadastro_artista(){}

    public void open_cadastro_contratante(){

        Intent activity_cadastro_contratante = new Intent(this, CadastroContratanteActivity.class);
        startActivity(activity_cadastro_contratante);
    }
}
