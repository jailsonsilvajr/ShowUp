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
import android.widget.ImageView;
import android.widget.TextView;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 04/02/17.
 */

public class ArtistaConfiguracoesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Artista artista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.artista_configuracoes_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.artista_configuracoes_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_configuracoes_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.artista_configuracoes_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("CONFIGURAÇÕES");

        Intent intent = getIntent();
        this.artista = intent.getParcelableExtra("paramsArtista");

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView artista_configuracoes_nav_header_image_background = (ImageView) hView.findViewById(R.id.artista_configuracoes_nav_header_image_background);
        Button artista_configuracoes_nav_header_button_configuracao = (Button) hView.findViewById(R.id.artista_configuracoes_nav_header_button_configuracao);
        CircleImageView artista_configuracoes_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.artista_configuracoes_nav_header_image_perfil);
        TextView artista_configuracoes_nav_header_textview_nome = (TextView) hView.findViewById(R.id.artista_configuracoes_nav_header_textview_nome);

        artista_configuracoes_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        artista_configuracoes_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        artista_configuracoes_nav_header_textview_nome.setText(this.artista.getNome());
        artista_configuracoes_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Configurar button conta:
        Button artista_configuracoes_content_button_conta = (Button) findViewById(R.id.artista_configuracoes_content_button_conta);
        artista_configuracoes_content_button_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_conta();
            }
        });

    }

    public void open_activity_conta(){

        Intent activity_conta = new Intent(this, ArtistaContaActivity.class);
        activity_conta.putExtra("paramsArtista", this.artista);
        startActivity(activity_conta);
    }

    public void open_activity_configuracao(){

        Intent activity_configura = new Intent(this, ArtistaConfiguracoesActivity.class);
        activity_configura.putExtra("paramsArtista", this.artista);
        startActivity(activity_configura);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_configuracoes_drawer_layout);
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
        if (id == R.id.artista_configuracoes_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.artista_configuracoes_drawer_menu_nav_agenda) {
            return true;
        } if (id == R.id.artista_configuracoes_drawer_menu_nav_promova) {
            return true;
        } if (id == R.id.artista_configuracoes_drawer_menu_nav_historico) {
            return true;
        } if (id == R.id.artista_configuracoes_drawer_menu_nav_sair) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.artista_configuracoes_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.artista_configuracoes_drawer_menu_nav_agenda) {

        } else if (id == R.id.artista_configuracoes_drawer_menu_nav_promova) {

            Intent activity_promovase = new Intent(this, ArtistaPromovaSeActivity.class);
            activity_promovase.putExtra("paramsArtista", this.artista);
            startActivity(activity_promovase);
        } else if (id == R.id.artista_configuracoes_drawer_menu_nav_historico) {

        } else if (id == R.id.artista_configuracoes_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_configuracoes_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
