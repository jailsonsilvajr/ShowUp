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

import br.com.appshow.showup.entidades.EnderecoEvento;
import br.com.appshow.showup.entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

import org.lucasr.twowayview.TwoWayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    String url = "";
    String parametros = "";

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
        this.artista = intent.getParcelableExtra("paramsArtista");

        //----------------------------------------------------------------------------//

        //--(1) Fazer consulta(as) ao banco de dados para popular eventosIndicados, eventosProximos, eventosBuscados e artista:
        /*ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            url = "http://192.241.244.47/showup/obter_evento_app_artista.php?";
            parametros = "id_contratante=" + this.artista.getCod();
            new SolicitarDados().execute(url);
        }else{

            Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
        }*/
        this.eventosIndicados = new EventosIndicados(popularEventos());
        this.eventosProximos = new EventosProximos(popularEventos());
        this.eventosBuscados = new EventosBuscados(popularEventos());
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
        //--Fim de (2)*/

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
        artista_inicio_nav_header_image_perfil.setImageResource(R.drawable.foto_perfil);
        artista_inicio_nav_header_textview_nome.setText(this.artista.getNome());
        artista_inicio_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (5)

        //--(6) Configurar evento principal/mais próximo:
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date data_atual = calendar.getTime();

        //String data = dateFormat.format(data_atual);
        //String hora = horaFormat.format(data_atual);

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int ano = calendar.get(Calendar.YEAR);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        Evento evento = this.eventosProximos.getEventoByIndex(0);
        int dia_evento = Integer.parseInt(evento.getData().substring(8, 10));
        int mes_evento = Integer.parseInt(evento.getData().substring(5, 7));
        int ano_evento = Integer.parseInt(evento.getData().substring(0, 4));
        int hora_evento = Integer.parseInt(evento.getHorario_inicio().substring(0, 2));
        int min_evento = Integer.parseInt(evento.getHorario_inicio().substring(3, 5));

        String tempo;
        if(dia == dia_evento){

            tempo = "EM " + (hora_evento - hora) + "H";
        }else if(mes_evento == mes){

            tempo = "EM " + (dia_evento - dia) + "D";
        }else if(ano_evento == ano){

            tempo = "EM " + (mes_evento - mes) + "M";
        }else{

            mes_evento = mes_evento + 12;
            tempo = "EM " + (mes - mes_evento) + "M";
        }
        ImageView artista_inicio_content_imageview = (ImageView) findViewById(R.id.artista_inicio_content_imageview);
        TextView artista_inicio_content_textview_tempo = (TextView) findViewById(R.id.artista_inicio_content_textview_tempo);
        TextView artista_inicio_content_textview_nome = (TextView) findViewById(R.id.artista_inicio_content_textview_nome);

        artista_inicio_content_imageview.setImageResource(R.drawable.temp_evento1);
        artista_inicio_content_textview_tempo.setText(tempo);
        artista_inicio_content_textview_nome.setText(this.eventosProximos.getEventoByIndex(0).getNome());
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Date data_atual = calendar.getTime();

            //String data = dateFormat.format(data_atual);
            //String hora = horaFormat.format(data_atual);

            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH) + 1;
            int ano = calendar.get(Calendar.YEAR);
            int hora = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);

            int dia_evento = Integer.parseInt(evento.getData().substring(8, 10));
            int mes_evento = Integer.parseInt(evento.getData().substring(5, 7));
            int ano_evento = Integer.parseInt(evento.getData().substring(0, 4));
            int hora_evento = Integer.parseInt(evento.getHorario_inicio().substring(0, 2));
            int min_evento = Integer.parseInt(evento.getHorario_inicio().substring(3, 5));

            String tempo;
            if(dia == dia_evento){

                tempo = "EM " + (hora_evento - hora) + "H";
            }else if(mes_evento == mes){

                tempo = "EM " + (dia_evento - dia) + "D";
            }else if(ano_evento == ano){

                tempo = "EM " + (mes_evento - mes) + "M";
            }else{

                mes_evento = mes_evento + 12;
                tempo = "EM " + (mes - mes_evento) + "M";
            }

            artista_inicio_list_item_twowayview_tempo.setText(tempo);
            artista_inicio_list_item_twowayview_nome.setText(evento.getNome());
            artista_inicio_list_item_twowayview_image.setImageResource(R.drawable.temp_evento1);

            /*Picasso.with(mContext) //Context
                .load(evento.getUrl_imagem()) //URL/FILE
                .into(artista_inicio_list_item_twowayview_image);//an ImageView Object to show the loaded image;*/

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

    /*private class SolicitarDados extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            StringTokenizer str = new StringTokenizer(result);
            ArrayList<Evento> eventos = new ArrayList<Evento>();
            while(str.hasMoreTokens()){

                Evento evento = new Evento();
                evento.setCod(str.nextToken());
                evento.setNomeEvento(str.nextToken());
                evento.setTempoEvento(str.nextToken());
                evento.setNomeLocal(str.nextToken());
                evento.setEnderecoLocal(str.nextToken());
                evento.setDescricao(str.nextToken());
                evento.setData(str.nextToken());
                evento.setHorario(str.nextToken());
                evento.setRequisito(str.nextToken());
                evento.setUrl_imagem(str.nextToken());
                eventos.add(evento);
            }
            eventosIndicados = new EventosIndicados(eventos);
            eventosProximos = new EventosProximos(eventos);
            eventosBuscados = new EventosBuscados(eventos);

            TwoWayView artista_inicio_content_twoWayView = (TwoWayView) findViewById(R.id.artista_inicio_content_twoWayView);
            artista_inicio_content_twoWayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    open_activity_evento(i+1);//Para retirar o primeiro evento que já aparece como principal
                }
            });

            ArrayList<Evento> subArrayEventosProximos = new ArrayList<Evento>(); //Para retirar o primeiro evento que já aparece como principal
            for(int i = 1; i < eventosProximos.getArrayEvento().size(); i++){

                subArrayEventosProximos.add(eventosProximos.getArrayEvento().get(i));
            }

            AdapterTwoWayView adapterTwoWayView = new AdapterTwoWayView(ArtistaInicioActivity.this, subArrayEventosProximos);
            artista_inicio_content_twoWayView.setAdapter(adapterTwoWayView);

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

            ImageView artista_inicio_content_imageview = (ImageView) findViewById(R.id.artista_inicio_content_imageview);
            TextView artista_inicio_content_textview_tempo = (TextView) findViewById(R.id.artista_inicio_content_textview_tempo);
            TextView artista_inicio_content_textview_nome = (TextView) findViewById(R.id.artista_inicio_content_textview_nome);

            //artista_inicio_content_imageview.setImageResource(R.drawable.temp_evento1);
            artista_inicio_content_textview_tempo.setText(eventosProximos.getEventoByIndex(0).getTempoEvento());
            artista_inicio_content_textview_nome.setText(eventosProximos.getEventoByIndex(0).getNomeEvento());
            Picasso.with(ArtistaInicioActivity.this) //Context
                    .load(eventosProximos.getEventoByIndex(0).getUrl_imagem()) //URL/FILE
                    .into(artista_inicio_content_imageview);
        }
    }*/

    public ArrayList<Evento> popularEventos(){

        ArrayList<Evento> eventos = new ArrayList<Evento>();

        Evento evento = new Evento();

        evento.setId_evento("1");
        evento.setNome("NomeDoEvento");
        evento.setLocal("NomeDoLocal");
        evento.setDescricao("DescricaoDoEvento");
        evento.setData("2017/04/01");
        evento.setHorario_inicio("19:00");
        evento.setHorario_fim("23:59");
        evento.setEquipamentos("EquipamentosDoEvento");
        evento.setRequisito("RequisitosDoEvento");
        evento.setUrl_imagem1(null);
        evento.setUrl_imagem2(null);
        evento.setUrl_imagem3(null);

        EnderecoEvento enderecoEvento = new EnderecoEvento();
        enderecoEvento.setCep("54515-580");
        enderecoEvento.setId_endereco(1);
        enderecoEvento.setPais("Brasil");
        enderecoEvento.setEstado("Pernambuco");
        enderecoEvento.setCidade("Recife");
        enderecoEvento.setCep("Ipsep");
        enderecoEvento.setRua("Rua 04");
        enderecoEvento.setNumero(31);
        enderecoEvento.setComplemento("Perto da Padaria");

        evento.setEndereco_evento(enderecoEvento);

        for(int i = 0; i < 10; i++){

            eventos.add(evento);
        }

        return eventos;
    }
}
