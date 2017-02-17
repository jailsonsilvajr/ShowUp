package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.appshow.showup.conexao.Conectar;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

import org.lucasr.twowayview.TwoWayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

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
        this.user = intent.getParcelableExtra("paramsUsuario");
        this.artista = user.getArtista();

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
        int dia_evento = Integer.parseInt(evento.getDia());
        int mes_evento = Integer.parseInt(evento.getMes());
        int ano_evento = Integer.parseInt(evento.getAno());
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

            int dia_evento = Integer.parseInt(evento.getDia());
            int mes_evento = Integer.parseInt(evento.getMes());
            int ano_evento = Integer.parseInt(evento.getAno());
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

        evento1.setNome("Cais do Alfândega");
        evento2.setNome("Preto Velho");
        evento3.setNome("Espaço Cultural Xinxim da Baiana");
        evento4.setNome("Roof Tebas");
        evento5.setNome("Preto Velho");
        evento6.setNome("Sétima Arte");
        evento7.setNome("Centro de Convenções");
        evento8.setNome("Centro de Convenções");
        evento9.setNome("Arena Liquidi Sky");

        evento1.setEstado("Pernambuco");
        evento2.setEstado("Pernambuco");
        evento3.setEstado("Pernambuco");
        evento4.setEstado("Pernambuco");
        evento5.setEstado("Pernambuco");
        evento6.setEstado("Pernambuco");
        evento7.setEstado("Pernambuco");
        evento8.setEstado("Pernambuco");
        evento9.setEstado("Pernambuco");

        evento1.setCidade("Recife");
        evento2.setCidade("Olinda");
        evento3.setCidade("Olinda");
        evento4.setCidade("Recife");
        evento5.setCidade("Olinda");
        evento6.setCidade("Recife");
        evento7.setCidade("Olinda");
        evento8.setCidade("Olinda");
        evento9.setCidade("Recife");

        evento1.setBairro("Cais da Alfândega");
        evento2.setBairro("Alto da Sé");
        evento3.setBairro("Bairro");
        evento4.setBairro("São José");
        evento5.setBairro("Bairro");
        evento6.setBairro("Bairro");
        evento7.setBairro("Bairro");
        evento8.setBairro("Salgadinho");
        evento9.setBairro("Guabiraba");

        evento1.setRua("Cais da Alfândega");
        evento2.setRua("Rua Bispo Coutinho");
        evento3.setRua("Avenida Sigismundo Gonçalves");
        evento4.setRua("Rua da Concórdia");
        evento5.setRua("Rua do Farol");
        evento6.setRua("Rua Capitao Lima");
        evento7.setRua("Rua");
        evento8.setRua("Avenida Professor Andrade Bezerra");
        evento9.setRua("R. Cataguáses");

        evento1.setNumero("s/n");
        evento2.setNumero("681");
        evento3.setNumero("742");
        evento4.setNumero("943");
        evento5.setNumero("218");
        evento6.setNumero("195");
        evento7.setNumero("s/n");
        evento8.setNumero("s/n");
        evento9.setNumero("100000");

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

        evento1.setDia("16");
        evento2.setDia("04");
        evento3.setDia("03");
        evento4.setDia("16");
        evento5.setDia("25");
        evento6.setDia("11");
        evento7.setDia("19");
        evento8.setDia("10");
        evento9.setDia("18");

        evento1.setMes("02");
        evento2.setMes("03");
        evento3.setMes("03");
        evento4.setMes("02");
        evento5.setMes("02");
        evento6.setMes("03");
        evento7.setMes("02");
        evento8.setMes("03");
        evento9.setMes("02");

        evento1.setAno("2017");
        evento2.setAno("2018");
        evento3.setAno("2017");
        evento4.setAno("2017");
        evento5.setAno("2017");
        evento6.setAno("2017");
        evento7.setAno("2017");
        evento8.setAno("2017");
        evento9.setAno("2017");

        evento1.setHorario_inicio("19:30");
        evento2.setHorario_inicio("10:00");
        evento3.setHorario_inicio("22:25");
        evento4.setHorario_inicio("18:00");
        evento5.setHorario_inicio("09:00");
        evento6.setHorario_inicio("23:00");
        evento7.setHorario_inicio("09:00");
        evento8.setHorario_inicio("22:00");
        evento9.setHorario_inicio("22:00");

        evento1.setHorario_fim("01:30");
        evento2.setHorario_fim("16:20");
        evento3.setHorario_fim("05:00");
        evento4.setHorario_fim("01:00");
        evento5.setHorario_fim("15:00");
        evento6.setHorario_fim("05:00");
        evento7.setHorario_fim("22:00");
        evento8.setHorario_fim("05:00");
        evento9.setHorario_fim("16:00");

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
