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

import java.util.StringTokenizer;

import br.com.appshow.showup.R;
import br.com.appshow.showup.conexao.Conectar;

/**
 * Created by jailson on 04/02/17.
 */

public class CadastroArtistaActivity extends AppCompatActivity {

    private String url;
    private String parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_artista_content);

        Button cadastro_artista_content_button_enviar = (Button) findViewById(R.id.cadastro_artista_content_button_enviar);
        cadastro_artista_content_button_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enviar_cadastro();
            }
        });
    }

    public void enviar_cadastro(){

        //EditText cadastro_artista_content_edittext_nome = (EditText) findViewById(R.id.cadastro_contratante_content_edittext_nome);
        EditText cadastro_artista_content_edittext_email = (EditText) findViewById(R.id.cadastro_artista_content_edittext_email);
        EditText cadastro_artista_content_edittext_senha = (EditText) findViewById(R.id.cadastro_artista_content_edittext_senha);
        EditText cadastro_artista_content_edittext_senha2 = (EditText) findViewById(R.id.cadastro_artista_content_edittext_senha2);

        //String nome = cadastro_artista_content_edittext_nome.getText().toString();
        String email = cadastro_artista_content_edittext_email.getText().toString();
        String senha = cadastro_artista_content_edittext_senha.getText().toString();
        String senha2 = cadastro_artista_content_edittext_senha2.getText().toString();

        if(!senha2.equals(senha)){

            Toast.makeText(this, "As senhas não são iguais!", Toast.LENGTH_SHORT).show();
        }else{

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){

                url = "https://showupbr.com/showup/cadastrar_artista_app.php?";
                parametros = "email=" + email + "&senha=" + senha;
                new SolicitarDados().execute(url);
            }else{

                Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SolicitarDados extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            StringTokenizer str = new StringTokenizer(result);
            String token = str.nextToken();

            if(token.equals("cadastro_erro3")){

                Toast.makeText(CadastroArtistaActivity.this, "E-mail já cadastrado!", Toast.LENGTH_SHORT).show();
            }else if(token.equals("cadastro_erro1") || token.equals("cadastro_erro2")){

                Toast.makeText(CadastroArtistaActivity.this, "Erro: Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                Toast.makeText(CadastroArtistaActivity.this, token, Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(CadastroArtistaActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent activity_login = new Intent(CadastroArtistaActivity.this, LoginActivity.class);
                startActivity(activity_login);
            }
        }
    }
}
