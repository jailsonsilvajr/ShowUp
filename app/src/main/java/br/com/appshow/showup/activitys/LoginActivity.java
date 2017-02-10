package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.StringTokenizer;

import br.com.appshow.showup.R;
import br.com.appshow.showup.conexao.Conectar;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.entidades.Usuario;

/**
 * Created by jailson on 04/02/17.
 */

public class LoginActivity extends AppCompatActivity {

    private String email;
    private String senha;

    String url = "";
    String parametros = "";

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

        if(getEmail().isEmpty() || getSenha().isEmpty()){

            Toast.makeText(this, "Email e/ou Senha inválidos!", Toast.LENGTH_SHORT).show();
        }else{

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){

                url = "http://192.241.244.47/showup/logar_app.php?";
                parametros = "email=" + getEmail() + "&senha=" + getSenha();
                new SolicitarDados().execute(url);
            }else{

                Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SolicitarDados extends AsyncTask<String, Void, String>{

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            StringTokenizer str = new StringTokenizer(result);
            String token1 = str.nextToken();

            if(token1.equals("conexao_erro")){

                Toast.makeText(LoginActivity.this, "Servidor Offline!", Toast.LENGTH_SHORT).show();
            }else if(token1.equals("login_erro")){

                Toast.makeText(LoginActivity.this, "Login e/ou Senha incorreto(s)!", Toast.LENGTH_SHORT).show();
            }else{

                if(token1.equals("artista")){

                    Artista artista = new Artista(str.nextToken());
                    artista.setNome(str.nextToken());
                    artista.setEstilo(str.nextToken());
                    artista.setSite(str.nextToken());
                    artista.setDescricao(str.nextToken());
                    Usuario user = new Usuario(artista, null);

                    Intent artista_inicial_activity = new Intent(LoginActivity.this, ArtistaInicioActivity.class);
                    artista_inicial_activity.putExtra("paramsUsuario", user);
                    startActivity(artista_inicial_activity);
                }else{

                    Contratante contratante = new Contratante(str.nextToken());
                    contratante.setNome(str.nextToken());
                    contratante.setEmail(str.nextToken());
                    contratante.setCpf_cnpj(str.nextToken());
                    contratante.setCep(str.nextToken());
                    contratante.setNascimento(str.nextToken());
                    contratante.setNumero_celular(str.nextToken());
                    Usuario user = new Usuario(null, contratante);

                    Intent contratante_inicial_activity = new Intent(LoginActivity.this, ContratanteInicioActivity.class);
                    contratante_inicial_activity.putExtra("paramsUsuario", user);
                    startActivity(contratante_inicial_activity);
                }
            }
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
