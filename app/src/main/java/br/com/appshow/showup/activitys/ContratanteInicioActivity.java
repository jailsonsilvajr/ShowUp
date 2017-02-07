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
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

import br.com.appshow.showup.R;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.entidades.Evento;
import br.com.appshow.showup.entidades.Usuario;
import br.com.appshow.showup.repositorios.ArtistasProximos;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 06/02/17.
 */

public class ContratanteInicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Usuario user;
    private Contratante contratante;
    private ArtistasProximos artistasProximos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_inicio_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_inicio_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_inicio_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_inicio_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("INÍCIO");

        Intent intent = getIntent();
        this.user = intent.getParcelableExtra("paramsUsuario");
        this.contratante = user.getContratante();

        //----------------------------------------------------------------------------//

        //--(0) Populando array de artisas:
        this.artistasProximos = new ArtistasProximos(popularArrayArtista());
        //--Fim de (0)

        //--(1) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_inicio_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_inicio_nav_header_image_background);
        Button contratante_inicio_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_inicio_nav_header_button_configuracao);
        CircleImageView contratante_inicio_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_inicio_nav_header_image_perfil);
        TextView contratante_inicio_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_inicio_nav_header_textview_nome);

        contratante_inicio_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        contratante_inicio_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_inicio_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_inicio_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        //--Fim de (1)

        //--(2) Configurando a TwoWayView:
        TwoWayView contratante_inicio_content_twoWayView = (TwoWayView) findViewById(R.id.contratante_inicio_content_twoWayView);
        contratante_inicio_content_twoWayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                open_activity_artista(i+1);//Para retirar o primeiro artista que já aparece como principal
            }
        });

        ArrayList<Artista> subArrayArtista = new ArrayList<Artista>(); //Para retirar o primeiro artista que já aparece como principal
        for(int i = 1; i < this.artistasProximos.getArrayArtista().size(); i++){

            subArrayArtista.add(this.artistasProximos.getArrayArtista().get(i));
        }

        AdapterTwoWayView adapterTwoWayView = new AdapterTwoWayView(this, subArrayArtista);
        contratante_inicio_content_twoWayView.setAdapter(adapterTwoWayView);
        //--Fim de (2)

        //--(3) Configurando o button seta:
        Button contratante_inicio_content_button = (Button) findViewById(R.id.contratante_inicio_content_button);
        contratante_inicio_content_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                open_activity_artista(0);
            }
        });
        //--Fim de (3)

        //--(4) Configurar artista principal/mais próximo:
        ImageView contratante_inicio_content_imageview = (ImageView) findViewById(R.id.contratante_inicio_content_imageview);
        TextView contratante_inicio_content_textview_nome_artista = (TextView) findViewById(R.id.contratante_inicio_content_textview_nome_artista);
        TextView contratante_inicio_content_textview_estilo_artista = (TextView) findViewById(R.id.contratante_inicio_content_textview_estilo_artista);

        contratante_inicio_content_imageview.setImageResource(R.drawable.temp_evento1);
        contratante_inicio_content_textview_nome_artista.setText(this.artistasProximos.getArtistaByIndex(0).getNome());
        contratante_inicio_content_textview_estilo_artista.setText(this.artistasProximos.getArtistaByIndex(0).getEstilo());
        //--Fim de (4)

        //--Configurando buttons de criar evento, evento urgente e meus eventos:
        ImageView contratante_inicio_content_button_criar_evento = (ImageView) findViewById(R.id.contratante_inicio_content_button_criar_evento);
        ImageView contratante_inicio_content_button_evento_urgente = (ImageView) findViewById(R.id.contratante_inicio_content_button_evento_urgente);
        ImageView contratante_inicio_content_button_meus_eventos = (ImageView) findViewById(R.id.contratante_inicio_content_button_meus_eventos);

        contratante_inicio_content_button_criar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_criar_evento();
            }
        });
        contratante_inicio_content_button_evento_urgente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_evento_urgente();
            }
        });
        contratante_inicio_content_button_meus_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_meus_eventos();
            }
        });
    }

    public void open_activity_configuracao(){}

    public void open_activity_artista(int position){}

    public void open_activity_criar_evento(){}

    public void open_activity_evento_urgente(){}

    public void open_activity_meus_eventos(){}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_inicio_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contratante_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.contratante_inicio_menu_action_buscar) {

            Intent activity_busca = new Intent(this, ContratanteBuscaActivity.class);
            activity_busca.putExtra("paramsContratante", this.contratante);
            startActivity(activity_busca);
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_inicio_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_inicio_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_favoritos) {

        } else if (id == R.id.contratante_inicio_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_inicio_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class AdapterTwoWayView extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Artista> mDataSource;

        public AdapterTwoWayView(Context context, ArrayList<Artista> itens){

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
            View rowView = mInflater.inflate(R.layout.contratante_inicio_list_item_twowayview, parent, false);

            TextView contratante_inicio_list_item_twowayview_nome_artista = (TextView) rowView.findViewById(R.id.contratante_inicio_list_item_twowayview_nome_artista);
            TextView contratante_inicio_list_item_twowayview_estilo = (TextView) rowView.findViewById(R.id.contratante_inicio_list_item_twowayview_estilo);
            ImageView contratante_inicio_list_item_twowayview_image = (ImageView) rowView.findViewById(R.id.contratante_inicio_list_item_twowayview_image);

            Artista artista = (Artista) getItem(position);
            contratante_inicio_list_item_twowayview_nome_artista.setText(artista.getNome());
            contratante_inicio_list_item_twowayview_estilo.setText(artista.getEstilo());
            contratante_inicio_list_item_twowayview_image.setImageResource(R.drawable.temp_evento2);

            /*Picasso.with(mContext) //Context
                .load("") //URL/FILE
                .into(proxEventoImageView);//an ImageView Object to show the loaded image;*/

            return rowView;
        }
    }

    public ArrayList<Artista> popularArrayArtista(){

        ArrayList<Artista> array = new ArrayList<Artista>();

        Artista artista1 = new Artista("Artista1");
        Artista artista2 = new Artista("Artista2");
        Artista artista3 = new Artista("Artista3");
        Artista artista4 = new Artista("Artista4");
        Artista artista5 = new Artista("Artista5");
        Artista artista6 = new Artista("Artista6");
        Artista artista7 = new Artista("Artista7");
        Artista artista8 = new Artista("Artista8");
        Artista artista9 = new Artista("Artista9");

        artista1.setCod("codigo1");
        artista2.setCod("codigo2");
        artista3.setCod("codigo3");
        artista4.setCod("codigo4");
        artista5.setCod("codigo5");
        artista6.setCod("codigo6");
        artista7.setCod("codigo7");
        artista8.setCod("codigo8");
        artista9.setCod("codigo9");

        artista1.setEstilo("estilo1");
        artista2.setEstilo("estilo2");
        artista3.setEstilo("estilo3");
        artista4.setEstilo("estilo4");
        artista5.setEstilo("estilo5");
        artista6.setEstilo("estilo6");
        artista7.setEstilo("estilo7");
        artista8.setEstilo("estilo8");
        artista9.setEstilo("estilo9");

        array.add(artista1);
        array.add(artista2);
        array.add(artista3);
        array.add(artista4);
        array.add(artista5);
        array.add(artista6);
        array.add(artista7);
        array.add(artista8);
        array.add(artista9);

        return array;
    }
}