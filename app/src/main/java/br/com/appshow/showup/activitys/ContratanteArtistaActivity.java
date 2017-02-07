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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Contratante;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 07/02/17.
 */

public class ContratanteArtistaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;
    private Artista artista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_artista_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_artista_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_artista_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_artista_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("DESCUBRA");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");
        this.artista = intent.getParcelableExtra("paramsArtista");

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_artista_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_artista_nav_header_image_background);
        Button contratante_artista_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_artista_nav_header_button_configuracao);
        CircleImageView contratante_artista_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_artista_nav_header_image_perfil);
        TextView contratante_artista_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_artista_nav_header_textview_nome);

        contratante_artista_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        contratante_artista_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_artista_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_artista_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Configurar as views:
        ImageView contratante_artista_content_imageview = (ImageView) findViewById(R.id.contratante_artista_content_imageview);
        TextView contratante_artista_content_textview_nome_artista = (TextView) findViewById(R.id.contratante_artista_content_textview_nome_artista);
        TextView contratante_artista_content_textview_estilo_artista = (TextView) findViewById(R.id.contratante_artista_content_textview_estilo_artista);
        TextView contratante_artista_content_textview_site_artista = (TextView) findViewById(R.id.contratante_artista_content_textview_site_artista);
        TextView contratante_artista_content_textview_descricao_artista = (TextView) findViewById(R.id.contratante_artista_content_textview_descricao_artista);
        ListView contratante_artista_content_listview_equipamentos1 = (ListView) findViewById(R.id.contratante_artista_content_listview_equipamentos1);
        ListView contratante_artista_content_listview_equipamentos2 = (ListView) findViewById(R.id.contratante_artista_content_listview_equipamentos2);
        Button contratante_artista_content_button_contatar = (Button) findViewById(R.id.contratante_artista_content_button_contatar);

        contratante_artista_content_imageview.setImageResource(R.drawable.temp_evento1);
        contratante_artista_content_textview_nome_artista.setText(this.artista.getNome());
        contratante_artista_content_textview_estilo_artista.setText(this.artista.getEstilo());
        contratante_artista_content_textview_site_artista.setText(this.artista.getSite());
        contratante_artista_content_textview_descricao_artista.setText(this.artista.getDescricao());
        contratante_artista_content_button_contatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contatar();
            }
        });
        //--Fim de (2)
    }

    public void contatar(){}

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_artista_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contratante_artista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.contratante_artista_menu_action_favoritar){
            Toast.makeText(this, "Artista Favoritado", Toast.LENGTH_SHORT).show();
            return true;
        } if (id == R.id.contratante_artista_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_artista_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_artista_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_artista_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_artista_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_artista_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_artista_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_artista_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_artista_drawer_menu_nav_favoritos) {

        } else if (id == R.id.contratante_artista_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_artista_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}