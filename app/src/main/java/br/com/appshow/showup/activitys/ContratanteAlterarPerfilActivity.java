package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import br.com.appshow.showup.R;
import br.com.appshow.showup.conexao.Conectar;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.util.Image;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 08/02/17.
 */

public class ContratanteAlterarPerfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Contratante contratante;

    private ImageView contratante_alterar_perfil_nav_header_image_background;
    private Button contratante_alterar_perfil_nav_header_button_configuracao;
    private CircleImageView contratante_alterar_perfil_nav_header_image_perfil;
    private TextView contratante_alterar_perfil_nav_header_textview_nome;

    private CircleImageView contratante_alterar_perfil_content_image_perfil;
    private EditText contratante_alterar_perfil_content_edittext_nome;
    private  EditText contratante_alterar_perfil_content_edittext_sobrenome;
    private EditText contratante_alterar_perfil_content_edittext_email;
    private EditText contratante_alterar_perfil_content_edittext_cpf;
    private EditText contratante_alterar_perfil_content_edittext_telefone;
    private Button contratante_alterar_perfil_content_button_alterar;

    private static final int IMG_CAM = 1;
    private static final int IMG_SDCARD = 2;
    private Image image;


    //Código de requerimento da imagem
    private int PICK_IMAGE_REQUEST = 1;

    //Uri to store the image uri
    private Uri filePath;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    private String url;
    private String parametros;

    RelativeLayout contratante_alterar_parfil_app_bar_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_alterar_perfil_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_alterar_perfil_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_alterar_perfil_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_alterar_perfil_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("PERFIL");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");

        image = new Image();
        contratante_alterar_parfil_app_bar_progressBar = (RelativeLayout) findViewById(R.id.contratante_alterar_parfil_app_bar_progressBar);
        contratante_alterar_parfil_app_bar_progressBar.setVisibility(View.GONE);

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        contratante_alterar_perfil_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_image_background);
        contratante_alterar_perfil_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_button_configuracao);
        contratante_alterar_perfil_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_image_perfil);
        contratante_alterar_perfil_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_textview_nome);
        //Conf. background do menu lateral:
        contratante_alterar_perfil_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        //Conf. imagem do perfil no menu lateral:
        if(contratante.getUrl_foto_perfil() == null || contratante.getUrl_foto_perfil().equals("")){

            contratante_alterar_perfil_nav_header_image_perfil.setImageResource(R.drawable.foto_perfil);
        }else{

            Picasso.with(this)
                    .load(contratante.getUrl_foto_perfil())
                    .into(contratante_alterar_perfil_nav_header_image_perfil);
        }
        //Conf. nome do perfil no menu lateral:
        if(contratante.getNome().equals("")){

            contratante_alterar_perfil_nav_header_textview_nome.setText("Nome");
        }else{

            contratante_alterar_perfil_nav_header_textview_nome.setText(this.contratante.getNome());
        }
        //Conf. o botão de config.:
        contratante_alterar_perfil_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Conf. as views:
        contratante_alterar_perfil_content_image_perfil = (CircleImageView) findViewById(R.id.contratante_alterar_perfil_image_perfil);
        contratante_alterar_perfil_content_edittext_nome = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_nome);
        contratante_alterar_perfil_content_edittext_sobrenome = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_sobrenome);
        contratante_alterar_perfil_content_edittext_email = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_email);
        contratante_alterar_perfil_content_edittext_cpf = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_cpf);
        contratante_alterar_perfil_content_edittext_telefone = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_telefone);
        contratante_alterar_perfil_content_button_alterar = (Button) findViewById(R.id.contratante_alterar_perfil_content_button_alterar);
        //Adicionar a foto do perfil:
        if(contratante.getUrl_foto_perfil() == null || contratante.getUrl_foto_perfil().equals("")){

            contratante_alterar_perfil_content_image_perfil.setImageResource(R.drawable.foto_perfil);
        }else{

            Picasso.with(this)
                    .load(contratante.getUrl_foto_perfil())
                    .into(contratante_alterar_perfil_content_image_perfil);
        }
        //Adicionar nome do perfil:
        contratante_alterar_perfil_content_edittext_nome.setText(contratante.getNome());
        //Adicionar sobrenome do perfil:
        contratante_alterar_perfil_content_edittext_sobrenome.setText(contratante.getSobrenome());
        //Adicionar email do perfil:
        contratante_alterar_perfil_content_edittext_email.setText(contratante.getEmail());
        //Adicionar cpf/cnpj do perfil:
        contratante_alterar_perfil_content_edittext_cpf.setText(contratante.getCpf());
        //Adicionar celular do perfil:
        contratante_alterar_perfil_content_edittext_telefone.setText(contratante.getTelefone());

        //Adicionar ação ao clicar na foto do perfil:
        contratante_alterar_perfil_content_image_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFileChooser();
            }
        });
        //Adicionar ação ao clicar no botão de alterar perfil:
        contratante_alterar_perfil_content_button_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alterar_perfil();
            }
        });
        //--Fim de (2)

    }

    public void alterar_perfil(){

        enableViews(false);

        url = Conectar.url_servidor + "alterar_perfil_contratante.php?";
        parametros = "";
        parametros += "cpf_atual=" + contratante.getCpf();
        parametros += "&cpf_novo=" + contratante_alterar_perfil_content_edittext_cpf.getText().toString();
        parametros += "&nome=" + contratante_alterar_perfil_content_edittext_nome.getText().toString();
        parametros += "&sobrenome=" + contratante_alterar_perfil_content_edittext_sobrenome.getText().toString();
        parametros += "&email=" + contratante_alterar_perfil_content_edittext_email.getText().toString();
        parametros += "&telefone=" + contratante_alterar_perfil_content_edittext_telefone.getText().toString();
        if(image.getBitmap() != null){

            parametros += "&img_mime=" + image.getMime();
            parametros += "&img_image=" + image.getBitmapBase64();
        }else{

            parametros += "&img_mime=" + "null";
            parametros += "&img_image=" + "null";
        }

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            new AlterarDados().execute(url);
        }else{

            Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
        }
    }

    public void enableViews(boolean enable){

        contratante_alterar_perfil_content_image_perfil.setEnabled(enable);
        contratante_alterar_perfil_content_edittext_nome.setEnabled(enable);
        contratante_alterar_perfil_content_edittext_email.setEnabled(enable);
        contratante_alterar_perfil_content_edittext_cpf.setEnabled(enable);
        contratante_alterar_perfil_content_edittext_telefone.setEnabled(enable);
        contratante_alterar_perfil_content_button_alterar.setEnabled(enable);
        contratante_alterar_perfil_content_button_alterar.setText(enable ? "ALTERAR" : "ALTERANDO...");
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_alterar_perfil_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.contratante_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);

        } else if (id == R.id.contratante_alterar_perfil_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_alterar_perfil_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Metodo para abrir galeria
    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_SDCARD);
    }

    // CALL IN CAM
    /*public void callIntentImgCam(View view){
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "img.png");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, IMG_CAM);
    }*/

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

                image.setResizedBitmap(file, 180, 180);
                image.setMimeFromImgPath(file.getPath());
            }
        }
        else if(requestCode == IMG_CAM && resultCode == RESULT_OK){
            file = new File(android.os.Environment.getExternalStorageDirectory(), "img.png");
            if(file != null){

                image.setResizedBitmap(file, 300, 300);
                image.setMimeFromImgPath(file.getPath());
            }
        }


        if(image.getBitmap() != null){
            contratante_alterar_perfil_content_image_perfil.setImageBitmap(image.getBitmap());
        }
    }

    private class AlterarDados extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            contratante_alterar_parfil_app_bar_progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            Toast.makeText(ContratanteAlterarPerfilActivity.this, result, Toast.LENGTH_SHORT).show();
            enableViews(true);
            contratante_alterar_parfil_app_bar_progressBar.setVisibility(View.GONE);
        }
    }
}
