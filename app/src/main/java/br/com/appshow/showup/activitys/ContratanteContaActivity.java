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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Contratante;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 07/02/17.
 */

public class ContratanteContaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_conta_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_conta_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_conta_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_conta_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("CONTA");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_conta_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_conta_nav_header_image_background);
        Button contratante_conta_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_conta_nav_header_button_configuracao);
        CircleImageView contratante_conta_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_conta_nav_header_image_perfil);
        TextView contratante_conta_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_conta_nav_header_textview_nome);

        contratante_conta_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        if(contratante.getUrl_foto_perfil().equals("")){

            contratante_conta_nav_header_image_perfil.setImageResource(R.drawable.foto_perfil);
        }else{

            Picasso.with(this)
                    .load(contratante.getUrl_foto_perfil())
                    .into(contratante_conta_nav_header_image_perfil);
        }
        if(contratante.getNome().equals("")){

            contratante_conta_nav_header_textview_nome.setText("Nome");
        }else{

            contratante_conta_nav_header_textview_nome.setText(this.contratante.getNome());
        }
        contratante_conta_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        contratante_conta_nav_header_image_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_alterar_perfil();
            }
        });
        //--Fim de (1)

        //--(2) Configurar button alterar pagamento:
        Button contratante_conta_content_button_alterar_dados = (Button) findViewById(R.id.contratante_conta_content_button_alterar_dados);
        contratante_conta_content_button_alterar_dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_alterar_pagamento();
            }
        });
        //--Fim de (2)

        //--(3) Configurar button renovar assinatura:
        Button contratante_conta_content_button_renovar_assinatura = (Button) findViewById(R.id.contratante_conta_content_button_renovar_assinatura);
        contratante_conta_content_button_renovar_assinatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_renovar_assinatura();
            }
        });
        //--Fim de (3)

        //--(4) Configurar button cancelar assinatura:
        Button contratante_conta_content_button_cancelar_assinatura = (Button) findViewById(R.id.contratante_conta_content_button_cancelar_assinatura);
        contratante_conta_content_button_cancelar_assinatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_cancelar_assinatura();
            }
        });
        //--Fim de (3)
    }

    public void open_activity_alterar_perfil(){

        Intent activity_alterar_perfil = new Intent(this, ContratanteAlterarPerfilActivity.class);
        activity_alterar_perfil.putExtra("paramsContratante", this.contratante);
        startActivity(activity_alterar_perfil);
    }

    public void open_activity_renovar_assinatura(){

        Toast.makeText(this, "Assinatura Renovada", Toast.LENGTH_SHORT).show();
    }

    public void open_activity_cancelar_assinatura(){

        Toast.makeText(this, "Assinatura Cancelada", Toast.LENGTH_SHORT).show();
    }

    public void open_activity_alterar_pagamento(){

        Intent activity_alterar_pagamento = new Intent(this, ContratanteAlterarPagamentoActivity.class);
        activity_alterar_pagamento.putExtra("paramsContratante", this.contratante);
        startActivity(activity_alterar_pagamento);
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_conta_drawer_layout);
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
        if (id == R.id.contratante_conta_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_conta_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_conta_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_conta_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_conta_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_conta_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_conta_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_conta_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_conta_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);

        } else if (id == R.id.contratante_conta_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_conta_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
