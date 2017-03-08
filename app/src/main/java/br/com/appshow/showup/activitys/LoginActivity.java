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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    static RelativeLayout login_content_layout_progressBar;

    Button button_entrar;

    EditText textView_email;
    EditText textView_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_content);

        login_content_layout_progressBar = (RelativeLayout) findViewById(R.id.login_content_layout_progressBar);
        login_content_layout_progressBar.setVisibility(View.GONE);

        textView_email = (EditText) findViewById(R.id.login_content_textview_email);
        textView_senha = (EditText) findViewById(R.id.login_content_textview_senha);

        button_entrar = (Button) findViewById(R.id.login_content_button_entrar);
        button_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fazer_login();
            }
        });
    }

    public void fazer_login(){

        setEmail(textView_email.getText().toString());
        setSenha(textView_senha.getText().toString());

        if(getEmail().isEmpty() || getSenha().isEmpty()){

            Toast.makeText(this, "Email e/ou Senha inválidos!", Toast.LENGTH_SHORT).show();
        }else{

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){

                url = Conectar.url_servidor + "logar.php?";
                parametros = "email=" + getEmail() + "&senha=" + getSenha();
                new SolicitarDados().execute(url);
            }else{

                Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SolicitarDados extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login_content_layout_progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            //Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
            StringTokenizer str = new StringTokenizer(result);
            String token1 = str.nextToken();

            if(token1.equals("conexao_erro")){

                Toast.makeText(LoginActivity.this, "Problema com o Servidor", Toast.LENGTH_SHORT).show();
            }else if(token1.equals("login_erro")){

                Toast.makeText(LoginActivity.this, "Email e/ou Senha incorreto(s)!", Toast.LENGTH_SHORT).show();
            }else{

                String token2 = str.nextToken();

                if(token1.equals("artista")){

                    Gson gson = new Gson();
                    Artista[] artista_array = gson.fromJson(token2, Artista[].class);

                    List artista_lista = Arrays.asList(artista_array);
                    ArrayList<Artista> artista = new ArrayList(artista_lista);

                    Intent artista_inicial_activity = new Intent(LoginActivity.this, ArtistaInicioActivity.class);
                    artista_inicial_activity.putExtra("paramsArtista", artista.get(0));
                    startActivity(artista_inicial_activity);
                }else{

                    Gson gson = new Gson();
                    Contratante[] contratante_array = gson.fromJson(token2, Contratante[].class);

                    List contratante_lista = Arrays.asList(contratante_array);
                    ArrayList<Contratante> contratante = new ArrayList(contratante_lista);

                    Intent contratante_inicial_activity = new Intent(LoginActivity.this, ContratanteInicioActivity.class);
                    contratante_inicial_activity.putExtra("paramsContratante", contratante.get(0));
                    startActivity(contratante_inicial_activity);
                }
            }
            login_content_layout_progressBar.setVisibility(View.GONE);
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
