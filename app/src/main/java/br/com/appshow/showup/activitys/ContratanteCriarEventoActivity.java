package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.appshow.showup.R;
import br.com.appshow.showup.conexao.Conectar;
import br.com.appshow.showup.entidades.Contratante;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 07/02/17.
 */

public class ContratanteCriarEventoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;
    private String nome;
    private String local;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String descricao;
    private String data;
    private String horario_inicio;
    private String horario_fim;
    private String equipamentos;
    private String requisitos;
    private String imagem_principal;
    private String imagem_secundaria;
    private String imagem_redonda;
    Spinner spinner_estado;
    Spinner spinner_cidade;

    String url;
    String parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_criar_evento_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_criar_evento_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_criar_evento_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_criar_evento_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("CRIE SEU EVENTO");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_criar_evento_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_criar_evento_nav_header_image_background);
        Button contratante_criar_evento_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_criar_evento_nav_header_button_configuracao);
        CircleImageView contratante_criar_evento_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_criar_evento_nav_header_image_perfil);
        TextView contratante_criar_evento_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_criar_evento_nav_header_textview_nome);

        contratante_criar_evento_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        if(contratante.getUrl_foto_perfil().equals("")){

            contratante_criar_evento_nav_header_image_perfil.setImageResource(R.drawable.foto_perfil);
        }else{

            Picasso.with(this)
                    .load(contratante.getUrl_foto_perfil())
                    .into(contratante_criar_evento_nav_header_image_perfil);
        }
        if(contratante.getNome().equals("")){

            contratante_criar_evento_nav_header_textview_nome.setText("Nome");
        }else{

            contratante_criar_evento_nav_header_textview_nome.setText(this.contratante.getNome());
        }
        contratante_criar_evento_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        contratante_criar_evento_nav_header_image_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_alterar_perfil();
            }
        });
        //--Fim de (1)

        //--(2) Configurando views:
        final EditText contratante_criar_evento_edittext_nome = (EditText) findViewById(R.id.contratante_criar_evento_edittext_nome);
        final EditText contratante_criar_evento_edittext_local = (EditText) findViewById(R.id.contratante_criar_evento_edittext_local);
        final Spinner contratante_criar_evento_spinner_estado = (Spinner) findViewById(R.id.contratante_criar_evento_spinner_estado);
        final Spinner contratante_criar_evento_spinner_cidade = (Spinner) findViewById(R.id.contratante_criar_evento_spinner_cidade);
        final EditText contratante_criar_evento_edittext_bairro = (EditText) findViewById(R.id.contratante_criar_evento_edittext_bairro);
        final EditText contratante_criar_evento_edittext_rua = (EditText) findViewById(R.id.contratante_criar_evento_edittext_rua);
        final EditText contratante_criar_evento_edittext_numero = (EditText) findViewById(R.id.contratante_criar_evento_edittext_numero);
        final EditText contratante_criar_evento_edittext_descricao = (EditText) findViewById(R.id.contratante_criar_evento_edittext_descricao);
        final EditText contratante_criar_evento_edittext_data = (EditText) findViewById(R.id.contratante_criar_evento_edittext_data);
        final EditText contratante_criar_evento_edittext_horario_inicio = (EditText) findViewById(R.id.contratante_criar_evento_edittext_horario_inicio);
        final EditText contratante_criar_evento_edittext_horario_fim = (EditText) findViewById(R.id.contratante_criar_evento_edittext_horario_fim);
        final EditText contratante_criar_evento_edittext_equipamentos = (EditText) findViewById(R.id.contratante_criar_evento_edittext_equipamentos);
        final EditText contratante_criar_evento_edittext_requisitos = (EditText) findViewById(R.id.contratante_criar_evento_edittext_requisitos);
        Button contratante_criar_evento_button_criar = (Button) findViewById(R.id.contratante_criar_evento_button_criar);

        contratante_criar_evento_button_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nome = contratante_criar_evento_edittext_nome.getText().toString();
                local = contratante_criar_evento_edittext_local.getText().toString();
                bairro = contratante_criar_evento_edittext_bairro.getText().toString();
                rua = contratante_criar_evento_edittext_rua.getText().toString();
                numero = contratante_criar_evento_edittext_numero.getText().toString();
                descricao = contratante_criar_evento_edittext_descricao.getText().toString();
                data = contratante_criar_evento_edittext_data.getText().toString();
                horario_inicio = contratante_criar_evento_edittext_horario_inicio.getText().toString();
                horario_fim = contratante_criar_evento_edittext_horario_fim.getText().toString();
                equipamentos = contratante_criar_evento_edittext_equipamentos.getText().toString();
                requisitos = contratante_criar_evento_edittext_requisitos.getText().toString();

                //showToast();//temporário

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()){

                    url = "https://showupbr.com/showup/criar_evento.php?";
                    parametros = "id_contratante=" + contratante.getId_contratante() +
                                 "&nome=" + nome + "&local=" + local + "&estado=" + estado +
                                 "&cidade=" + cidade + "&bairro=" + bairro + "&rua=" + rua +
                                 "&numero=" + numero + "&descricao=" + descricao + "&horario_inicio=" + horario_inicio +
                                 "&horario_fim=" + horario_fim + "&equipamentos=" + equipamentos + "&requisito=" + requisitos +
                                 "&url_imagem_principal=" + "http://192.241.244.47/showup/img_eventos/img_evento1_principal.png" +
                                 "&url_imagem_secundaria=" + "http://192.241.244.47/showup/img_eventos/img_evento1_secundaria.png" +
                                 "&url_imagem_redonda=" + "http://192.241.244.47/showup/img_eventos/img_evento1_redonda.png";
                    new CriarEvento().execute(url);
                }else{}
            }
        });
        //--Fim de (2)

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            url = "http://192.241.244.47/showup/json/estados-cidades.json";
            parametros = "";
            new SolicitarDados().execute(url);
        }else{}
    }

    public void conf_spinner(JSONObject jsonObjetoEstado){

        final List<String> cidades = new ArrayList<String>();
        cidades.add("Cidade:");

        if(jsonObjetoEstado!= null ){

            try{

                JSONArray jsonArrayCidades = jsonObjetoEstado.getJSONArray("cidades");
                for(int i = 0; i < jsonArrayCidades.length(); i++){

                    cidades.add(jsonArrayCidades.getString(i));
                }
            }catch(JSONException e){

                //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        spinner_cidade = (Spinner) findViewById(R.id.contratante_criar_evento_spinner_cidade);
        ArrayAdapter<String> adapter_cidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cidades);
        adapter_cidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cidade.setAdapter(adapter_cidade);
        spinner_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textColorTertiary));
                if(position == 0) cidade = null;
                else cidade = cidades.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /*public void showToast(){ //temporário

        Toast.makeText(this, nome+"\n"+local+"\n"+estado+"\n"+cidade+"\n"+bairro+"\n"+rua+"\n"+
                numero+"\n"+descricao+"\n"+data+"\n"+horario_inicio+"\n"+horario_fim+"\n"+
                equipamentos+"\n"+requisitos, Toast.LENGTH_LONG).show();
    }*/

    public void open_activity_alterar_perfil(){

        Intent activity_alterar_perfil = new Intent(this, ContratanteAlterarPerfilActivity.class);
        activity_alterar_perfil.putExtra("paramsContratante", this.contratante);
        startActivity(activity_alterar_perfil);
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_criar_evento_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.contratante_criar_evento_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_criar_evento_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_criar_evento_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_criar_evento_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_criar_evento_drawer_menu_nav_sair) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contratante_criar_evento_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_criar_evento_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_criar_evento_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_criar_evento_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);

        } else if (id == R.id.contratante_criar_evento_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_criar_evento_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SolicitarDados extends AsyncTask<String, Void, String> {

        RelativeLayout contratante_criar_evento_content_layout_progressBar = (RelativeLayout) findViewById(R.id.contratante_criar_evento_content_layout_progressBar);
        LinearLayout contratante_criar_evento_content_layout_principal = (LinearLayout) findViewById(R.id.contratante_criar_evento_content_layout_principal);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            contratante_criar_evento_content_layout_progressBar.setVisibility(View.VISIBLE);
            contratante_criar_evento_content_layout_principal.setVisibility(View.GONE);
        }

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

            final List<String> estados = new ArrayList<String>();
            estados.add("Estado:");
            if(!result.equals("conexao_erro")){

                try{

                    JSONObject jsonObject = new JSONObject(result);
                    final JSONArray jsonArrayEstado = jsonObject.getJSONArray("estados");
                    for(int i = 0; i < jsonArrayEstado.length(); i++){

                        JSONObject jsonObjectEstado = (JSONObject) jsonArrayEstado.get(i);
                        estados.add(jsonObjectEstado.getString("nome"));
                    }
                    spinner_estado = (Spinner) findViewById(R.id.contratante_criar_evento_spinner_estado);
                    ArrayAdapter<String> adapter_estado = new ArrayAdapter<String>(ContratanteCriarEventoActivity.this, android.R.layout.simple_spinner_item, estados);
                    adapter_estado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_estado.setAdapter(adapter_estado);
                    spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textColorTertiary));
                            try{

                                if(position == 0) estado = null;
                                else estado = estados.get(position);

                                JSONObject jsonObjectEstado;
                                if(position != 0) jsonObjectEstado = jsonArrayEstado.getJSONObject(position-1);
                                else jsonObjectEstado = null;
                                conf_spinner(jsonObjectEstado);
                            }catch (Exception e){}
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }catch (Exception e){

                    //Toast.makeText(MainActivity.this, "ERRO", Toast.LENGTH_LONG).show();
                }
            }else{

                Toast.makeText(ContratanteCriarEventoActivity.this, "ERRO", Toast.LENGTH_LONG).show();
            }
            contratante_criar_evento_content_layout_progressBar.setVisibility(View.GONE);
            contratante_criar_evento_content_layout_principal.setVisibility(View.VISIBLE);
        }
    }

    private class CriarEvento extends AsyncTask<String, Void, String> {

        //RelativeLayout contratante_criar_evento_content_layout_progressBar = (RelativeLayout) findViewById(R.id.contratante_criar_evento_content_layout_progressBar);
        //LinearLayout contratante_criar_evento_content_layout_principal = (LinearLayout) findViewById(R.id.contratante_criar_evento_content_layout_principal);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //contratante_criar_evento_content_layout_progressBar.setVisibility(View.VISIBLE);
            //contratante_criar_evento_content_layout_principal.setVisibility(View.GONE);
        }

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            //Toast.makeText(ContratanteCriarEventoActivity.this, result, Toast.LENGTH_LONG).show();

            if(result.equals("inserir_ok")) Toast.makeText(ContratanteCriarEventoActivity.this, "EVENTO CRIADO!", Toast.LENGTH_LONG).show();
            else{

                Toast.makeText(ContratanteCriarEventoActivity.this, "TENTE NOVAMENTE!", Toast.LENGTH_LONG).show();
            }

            //contratante_criar_evento_content_layout_progressBar.setVisibility(View.GONE);
            //contratante_criar_evento_content_layout_principal.setVisibility(View.VISIBLE);
        }
    }
}
