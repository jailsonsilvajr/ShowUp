package br.com.appshow.showup.activitys;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 03/02/17.
 */

public class ArtistaBuscaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Artista artista;
    private List<String> cidade;
    private List<String> faixa_pag;
    private String cidade_buscada;
    private String faixa_pagamento;
    private String data_buscada_dia;
    private String data_buscada_mes;
    private String data_buscada_ano;
    private Calendar data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.artista_busca_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.artista_busca_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_busca_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.artista_busca_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("DESCUBRA");

        Intent intent = getIntent();
        this.artista = intent.getParcelableExtra("paramsArtista");

        this.data = Calendar.getInstance();
        getCidades();
        getPagamentos();

        //---------------------------------------------------------------------//

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView artista_busca_nav_header_image_background = (ImageView) hView.findViewById(R.id.artista_busca_nav_header_image_background);
        Button artista_busca_nav_header_button_configuracao = (Button) hView.findViewById(R.id.artista_busca_nav_header_button_configuracao);
        CircleImageView artista_busca_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.artista_busca_nav_header_image_perfil);
        TextView artista_busca_nav_header_textview_nome = (TextView) hView.findViewById(R.id.artista_busca_nav_header_textview_nome);

        artista_busca_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        artista_busca_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        artista_busca_nav_header_textview_nome.setText(this.artista.getNome());
        artista_busca_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Configurando os Spinner:
        Spinner artista_busca_content_spinner_cidade = (Spinner) findViewById(R.id.artista_busca_content_spinner_cidade);
        ArrayAdapter<String> adapter_cidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cidade);
        adapter_cidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        artista_busca_content_spinner_cidade.setAdapter(adapter_cidade);
        artista_busca_content_spinner_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                setCidadeBuscada(getCidadeList(position));
            }

            public void onNothingSelected(AdapterView<?> parent){}
        });

        Spinner artista_busca_content_spinner_pagamento = (Spinner) findViewById(R.id.artista_busca_content_spinner_pagamento);
        ArrayAdapter<String> adapter_pagamentos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, faixa_pag);
        adapter_pagamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        artista_busca_content_spinner_pagamento.setAdapter(adapter_pagamentos);
        artista_busca_content_spinner_pagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                setFaixaPagamento(getFaixaPagamento(position));
            }

            public void onNothingSelected(AdapterView<?> parent){}
        });
        //--Fim de (2)

        //--(3) Configurando o DatePicker:
        int year = data.get(data.YEAR);
        int month = data.get(data.MONTH);
        int dayOfMonth = data.get(data.DAY_OF_MONTH);

        setDataBuscada(dayOfMonth+"", month+1+"", year+"");

        DatePicker dp = (DatePicker) findViewById(R.id.artista_busca_content_datepicker);

        dp.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                setDataBuscada(dayOfMonth+"", monthOfYear+1+"", year+"");
            }
        });
        //--Fim de (3)

        //--(4) Configurando o button Buscar:
        Button artista_busca_content_button = (Button) findViewById(R.id.artista_busca_content_button);
        artista_busca_content_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                open_activity_resultado_busca();
            }
        });
        //--Fim de (4)

    }

    public void open_activity_resultado_busca(){

        Intent activity_resultado_busca = new Intent(this, ArtistaResultadoBuscaActivity.class);
        activity_resultado_busca.putExtra("paramsArtista", this.artista);
        startActivity(activity_resultado_busca);
    }

    public void open_activity_configuracao(){

        Intent activity_configura = new Intent(this, ArtistaConfiguracoesActivity.class);
        activity_configura.putExtra("paramsArtista", this.artista);
        startActivity(activity_configura);
    }

    public void setCidadeBuscada(String str){

        this.cidade_buscada = str;
    }

    public String getCidadeList(int position){

        return this.cidade.get(position);
    }

    public void setFaixaPagamento(String str){

        this.faixa_pagamento = str;
    }

    public String getFaixaPagamento(int position){

        return this.faixa_pag.get(position);
    }

    public void setDataBuscada(String dia, String mes, String ano){

        this.data_buscada_dia = dia;
        this.data_buscada_mes = mes;
        this.data_buscada_ano = ano;
    }

    public void getCidades(){

        this.cidade = new ArrayList<String>();
        cidade.add("Recife");
        cidade.add("Olinda");
        cidade.add("Jaboatão");
        cidade.add("Cabo");
        cidade.add("Camaragibe");
        cidade.add("Ipojuca");
        cidade.add("Paulista");
    }

    public void getPagamentos(){

        this.faixa_pag = new ArrayList<String>();
        faixa_pag.add("R$ 100,00 - R$ 199,99");
        faixa_pag.add("R$ 200,00 - R$ 299,99");
        faixa_pag.add("R$ 300,00 - R$ 399,99");
        faixa_pag.add("R$ 400,00 - R$ 499,99");
        faixa_pag.add("R$ 500,00 - R$ 599,99");
        faixa_pag.add("R$ 600,00 - R$ 699,99");
        faixa_pag.add("R$ 700,00 - R$ 799,99");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_busca_drawer_layout);
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
        if (id == R.id.artista_busca_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.artista_busca_drawer_menu_nav_agenda) {
            return true;
        } if (id == R.id.artista_busca_drawer_menu_nav_promova) {
            return true;
        } if (id == R.id.artista_busca_drawer_menu_nav_historico) {
            return true;
        } if (id == R.id.artista_busca_drawer_menu_nav_sair) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.artista_busca_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.artista_busca_drawer_menu_nav_agenda) {

        } else if (id == R.id.artista_busca_drawer_menu_nav_promova) {

            Intent activity_promovase = new Intent(this, ArtistaPromovaSeActivity.class);
            activity_promovase.putExtra("paramsArtista", this.artista);
            startActivity(activity_promovase);
        } else if (id == R.id.artista_busca_drawer_menu_nav_historico) {

        } else if (id == R.id.artista_busca_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_busca_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
