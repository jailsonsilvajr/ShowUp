package br.com.appshow.showup.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.entidades.Usuario;

/**
 * Created by jailson on 04/02/17.
 */

public class LoginActivity extends AppCompatActivity {

    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_content);

        Button login_content_button_entrar = (Button) findViewById(R.id.login_content_button_entrar);
        login_content_button_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fazer_login();
            }
        });
    }

    public void fazer_login(){

        EditText login_content_textview_email = (EditText) findViewById(R.id.login_content_textview_email);
        EditText login_content_textview_senha = (EditText) findViewById(R.id.login_content_textview_senha);
        setEmail(login_content_textview_email.getText().toString());
        setSenha(login_content_textview_senha.getText().toString());

        //Mandar email e senha para o banco de dados.
        //O Banco retornar um objeto do tipo Usuario ou null.
        Artista artista = new Artista("Joey Tribbiani"); //Artista para teste!!
        Contratante contratante = new Contratante("Eike Batista");
        Usuario user = new Usuario(artista, null);
        if(user != null){

            if(user.getArtista() != null){

                Intent artista_inicial_activity = new Intent(this, ArtistaInicioActivity.class);
                artista_inicial_activity.putExtra("paramsUsuario", user);
                startActivity(artista_inicial_activity);
            } else {

                Intent contratante_inicial_activity = new Intent(this, ContratanteInicioActivity.class);
                contratante_inicial_activity.putExtra("paramsUsuario", user);
                startActivity(contratante_inicial_activity);
            }
        }else{

            //Usuario e/ou senha nao encontrados
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
