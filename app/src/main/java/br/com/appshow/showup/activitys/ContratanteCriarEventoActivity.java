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

import java.util.Calendar;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Contratante;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 07/02/17.
 */

public class ContratanteCriarEventoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;
    private String nome_evento;
    private String nome_local_evento;
    private String endereco_evento;
    private String data_evento;
    private String inicio_evento;
    private String termino_evento;
    private String descricao_evento;
    private String equipamentos_evento;
    private String procura_evento;

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
        contratante_criar_evento_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_criar_evento_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_criar_evento_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Configurando views:
        final EditText contratante_criar_evento_edittext_nome_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_nome_evento);
        final EditText contratante_criar_evento_edittext_local_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_local_evento);
        final EditText contratante_criar_evento_edittext_endereco_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_endereco_evento);
        final EditText contratante_criar_evento_edittext_data_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_data_evento);
        final EditText contratante_criar_evento_edittext_inicio_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_inicio_evento);
        final EditText contratante_criar_evento_edittext_termino_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_termino_evento);
        final EditText contratante_criar_evento_edittext_descricao_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_descricao_evento);
        final EditText contratante_criar_evento_edittext_equipamentos_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_equipamentos_evento);
        final EditText contratante_criar_evento_edittext_procura_evento = (EditText) findViewById(R.id.contratante_criar_evento_edittext_procura_evento);
        Button contratante_criar_evento_button_criar = (Button) findViewById(R.id.contratante_criar_evento_button_criar);

        contratante_criar_evento_button_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nome_evento = contratante_criar_evento_edittext_nome_evento.getText().toString();
                nome_local_evento = contratante_criar_evento_edittext_local_evento.getText().toString();
                endereco_evento = contratante_criar_evento_edittext_endereco_evento.getText().toString();
                data_evento = contratante_criar_evento_edittext_data_evento.getText().toString();
                inicio_evento = contratante_criar_evento_edittext_inicio_evento.getText().toString();
                termino_evento = contratante_criar_evento_edittext_termino_evento.getText().toString();
                descricao_evento = contratante_criar_evento_edittext_descricao_evento.getText().toString();
                equipamentos_evento = contratante_criar_evento_edittext_equipamentos_evento.getText().toString();
                procura_evento = contratante_criar_evento_edittext_procura_evento.getText().toString();

                showToast();//temporário
            }
        });
        //--Fim de (2)
    }

    public void showToast(){ //temporário

        Toast.makeText(this, nome_evento+"\n"+nome_local_evento+"\n"+endereco_evento+"\n"+
                data_evento+"\n"+inicio_evento+"\n"+termino_evento+"\n"+descricao_evento+"\n"+
                equipamentos_evento+"\n"+procura_evento, Toast.LENGTH_LONG).show();
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

        } else if (id == R.id.contratante_criar_evento_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_criar_evento_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
