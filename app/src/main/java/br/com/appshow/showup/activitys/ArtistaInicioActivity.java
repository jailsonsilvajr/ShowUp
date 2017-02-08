package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.appshow.showup.entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Evento;
import br.com.appshow.showup.fragments.FragmentoBuscasRecentes;
import br.com.appshow.showup.fragments.FragmentoIndicados;
import br.com.appshow.showup.fragments.FragmentoProximos;
import br.com.appshow.showup.repositorios.EventosBuscados;
import br.com.appshow.showup.repositorios.EventosIndicados;
import br.com.appshow.showup.repositorios.EventosProximos;

public class ArtistaInicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EventosIndicados eventosIndicados;
    private EventosProximos eventosProximos;
    private EventosBuscados eventosBuscados;
    private Artista artista;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.artista_inicio_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.artista_inicio_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_inicio_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.artista_inicio_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("INÍCIO");

        Intent intent = getIntent();
        this.user = intent.getParcelableExtra("paramsUsuario");
        this.artista = user.getArtista();

        //----------------------------------------------------------------------------//

        //--(1) Fazer consulta(as) ao banco de dados para popular eventosIndicados, eventosProximos, eventosBuscados e artista:

        this.eventosIndicados = new EventosIndicados(popularEventos());
        this.eventosProximos = new EventosProximos(popularEventos());
        this.eventosBuscados = new EventosBuscados(popularEventos());
        //this.artista = new Artista("Joey Tribbiani");
        //--Fim de (1)

        //--(2) Configurando a TwoWayView:
        TwoWayView artista_inicio_content_twoWayView = (TwoWayView) findViewById(R.id.artista_inicio_content_twoWayView);
        artista_inicio_content_twoWayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                open_activity_evento(i+1);//Para retirar o primeiro evento que já aparece como principal
            }
        });

        ArrayList<Evento> subArrayEventosProximos = new ArrayList<Evento>(); //Para retirar o primeiro evento que já aparece como principal
        for(int i = 1; i < this.eventosProximos.getArrayEvento().size(); i++){

            subArrayEventosProximos.add(this.eventosProximos.getArrayEvento().get(i));
        }

        AdapterTwoWayView adapterTwoWayView = new AdapterTwoWayView(this, subArrayEventosProximos);
        artista_inicio_content_twoWayView.setAdapter(adapterTwoWayView);
        //--Fim de (2)

        //--(3) Configurando o button seta:
        Button artista_inicio_content_button = (Button) findViewById(R.id.artista_inicio_content_button);
        artista_inicio_content_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                open_activity_evento(0);
            }
        });
        //--Fim de (3)

        //--(4) Configurando a TabLayout e a ViewPage:
        final TabLayout artista_inicio_content_tabLayout = (TabLayout) findViewById(R.id.artista_inicio_content_tabLayout);
        final ViewPager artista_inicio_content_viewpager = (ViewPager) findViewById(R.id.artista_inicio_content_viewpager);

        artista_inicio_content_tabLayout.addTab(artista_inicio_content_tabLayout.newTab().setText("INDICADOS"));
        artista_inicio_content_tabLayout.addTab(artista_inicio_content_tabLayout.newTab().setText("PRÓXIMOS"));
        artista_inicio_content_tabLayout.addTab(artista_inicio_content_tabLayout.newTab().setText("BUSCAS RECENTES"));

        artista_inicio_content_viewpager.setAdapter(new PagerAdapter(getSupportFragmentManager(), artista_inicio_content_tabLayout.getTabCount()));
        artista_inicio_content_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(artista_inicio_content_tabLayout));

        artista_inicio_content_tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                artista_inicio_content_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //--Fim de (4)

        //--(5) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView artista_inicio_nav_header_image_background = (ImageView) hView.findViewById(R.id.artista_inicio_nav_header_image_background);
        Button artista_inicio_nav_header_button_configuracao = (Button) hView.findViewById(R.id.artista_inicio_nav_header_button_configuracao);
        CircleImageView artista_inicio_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.artista_inicio_nav_header_image_perfil);
        TextView artista_inicio_nav_header_textview_nome = (TextView) hView.findViewById(R.id.artista_inicio_nav_header_textview_nome);

        artista_inicio_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        artista_inicio_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        artista_inicio_nav_header_textview_nome.setText(this.artista.getNome());
        artista_inicio_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (5)

        //--(6) Configurar evento principal/mais próximo:
        ImageView artista_inicio_content_imageview = (ImageView) findViewById(R.id.artista_inicio_content_imageview);
        TextView artista_inicio_content_textview_tempo = (TextView) findViewById(R.id.artista_inicio_content_textview_tempo);
        TextView artista_inicio_content_textview_nome = (TextView) findViewById(R.id.artista_inicio_content_textview_nome);

        artista_inicio_content_imageview.setImageResource(R.drawable.temp_evento1);
        artista_inicio_content_textview_tempo.setText(this.eventosProximos.getEventoByIndex(0).getTempoEvento());
        artista_inicio_content_textview_nome.setText(this.eventosProximos.getEventoByIndex(0).getNomeEvento());
        //--Fim de (6)
    }

    public ArrayList<Evento> getArrayListEvento(String str){

        if(str.equals("INDICADOS")) return this.eventosIndicados.getArrayEvento();
        if(str.equals("PROXIMOS")) return this.eventosProximos.getArrayEvento();
        if(str.equals("BUSCADOS")) return this.eventosBuscados.getArrayEvento();
        return null;
    }

    public void open_activity_evento(int position){

        Intent activity_evento = new Intent(this, ArtistaEventoActivity.class);
        activity_evento.putExtra("paramsArtista", this.artista);
        activity_evento.putExtra("paramsEvento", this.eventosProximos.getEventoByIndex(position));
        startActivity(activity_evento);
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ArtistaConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsArtista", this.artista);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_inicio_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.artista_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.artista_inicio_menu_action_buscar) {

            Intent activity_busca = new Intent(this, ArtistaBuscaActivity.class);
            activity_busca.putExtra("paramsArtista", this.artista);
            startActivity(activity_busca);
            return true;
        } if (id == R.id.artista_inicio_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.artista_inicio_drawer_menu_nav_agenda) {
            return true;
        } if (id == R.id.artista_inicio_drawer_menu_nav_promova) {
            return true;
        } if (id == R.id.artista_inicio_drawer_menu_nav_historico) {
            return true;
        } if (id == R.id.artista_inicio_drawer_menu_nav_sair) {
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

        if (id == R.id.artista_inicio_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.artista_inicio_drawer_menu_nav_agenda) {

        } else if (id == R.id.artista_inicio_drawer_menu_nav_promova) {

            Intent activity_promovase = new Intent(this, ArtistaPromovaSeActivity.class);
            activity_promovase.putExtra("paramsArtista", this.artista);
            startActivity(activity_promovase);
        } else if (id == R.id.artista_inicio_drawer_menu_nav_historico) {

        } else if (id == R.id.artista_inicio_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.artista_inicio_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class AdapterTwoWayView extends BaseAdapter{

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Evento> mDataSource;

        public AdapterTwoWayView(Context context, ArrayList<Evento> itens){

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
            View rowView = mInflater.inflate(R.layout.artista_inicio_list_item_twowayview, parent, false);

            TextView artista_inicio_list_item_twowayview_tempo = (TextView) rowView.findViewById(R.id.artista_inicio_list_item_twowayview_tempo);
            TextView artista_inicio_list_item_twowayview_nome = (TextView) rowView.findViewById(R.id.artista_inicio_list_item_twowayview_nome);
            ImageView artista_inicio_list_item_twowayview_image = (ImageView) rowView.findViewById(R.id.artista_inicio_list_item_twowayview_image);

            Evento evento = (Evento) getItem(position);
            artista_inicio_list_item_twowayview_tempo.setText(evento.getTempoEvento());
            artista_inicio_list_item_twowayview_nome.setText(evento.getNomeEvento());
            artista_inicio_list_item_twowayview_image.setImageResource(R.drawable.temp_evento2);

            /*Picasso.with(mContext) //Context
                .load("") //URL/FILE
                .into(proxEventoImageView);//an ImageView Object to show the loaded image;*/

            return rowView;
        }
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {

            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:{
                    FragmentoIndicados newFrame = new FragmentoIndicados();
                    //Bundle data = new Bundle();
                    //data.putParcelable("paramsArtista", artista);
                    //newFrame.setArguments(data);
                    newFrame.setArrayListEventos(getArrayListEvento("INDICADOS"));
                    newFrame.setArtista(artista);
                    return newFrame;
                }
                case 1:{
                    FragmentoProximos newFrame = new FragmentoProximos();
                    newFrame.setArrayListEventos(getArrayListEvento("PROXIMOS"));
                    newFrame.setArtista(artista);
                    return newFrame;
                }
                case 2:{
                    FragmentoBuscasRecentes newFrame = new FragmentoBuscasRecentes();
                    newFrame.setArrayListEventos(getArrayListEvento("BUSCADOS"));
                    newFrame.setArtista(artista);
                    return newFrame;
                }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return mNumOfTabs;
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

        evento1.setCod("1"); evento2.setCod("2"); evento3.setCod("3"); evento4.setCod("4"); evento5.setCod("5");
        evento6.setCod("6"); evento7.setCod("7"); evento8.setCod("8"); evento9.setCod("9");

        evento1.setNomeLocal("Cais do Alfândega");
        evento2.setNomeLocal("Preto Velho");
        evento3.setNomeLocal("Espaço Cultural Xinxim da Baiana");
        evento4.setNomeLocal("Roof Tebas");
        evento5.setNomeLocal("Preto Velho");
        evento6.setNomeLocal("Sétima Arte");
        evento7.setNomeLocal("Centro de Convenções");
        evento8.setNomeLocal("Centro de Convenções");
        evento9.setNomeLocal("Arena Liquidi Sky");

        evento1.setEnderecoLocal("Cais da Alfândega, 50030-100, Recife");
        evento2.setEnderecoLocal("Rua Bispo Coutinho, 681, Alto da Sé, Olinda");
        evento3.setEnderecoLocal("Avenida Sigismundo Gonçalves, 742, 53010-240, Olinda");
        evento4.setEnderecoLocal("Rua da Concórdia, 943 - São José, 50020-050, Recife");
        evento5.setEnderecoLocal("Rua do Farol, 218, Olinda - PE, 53120-390, Brasil");
        evento6.setEnderecoLocal("Rua Capitao Lima, 195, 50040-080, Recife");
        evento7.setEnderecoLocal("Olinda");
        evento8.setEnderecoLocal("Avenida Professor Andrade Bezerra, S/N - Salgadinho/Olinda-PE");
        evento8.setEnderecoLocal("R. Cataguáses, 2 - Guabiraba, 100000, Recife");

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

        evento1.setNomeEvento("Festival Rec-Beat 2017");
        evento2.setNomeEvento("Prévia - Esses Boy Tão Muito Doido");
        evento3.setNomeEvento("Doce Libido");
        evento4.setNomeEvento("Bregalize");
        evento5.setNomeEvento("Esses Boy Tão Muito Doido");
        evento6.setNomeEvento("Freaks! Carnaval Edition");
        evento7.setNomeEvento("Olinda Beer");
        evento8.setNomeEvento("Enquanto isso na Sala da Justiça");
        evento9.setNomeEvento("Liquid Sky");

        evento1.setData("25/02");
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

        evento1.setTempoEvento("EM 9H");
        evento2.setTempoEvento("EM 3H");
        evento3.setTempoEvento("EM 1H");
        evento4.setTempoEvento("EM 2H");
        evento5.setTempoEvento("EM 8H");
        evento6.setTempoEvento("EM 5H");
        evento7.setTempoEvento("EM 7H");
        evento8.setTempoEvento("EM 4H");
        evento9.setTempoEvento("EM 6H");

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
