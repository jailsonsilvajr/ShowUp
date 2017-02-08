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
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.entidades.Evento;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 08/02/17.
 */

public class ContratanteEventoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_evento_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_evento_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_evento_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_evento_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("EVENTO");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");
        this.evento = intent.getParcelableExtra("paramsEvento");

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_evento_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_evento_nav_header_image_background);
        Button contratante_evento_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_evento_nav_header_button_configuracao);
        CircleImageView contratante_evento_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_evento_nav_header_image_perfil);
        TextView contratante_evento_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_evento_nav_header_textview_nome);

        contratante_evento_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        contratante_evento_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_evento_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_evento_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Configurar as view:
        TextView contratante_evento_content_textview_nome_local = (TextView) findViewById(R.id.contratante_evento_content_textview_nome_local);
        TextView contratante_evento_content_textview_endereco_local = (TextView) findViewById(R.id.contratante_evento_content_textview_endereco_local);
        TextView contratante_evento_content_textview_descricao_evento = (TextView) findViewById(R.id.contratante_evento_content_textview_descricao_evento);
        TextView contratante_evento_content_nome_evento = (TextView) findViewById(R.id.contratante_evento_content_nome_evento);
        TextView contratante_evento_content_data_evento = (TextView) findViewById(R.id.contratante_evento_content_data_evento);
        TextView contratante_evento_content_hora_evento = (TextView) findViewById(R.id.contratante_evento_content_hora_evento);
        TextView contratante_evento_content_requisitos_evento = (TextView) findViewById(R.id.contratante_evento_content_requisitos_evento);

        contratante_evento_content_textview_nome_local.setText(this.evento.getNomeLocal());
        contratante_evento_content_textview_endereco_local.setText(this.evento.getEnderecoLocal());
        contratante_evento_content_textview_descricao_evento.setText(this.evento.getDescricao());
        contratante_evento_content_nome_evento.setText(this.evento.getNomeEvento());
        contratante_evento_content_data_evento.setText(this.evento.getData());
        contratante_evento_content_hora_evento.setText(this.evento.getHorario());
        contratante_evento_content_requisitos_evento.setText(this.evento.getRequisito());
        //--Fim de (2)
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_evento_drawer_layout);
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
        if (id == R.id.contratante_evento_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_evento_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_evento_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_evento_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_evento_drawer_menu_nav_sair) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contratante_evento_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_evento_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_evento_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_evento_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);
        } else if (id == R.id.contratante_evento_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_evento_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
