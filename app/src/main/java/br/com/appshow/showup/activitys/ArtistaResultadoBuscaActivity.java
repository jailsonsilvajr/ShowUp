package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Evento;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 04/02/17.
 */

public class ArtistaResultadoBuscaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Artista artista;
    private ArrayList<Evento> eventosEncontrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.artista_resultado_busca_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.artista_resultado_busca_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_resultado_busca_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.artista_resultado_busca_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("DESCUBRA");

        Intent intent = getIntent();
        this.artista = intent.getParcelableExtra("paramsArtista");

        //----------------------------------------------------------------------------//

        //--(1) Configurando listview de eventos:
        ListView artista_resultado_busca_content_listview_evento = (ListView) findViewById(R.id.artista_resultado_busca_content_listview_evento);
        artista_resultado_busca_content_listview_evento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                open_activity_evento(i);
            }
        });

        this.eventosEncontrados = popularEventos();//Metodo TEMPORÁRIO!!!
        AdapterList adapterList = new AdapterList(this, this.eventosEncontrados);
        artista_resultado_busca_content_listview_evento.setAdapter(adapterList);
        //--Fim de (1)

        //--(2) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView artista_resultado_busca_nav_header_image_background = (ImageView) hView.findViewById(R.id.artista_resultado_busca_nav_header_image_background);
        Button artista_resultado_busca_nav_header_button_configuracao = (Button) hView.findViewById(R.id.artista_resultado_busca_nav_header_button_configuracao);
        CircleImageView artista_resultado_busca_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.artista_resultado_busca_nav_header_image_perfil);
        TextView artista_resultado_busca_nav_header_textview_nome = (TextView) hView.findViewById(R.id.artista_resultado_busca_nav_header_textview_nome);

        artista_resultado_busca_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        artista_resultado_busca_nav_header_image_perfil.setImageResource(R.drawable.foto_perfil);
        artista_resultado_busca_nav_header_textview_nome.setText(this.artista.getNome());
        artista_resultado_busca_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (2)
    }

    public void open_activity_evento(int position){

        Intent activity_evento = new Intent(this, ArtistaEventoActivity.class);
        activity_evento.putExtra("paramsArtista", this.artista);
        activity_evento.putExtra("paramsEvento", this.eventosEncontrados.get(position));
        startActivity(activity_evento);
    }

    public void open_activity_configuracao(){

        Intent activity_configura = new Intent(this, ArtistaConfiguracoesActivity.class);
        activity_configura.putExtra("paramsArtista", this.artista);
        startActivity(activity_configura);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_resultado_busca_drawer_layout);
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
        if (id == R.id.artista_resultado_busca_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.artista_resultado_busca_drawer_menu_nav_agenda) {
            return true;
        } if (id == R.id.artista_resultado_busca_drawer_menu_nav_promova) {
            return true;
        } if (id == R.id.artista_resultado_busca_drawer_menu_nav_historico) {
            return true;
        } if (id == R.id.artista_resultado_busca_drawer_menu_nav_sair) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.artista_resultado_busca_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.artista_resultado_busca_drawer_menu_nav_agenda) {

        } else if (id == R.id.artista_resultado_busca_drawer_menu_nav_promova) {

            Intent activity_promovase = new Intent(this, ArtistaPromovaSeActivity.class);
            activity_promovase.putExtra("paramsArtista", this.artista);
            startActivity(activity_promovase);
        } else if (id == R.id.artista_resultado_busca_drawer_menu_nav_historico) {

        } else if (id == R.id.artista_resultado_busca_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_resultado_busca_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class AdapterList extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Evento> mDataSource;

        public AdapterList(Context context, ArrayList<Evento> itens){

            mContext = context;
            mDataSource = itens;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get view for row item
            View rowView = mInflater.inflate(R.layout.artista_resultado_busca_list_item_listview, parent, false);

            TextView artista_resultado_busca_list_item_textview_nome_evento = (TextView) rowView.findViewById(R.id.artista_resultado_busca_list_item_textview_nome_evento);
            TextView artista_resultado_busca_list_item_textview_local_evento = (TextView) rowView.findViewById(R.id.artista_resultado_busca_list_item_textview_local_evento);
            ImageView artista_resultado_busca_list_item_image_evento = (ImageView) rowView.findViewById(R.id.artista_resultado_busca_list_item_image_evento);
            ImageView artista_resultado_busca_list_item_button_evento = (ImageView) rowView.findViewById(R.id.artista_resultado_busca_list_item_button_evento);

            Evento evento = (Evento) getItem(position);
            artista_resultado_busca_list_item_textview_nome_evento.setText(evento.getNome());
            artista_resultado_busca_list_item_textview_local_evento.setText(evento.getLocal());
            artista_resultado_busca_list_item_image_evento.setImageResource(R.drawable.temp_evento3);
            artista_resultado_busca_list_item_button_evento.setImageResource(R.drawable.seta_menor);

        /*Picasso.with(mContext) //Context
                .load("") //URL/FILE
                .into(proxEventoImageView);//an ImageView Object to show the loaded image;*/

            return rowView;
        }
    }

    public ArrayList<Evento> popularEventos(){

        ArrayList<Evento> eventos = new ArrayList<Evento>();

        Evento evento1 = new Evento();
        Evento evento2 = new Evento();
        Evento evento3 = new Evento();
        Evento evento4 = new Evento();
        Evento evento5 = new Evento();
        Evento evento6 = new Evento();
        Evento evento7 = new Evento();
        Evento evento8 = new Evento();
        Evento evento9 = new Evento();

        evento1.setId_evento("1"); evento2.setId_evento("2"); evento3.setId_evento("3"); evento4.setId_evento("4"); evento5.setId_evento("5");
        evento6.setId_evento("6"); evento7.setId_evento("7"); evento8.setId_evento("8"); evento9.setId_evento("9");

        evento1.setLocal("Cais do Alfândega");
        evento2.setLocal("Preto Velho");
        evento3.setLocal("Espaço Cultural Xinxim da Baiana");
        evento4.setLocal("Roof Tebas");
        evento5.setLocal("Preto Velho");
        evento6.setLocal("Sétima Arte");
        evento7.setLocal("Centro de Convenções");
        evento8.setLocal("Centro de Convenções");
        evento9.setLocal("Arena Liquidi Sky");

        /*evento1.setEndereco("Cais da Alfândega, 50030-100, Recife");
        evento2.setEndereco("Rua Bispo Coutinho, 681, Alto da Sé, Olinda");
        evento3.setEndereco("Avenida Sigismundo Gonçalves, 742, 53010-240, Olinda");
        evento4.setEndereco("Rua da Concórdia, 943 - São José, 50020-050, Recife");
        evento5.setEndereco("Rua do Farol, 218, Olinda - PE, 53120-390, Brasil");
        evento6.setEndereco("Rua Capitao Lima, 195, 50040-080, Recife");
        evento7.setEndereco("Olinda");
        evento8.setEndereco("Avenida Professor Andrade Bezerra, S/N - Salgadinho/Olinda-PE");
        evento8.setEndereco("R. Cataguáses, 2 - Guabiraba, 100000, Recife");*/

        evento1.setDescricao("A 22ª edição do Festival Rec-Beat, vai acontecer no Cais da Alfândega, entre 25 e 28 de fevereiro, durante o carnaval.");
        evento2.setDescricao("Enquanto o dia 25 de fevereiro não chega, já estamos ansiosos e preparando nossa tradicional Prévia, a concentração marca o início das comemorações dos 10 anos do bloco.");
        evento3.setDescricao("O carnaval tá na porta, batendo, tocando a campanhia, já fantasiado, pronto para os braços de Momo. Pra celecrar essa chegada a Doce Libido de fevereiro vem se manifestar no salão! Não gressive não!!! Tá chegando a hora de envernizar a noite toda e sair da pista brilhando, joiada, linda pra cair no carnaval.");
        evento4.setDescricao("ESSE BREGA TÁ MASSA, TÁ PEGANDO FOGO...\n" +
                "É nele que eu vou desandar de novo!\n" +
                "\n" +
                "A bregalize já deu onda... e nesse passo envolvente pra ninguém ficar parado, a gente volta num clima brega astral de carnaval!");
        evento5.setDescricao("Temos o prazer de convidar todos os amigos, familiares, foliões e o mundo todo para curtir nossos 10 anos de carnaval.\n" +
                "É felicidade demais!");
        evento6.setDescricao("E quem disse que em Recife não tem pré carnaval bebean? Em fevereiro vai rolar a FREAKS reunindo um time de Djs de peso e um publico close nesse carnaval !! Vai rolar muito pop , indie\\ rock, black music , funk e o melhor VAI SER OPEN BAR !!!");
        evento7.setDescricao("A maior prévia de carnaval do Brasil, já esta com sua nova edição, Olinda Beer 2017, confirmada. O evento irá ocorrer no centro de convenções de Pernambuco no dia 19 de fevereiro.");
        evento8.setDescricao("Uma das prévias mais tradicionais e irreverentes do carnaval, vem este ano com nova data (10 de fevereiro).");
        evento9.setDescricao("Esqueça tudo o que você sabe sobre carnaval: A Liquid Sky vai te levar a um novo conceito!");

        evento1.setNome("Festival Rec-Beat 2017");
        evento2.setNome("Prévia - Esses Boy Tão Muito Doido");
        evento3.setNome("Doce Libido");
        evento4.setNome("Bregalize");
        evento5.setNome("Esses Boy Tão Muito Doido");
        evento6.setNome("Freaks! Carnaval Edition");
        evento7.setNome("Olinda Beer");
        evento8.setNome("Enquanto isso na Sala da Justiça");
        evento9.setNome("Liquid Sky");

        /*evento1.setData("25/02");
        evento2.setData("04/02");
        evento3.setData("03/02");
        evento4.setData("03/02");
        evento5.setData("25/02");
        evento6.setData("11/02");
        evento7.setData("19/02");
        evento8.setData("10/02");
        evento9.setData("18/02");

        evento1.setHorario("19:30H - 01:30");
        evento2.setHorario("10:00H - 16:20H");
        evento3.setHorario("22:00H - 05:00H");
        evento4.setHorario("22:00H - 06:00H");
        evento5.setHorario("09:00H - 15:00H");
        evento6.setHorario("23:00H - 05:00H");
        evento7.setHorario("09:00H - 22:00H");
        evento8.setHorario("22:00H - 05:00H");
        evento9.setHorario("22:00H - 16:00H");

        evento1.setTempo("EM 9H");
        evento2.setTempo("EM 3H");
        evento3.setTempo("EM 1H");
        evento4.setTempo("EM 2H");
        evento5.setTempo("EM 8H");
        evento6.setTempo("EM 5H");
        evento7.setTempo("EM 7H");
        evento8.setTempo("EM 4H");
        evento9.setTempo("EM 6H");*/

        evento1.setRequisito("Requisitos...\n"+"Requisitos...");
        evento2.setRequisito("Requisitos...\n"+"Requisitos...");
        evento3.setRequisito("Requisitos...\n"+"Requisitos...");
        evento4.setRequisito("Requisitos...\n"+"Requisitos...");
        evento5.setRequisito("Requisitos...\n"+"Requisitos...");
        evento6.setRequisito("Requisitos...\n"+"Requisitos...");
        evento7.setRequisito("Requisitos...\n"+"Requisitos...");
        evento8.setRequisito("Requisitos...\n"+"Requisitos...");
        evento9.setRequisito("Requisitos...\n"+"Requisitos...");

        eventos.add(evento3); eventos.add(evento4); eventos.add(evento2); eventos.add(evento8); eventos.add(evento6);
        eventos.add(evento9); eventos.add(evento7); eventos.add(evento5); eventos.add(evento1);

        return eventos;
    }
}
