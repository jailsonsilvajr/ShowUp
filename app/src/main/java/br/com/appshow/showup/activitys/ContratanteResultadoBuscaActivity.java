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
import br.com.appshow.showup.entidades.Contratante;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 07/02/17.
 */

public class ContratanteResultadoBuscaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;
    private ArrayList<Artista> artistasEncontrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_resultado_busca_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_resultado_busca_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_resultado_busca_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_resultado_busca_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("DESCUBRA");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");

        //----------------------------------------------------------------------------//

        //--(1) Configurando listview de artistas:
        ListView contratante_resultado_busca_content_listview_artista = (ListView) findViewById(R.id.contratante_resultado_busca_content_listview_artista);
        contratante_resultado_busca_content_listview_artista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                open_activity_artista(i);
            }
        });

        this.artistasEncontrados = popularArtistas();//Metodo TEMPORÁRIO!!!
        AdapterList adapterList = new AdapterList(this, this.artistasEncontrados);
        contratante_resultado_busca_content_listview_artista.setAdapter(adapterList);
        //--Fim de (1)

        //--(2) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_resultado_busca_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_resultado_busca_nav_header_image_background);
        Button contratante_resultado_busca_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_resultado_busca_nav_header_button_configuracao);
        CircleImageView contratante_resultado_busca_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_resultado_busca_nav_header_image_perfil);
        TextView contratante_resultado_busca_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_resultado_busca_nav_header_textview_nome);

        contratante_resultado_busca_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        contratante_resultado_busca_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_resultado_busca_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_resultado_busca_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        contratante_resultado_busca_nav_header_image_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_alterar_perfil();
            }
        });
        //--Fim de (2)

    }

    public void open_activity_alterar_perfil(){

        Intent activity_alterar_perfil = new Intent(this, ContratanteAlterarPerfilActivity.class);
        activity_alterar_perfil.putExtra("paramsContratante", this.contratante);
        startActivity(activity_alterar_perfil);
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    public void open_activity_artista(int position){

        Intent activity_artista = new Intent(this, ContratanteArtistaActivity.class);
        activity_artista.putExtra("paramsContratante", this.contratante);
        activity_artista.putExtra("paramsArtista", this.artistasEncontrados.get(position));
        startActivity(activity_artista);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_resultado_busca_drawer_layout);
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
        if (id == R.id.contratante_resultado_busca_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_resultado_busca_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_resultado_busca_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_resultado_busca_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_resultado_busca_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_resultado_busca_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_resultado_busca_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_resultado_busca_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_resultado_busca_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);
        } else if (id == R.id.contratante_resultado_busca_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_resultado_busca_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class AdapterList extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Artista> mDataSource;

        public AdapterList(Context context, ArrayList<Artista> itens){

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
            View rowView = mInflater.inflate(R.layout.contratante_resultado_busca_list_item_listview, parent, false);

            TextView contratante_resultado_busca_list_item_textview_nome_artista = (TextView) rowView.findViewById(R.id.contratante_resultado_busca_list_item_textview_nome_artista);
            TextView contratante_resultado_busca_list_item_textview_genero_artista = (TextView) rowView.findViewById(R.id.contratante_resultado_busca_list_item_textview_genero_artista);
            ImageView contratante_resultado_busca_list_item_image_artista = (ImageView) rowView.findViewById(R.id.contratante_resultado_busca_list_item_image_artista);
            ImageView contratante_resultado_busca_list_item_button_artista = (ImageView) rowView.findViewById(R.id.contratante_resultado_busca_list_item_button_artista);

            Artista artista = (Artista) getItem(position);
            contratante_resultado_busca_list_item_textview_nome_artista.setText(artista.getNome());
            contratante_resultado_busca_list_item_textview_genero_artista.setText(artista.getEstilo());
            contratante_resultado_busca_list_item_image_artista.setImageResource(R.drawable.temp_evento3);
            contratante_resultado_busca_list_item_button_artista.setImageResource(R.drawable.seta_menor);

        /*Picasso.with(mContext) //Context
                .load("") //URL/FILE
                .into(proxEventoImageView);//an ImageView Object to show the loaded image;*/

            return rowView;
        }
    }

    public ArrayList<Artista> popularArtistas(){

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

        artista1.setSite("www.artista1.net");
        artista2.setSite("www.artista2.net");
        artista3.setSite("www.artista3.net");
        artista4.setSite("www.artista4.net");
        artista5.setSite("www.artista5.net");
        artista6.setSite("www.artista6.net");
        artista7.setSite("www.artista7.net");
        artista8.setSite("www.artista8.net");
        artista9.setSite("www.artista9.net");

        artista1.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista2.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista3.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista4.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista5.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista6.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista7.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista8.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");
        artista9.setDescricao("Descrição"+"\n"+"Descrição"+"\n"+"Descrição.");

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
