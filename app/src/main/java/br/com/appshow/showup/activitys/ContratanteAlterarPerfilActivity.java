package br.com.appshow.showup.activitys;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Contratante;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 08/02/17.
 */

public class ContratanteAlterarPerfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Contratante contratante;
    private String novo_nome;
    private String novo_email;
    private String novo_cpf_cnpj;
    private String novo_cep;
    private String novo_nascimento;
    private String novo_numero_celular;

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
        ImageView contratante_alterar_perfil_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_image_background);
        Button contratante_alterar_perfil_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_button_configuracao);
        CircleImageView contratante_alterar_perfil_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_image_perfil);
        TextView contratante_alterar_perfil_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_alterar_perfil_nav_header_textview_nome);

        contratante_alterar_perfil_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        contratante_alterar_perfil_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_alterar_perfil_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_alterar_perfil_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Obter os novos atributos:
        final CircleImageView contratante_alterar_perfil_image_perfil = (CircleImageView) findViewById(R.id.contratante_alterar_perfil_image_perfil);
        final EditText contratante_alterar_perfil_content_edittext_nome = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_nome);
        final EditText contratante_alterar_perfil_content_edittext_email = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_email);
        final EditText contratante_alterar_perfil_content_edittext_cpf_cnpj = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_cpf_cnpj);
        final EditText contratante_alterar_perfil_content_edittext_cep = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_cep);
        final EditText contratante_alterar_perfil_content_edittext_nascimento = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_nascimento);
        final EditText contratante_alterar_perfil_content_edittext_celular = (EditText) findViewById(R.id.contratante_alterar_perfil_content_edittext_celular);
        Button contratante_alterar_perfil_content_button_alterar = (Button) findViewById(R.id.contratante_alterar_perfil_content_button_alterar);
        contratante_alterar_perfil_content_button_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                novo_nome = contratante_alterar_perfil_content_edittext_nome.getText().toString();
                novo_email = contratante_alterar_perfil_content_edittext_email.getText().toString();
                novo_cpf_cnpj = contratante_alterar_perfil_content_edittext_cpf_cnpj.getText().toString();
                novo_cep = contratante_alterar_perfil_content_edittext_cep.getText().toString();
                novo_nascimento = contratante_alterar_perfil_content_edittext_nascimento.getText().toString();
                novo_numero_celular = contratante_alterar_perfil_content_edittext_celular.getText().toString();
                alterar_perfil();//TEMPORÁRIOOOOO
            }
        });
        //--Fim de (2)

    }

    public void alterar_perfil(){

        Toast.makeText(this, novo_nome+"\n"+
                novo_email+"\n"+
                novo_cpf_cnpj+"\n"+
                novo_cep +"\n"+
                novo_nascimento+"\n"+
                novo_numero_celular+"\n", Toast.LENGTH_SHORT).show();
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
}
