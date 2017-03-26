package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.appshow.showup.R;
import br.com.appshow.showup.conexao.Conectar;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.util.Image;
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

    private static final int IMG_CAM = 1;
    private static final int IMG_SDCARD = 2;
    ArrayList<Image> images;
    private Image image2;
    private Image image3;

    private int imageviewclick;

    private ImageView contratante_criar_evento_content_imagem_evento1;
    private ImageView contratante_criar_evento_content_imagem_evento2;
    private ImageView contratante_criar_evento_content_imagem_evento3;
    private EditText contratante_criar_evento_edittext_nome;
    private EditText contratante_criar_evento_edittext_local;
    private Spinner contratante_criar_evento_spinner_estado;
    private Spinner contratante_criar_evento_spinner_cidade;
    private EditText contratante_criar_evento_edittext_bairro;
    private EditText contratante_criar_evento_edittext_rua;
    private EditText contratante_criar_evento_edittext_numero;
    private EditText contratante_criar_evento_edittext_descricao;
    private EditText contratante_criar_evento_edittext_data;
    private EditText contratante_criar_evento_edittext_horario_inicio;
    private EditText contratante_criar_evento_edittext_horario_fim;
    private EditText contratante_criar_evento_edittext_equipamentos;
    private EditText contratante_criar_evento_edittext_requisitos;
    private Button contratante_criar_evento_button_criar;

    private ImageView contratante_criar_evento_nav_header_image_background;
    private Button contratante_criar_evento_nav_header_button_configuracao;
    private CircleImageView contratante_criar_evento_nav_header_image_perfil;
    private TextView contratante_criar_evento_nav_header_textview_nome;

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

        images = new ArrayList<Image>();
        images.add(new Image());
        images.add(new Image());
        images.add(new Image());
        image2 = new Image();
        image3 = new Image();

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        contratante_criar_evento_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_inicio_nav_header_image_background);
        contratante_criar_evento_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_inicio_nav_header_button_configuracao);
        contratante_criar_evento_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_inicio_nav_header_image_perfil);
        contratante_criar_evento_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_inicio_nav_header_textview_nome);

        contratante_criar_evento_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        if(contratante.getUrl_foto_perfil() == null || contratante.getUrl_foto_perfil().equals("")){

            contratante_criar_evento_nav_header_image_perfil.setImageResource(R.drawable.foto_perfil);
        }else{

            Picasso.with(this)
                    .load(contratante.getUrl_foto_perfil())
                    .into(contratante_criar_evento_nav_header_image_perfil);
        }

        String nome_completo = this.contratante.getNome() + " " + this.contratante.getSobrenome();
        contratante_criar_evento_nav_header_textview_nome.setText(nome_completo);

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
        contratante_criar_evento_edittext_nome = (EditText) findViewById(R.id.contratante_criar_evento_edittext_nome);
        contratante_criar_evento_edittext_local = (EditText) findViewById(R.id.contratante_criar_evento_edittext_local);
        contratante_criar_evento_spinner_estado = (Spinner) findViewById(R.id.contratante_criar_evento_spinner_estado);
        contratante_criar_evento_spinner_cidade = (Spinner) findViewById(R.id.contratante_criar_evento_spinner_cidade);
        contratante_criar_evento_edittext_bairro = (EditText) findViewById(R.id.contratante_criar_evento_edittext_bairro);
        contratante_criar_evento_edittext_rua = (EditText) findViewById(R.id.contratante_criar_evento_edittext_rua);
        contratante_criar_evento_edittext_numero = (EditText) findViewById(R.id.contratante_criar_evento_edittext_numero);
        contratante_criar_evento_edittext_descricao = (EditText) findViewById(R.id.contratante_criar_evento_edittext_descricao);
        contratante_criar_evento_edittext_data = (EditText) findViewById(R.id.contratante_criar_evento_edittext_data);
        contratante_criar_evento_edittext_horario_inicio = (EditText) findViewById(R.id.contratante_criar_evento_edittext_horario_inicio);
        contratante_criar_evento_edittext_horario_fim = (EditText) findViewById(R.id.contratante_criar_evento_edittext_horario_fim);
        contratante_criar_evento_edittext_equipamentos = (EditText) findViewById(R.id.contratante_criar_evento_edittext_equipamentos);
        contratante_criar_evento_edittext_requisitos = (EditText) findViewById(R.id.contratante_criar_evento_edittext_requisitos);

        contratante_criar_evento_content_imagem_evento1 = (ImageView) findViewById(R.id.contratante_criar_evento_content_imagem_evento1);
        contratante_criar_evento_content_imagem_evento1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageviewclick = 1;
                showFileChooser();
            }
        });
        contratante_criar_evento_content_imagem_evento2 = (ImageView) findViewById(R.id.contratante_criar_evento_content_imagem_evento2);
        contratante_criar_evento_content_imagem_evento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageviewclick = 2;
                showFileChooser();
            }
        });
        contratante_criar_evento_content_imagem_evento3 = (ImageView) findViewById(R.id.contratante_criar_evento_content_imagem_evento3);
        contratante_criar_evento_content_imagem_evento3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageviewclick = 3;
                showFileChooser();
            }
        });

        contratante_criar_evento_button_criar = (Button) findViewById(R.id.contratante_criar_evento_button_criar);
        contratante_criar_evento_button_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                criar_evento();
            }
        });
        //--Fim de (2)

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            url = "https://showupbr.com/appdroid/showup/json/estados-cidades.json"; //COLOCAR ESSE ARQUIVO DENTRO DO PROJETO!!
            parametros = "";
            new SolicitarDados().execute(url);
        }else{}
    }

    public void enableViews(boolean enable){

        contratante_criar_evento_button_criar.setText(enable ? "CRIAR" : "CRIANDO...");
        contratante_criar_evento_button_criar.setEnabled(enable);
    }

    public void criar_evento(){

        enableViews(false);

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

        url = Conectar.url_servidor + "criar_evento.php?";
        parametros = "";
        parametros = "cpf=" + contratante.getCpf();

        parametros += "&cep=" + "54515-580";
        parametros += "&pais=" + "Brasil";
        parametros += "&estado=" + estado;
        parametros += "&cidade=" + cidade;
        parametros += "&bairro=" + bairro;
        parametros += "&rua=" + rua;
        parametros += "&numero=" + numero;
        parametros += "&complemento=" + null;

        parametros += "&nome=" + nome;
        parametros += "&nome_local=" + local;
        parametros += "&descricao=" + descricao;
        parametros += "&data=" + data;
        parametros += "&horario_inicio=" + horario_inicio;
        parametros += "&horario_fim=" + horario_fim;
        parametros += "&equipamentos=" + equipamentos;
        parametros += "&requisitos=" + requisitos;

        //Mandando apenas imagem1... depois conf pra mandar as outras!
        if(images.get(0).getBitmap() != null && images.get(1).getBitmap() != null && images.get(2).getBitmap() != null){

            parametros += "&img1_mime=" + images.get(0).getMime();
            parametros += "&img1_image=" + images.get(0).getBitmapBase64();

            parametros += "&img2_mime=" + images.get(1).getMime();
            parametros += "&img2_image=" + images.get(1).getBitmapBase64();

            parametros += "&img3_mime=" + images.get(2).getMime();
            parametros += "&img3_image=" + images.get(2).getBitmapBase64();
        }

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            new CriarEvento().execute(url);
        }else{

            Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
        }
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
        if (id == R.id.contratante_inicio_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_inicio_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_criar_evento_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Metodo para abrir galeria
    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_SDCARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        File file = null;
        if(data != null && requestCode == IMG_SDCARD && resultCode == RESULT_OK){

            Uri img = data.getData();
            String[] cols = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(img, cols, null, null, null);
            cursor.moveToFirst();

            int indexCol = cursor.getColumnIndex(cols[0]);

            String imgString = cursor.getString(indexCol);
            cursor.close();

            file = new File(imgString);
            if(file != null){

                if(imageviewclick == 1){

                    images.get(0).setResizedBitmap(file, 600, 180);
                    images.get(0).setMimeFromImgPath(file.getPath());

                    images.get(1).setResizedBitmap(file, 200, 180);
                    images.get(1).setMimeFromImgPath(file.getPath());

                    images.get(2).setResizedBitmap(file, 100, 180);
                    images.get(2).setMimeFromImgPath(file.getPath());
                }else if(imageviewclick == 2){

                    image2.setResizedBitmap(file, 600, 180);
                    image2.setMimeFromImgPath(file.getPath());
                }else{

                    image3.setResizedBitmap(file, 600, 180);
                    image3.setMimeFromImgPath(file.getPath());
                }
            }
        }
        else if(requestCode == IMG_CAM && resultCode == RESULT_OK){
            file = new File(android.os.Environment.getExternalStorageDirectory(), "img.png");
            if(file != null){

                /*images.get(0).setResizedBitmap(file, 600, 180);
                images.get(0).setMimeFromImgPath(file.getPath());

                images.get(1).setResizedBitmap(file, 200, 180);
                images.get(1).setMimeFromImgPath(file.getPath());

                images.get(2).setResizedBitmap(file, 100, 180);
                images.get(2).setMimeFromImgPath(file.getPath());*/
            }
        }

        if(imageviewclick == 1){

            if(images.get(0).getBitmap() != null && images.get(1).getBitmap() != null && images.get(2).getBitmap() != null){

                contratante_criar_evento_content_imagem_evento1.setImageBitmap(images.get(0).getBitmap());
            }
        }else if(imageviewclick == 2){

            if(image2.getBitmap() != null){

                contratante_criar_evento_content_imagem_evento2.setImageBitmap(image2.getBitmap());
            }
        }else{

            if(image3.getBitmap() != null){

                contratante_criar_evento_content_imagem_evento3.setImageBitmap(image3.getBitmap());
            }
        }
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
            enableViews(true);

            //contratante_criar_evento_content_layout_progressBar.setVisibility(View.GONE);
            //contratante_criar_evento_content_layout_principal.setVisibility(View.VISIBLE);
        }
    }
}
