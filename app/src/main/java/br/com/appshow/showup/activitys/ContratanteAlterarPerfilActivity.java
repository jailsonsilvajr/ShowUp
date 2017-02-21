package br.com.appshow.showup.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Contratante;
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
    private EditText contratante_alterar_perfil_content_edittext_email;
    private EditText contratante_alterar_perfil_content_edittext_cpf_cnpj;
    private EditText contratante_alterar_perfil_content_edittext_cep;
    private EditText contratante_alterar_perfil_content_edittext_nascimento;
    private EditText contratante_alterar_perfil_content_edittext_celular;
    private Button contratante_alterar_perfil_content_button_alterar;

    //Código de requerimento da imagem
    private int PICK_IMAGE_REQUEST = 1;

    //Uri to store the image uri
    private Uri filePath;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

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
        if(contratante.getUrl_foto_perfil().equals("")){

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
        contratante_alterar_perfil_content_edittext_email = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_email);
        contratante_alterar_perfil_content_edittext_cpf_cnpj = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_cpf_cnpj);
        contratante_alterar_perfil_content_edittext_cep = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_cep);
        contratante_alterar_perfil_content_edittext_nascimento = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_nascimento);
        contratante_alterar_perfil_content_edittext_celular = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_celular);
        contratante_alterar_perfil_content_button_alterar = (Button) findViewById(R.id.contratante_alterar_perfil_content_button_alterar);
        //Adicionar a foto do perfil:
        if(contratante.getUrl_foto_perfil().equals("")){

            contratante_alterar_perfil_content_image_perfil.setImageResource(R.drawable.foto_perfil);
        }else{

            Picasso.with(this)
                    .load(contratante.getUrl_foto_perfil())
                    .into(contratante_alterar_perfil_content_image_perfil);
        }
        //Adicionar nome do perfil:
        contratante_alterar_perfil_content_edittext_nome.setText(contratante.getNome());
        //Adicionar email do perfil:
        contratante_alterar_perfil_content_edittext_email.setText(contratante.getEmail());
        //Adicionar cpf/cnpj do perfil:
        contratante_alterar_perfil_content_edittext_cpf_cnpj.setText(contratante.getCpf_cnpj());
        //Adicionar cep do perfil:
        contratante_alterar_perfil_content_edittext_cep.setText(contratante.getCep());
        //Adicionar nascimento do perfil:
        contratante_alterar_perfil_content_edittext_nascimento.setText(contratante.getNascimento());
        //Adicionar celular do perfil:
        contratante_alterar_perfil_content_edittext_celular.setText(contratante.getNumero_celular());

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

    //Metodo para escolher a foto de perfil
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                contratante_alterar_perfil_content_image_perfil.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
