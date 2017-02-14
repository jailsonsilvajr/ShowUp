package br.com.appshow.showup.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.appshow.showup.R;
import br.com.appshow.showup.conexao.Conectar;
import br.com.appshow.showup.entidades.Contratante;
import br.com.appshow.showup.entidades.Evento;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jailson on 08/02/17.
 */

public class ContratanteMeusEventosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Contratante contratante;
    private ArrayList<Evento> meusEventos;

    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contratante_meus_eventos_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contratante_meus_eventos_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_meus_eventos_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.contratante_meus_eventos_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("EVENTOS");

        Intent intent = getIntent();
        this.contratante = intent.getParcelableExtra("paramsContratante");

        //----------------------------------------------------------------------------//

        //--(1) Configurar listview de eventos:
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            url = "http://192.241.244.47/showup/obter_evento_app_contratante.php?";
            parametros = "id_contratante=" + this.contratante.getId_contratante();
            new SolicitarDados().execute(url);
        }else{

            Toast.makeText(this, "Nenhuma conexão foi encontrada!", Toast.LENGTH_SHORT).show();
        }
        //--Fim de (1)

        //--(2) Configurando menu lateral:
        View hView =  navigationView.getHeaderView(0);
        ImageView contratante_meus_eventos_nav_header_image_background = (ImageView) hView.findViewById(R.id.contratante_meus_eventos_nav_header_image_background);
        Button contratante_meus_eventos_nav_header_button_configuracao = (Button) hView.findViewById(R.id.contratante_meus_eventos_nav_header_button_configuracao);
        CircleImageView contratante_meus_eventos_nav_header_image_perfil = (CircleImageView) hView.findViewById(R.id.contratante_meus_eventos_nav_header_image_perfil);
        TextView contratante_meus_eventos_nav_header_textview_nome = (TextView) hView.findViewById(R.id.contratante_meus_eventos_nav_header_textview_nome);

        contratante_meus_eventos_nav_header_image_background.setImageResource(R.drawable.temp_background_menu_lateral);
        contratante_meus_eventos_nav_header_image_perfil.setImageResource(R.drawable.temp_foto_perfil);
        contratante_meus_eventos_nav_header_textview_nome.setText(this.contratante.getNome());
        contratante_meus_eventos_nav_header_button_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_activity_configuracao();
            }
        });
        contratante_meus_eventos_nav_header_image_perfil.setOnClickListener(new View.OnClickListener() {
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

    public void open_activity_evento(int position){

        Intent activity_artista = new Intent(this, ContratanteEventoActivity.class);
        activity_artista.putExtra("paramsContratante", this.contratante);
        activity_artista.putExtra("paramsEvento", this.meusEventos.get(position));
        startActivity(activity_artista);
    }

    public void open_activity_configuracao(){

        Intent activity_configuracoes = new Intent(this, ContratanteConfiguracoesActivity.class);
        activity_configuracoes.putExtra("paramsContratante", this.contratante);
        startActivity(activity_configuracoes);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_meus_eventos_drawer_layout);
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
        if (id == R.id.contratante_meus_eventos_drawer_menu_nav_pagina_inicial) {
            return true;
        } if (id == R.id.contratante_meus_eventos_drawer_menu_nav_mensagens) {
            return true;
        } if (id == R.id.contratante_meus_eventos_drawer_menu_nav_eventos) {
            return true;
        } if (id == R.id.contratante_meus_eventos_drawer_menu_nav_favoritos) {
            return true;
        } if (id == R.id.contratante_meus_eventos_drawer_menu_nav_sair) {
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

        if (id == R.id.contratante_meus_eventos_drawer_menu_nav_pagina_inicial) {

        } else if (id == R.id.contratante_meus_eventos_drawer_menu_nav_mensagens) {

        } else if (id == R.id.contratante_meus_eventos_drawer_menu_nav_eventos) {

        } else if (id == R.id.contratante_meus_eventos_drawer_menu_nav_favoritos) {

            Intent activity_favoritos = new Intent(this, ContratanteFavoritosActivity.class);
            activity_favoritos.putExtra("paramsContratante", this.contratante);
            startActivity(activity_favoritos);

        } else if (id == R.id.contratante_meus_eventos_drawer_menu_nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.contratante_meus_eventos_drawer_layout);
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
            View rowView = mInflater.inflate(R.layout.contratante_meus_eventos_list_item_listview, parent, false);

            TextView contratante_meus_eventos_list_item_textview_nome_evento = (TextView) rowView.findViewById(R.id.contratante_meus_eventos_list_item_textview_nome_evento);
            ImageView contratante_meus_eventos_list_item_image_evento = (ImageView) rowView.findViewById(R.id.contratante_meus_eventos_list_item_image_evento);
            ImageView contratante_meus_eventos_list_item_button_evento = (ImageView) rowView.findViewById(R.id.contratante_meus_eventos_list_item_button_evento);

            Evento evento = (Evento) getItem(position);
            contratante_meus_eventos_list_item_textview_nome_evento.setText(evento.getNome());
            contratante_meus_eventos_list_item_button_evento.setImageResource(R.drawable.seta_menor);

            Picasso.with(mContext) //Context
                .load(evento.getUrl_imagem_redonda()) //URL/FILE
                .into(contratante_meus_eventos_list_item_image_evento);//an ImageView Object to show the loaded image;*/

            return rowView;
        }
    }

    private class SolicitarDados extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){

            return Conectar.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String result){

            ListView contratante_eventos_content_listview_evento = (ListView) findViewById(R.id.contratante_meus_eventos_content_listview_evento);
            contratante_eventos_content_listview_evento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    open_activity_evento(i);
                }
            });

            Gson gson = new Gson();
            Evento[] eventos_array = gson.fromJson(result, Evento[].class);

            List eventos_lista = Arrays.asList(eventos_array);
            meusEventos = new ArrayList(eventos_lista);

            AdapterList adapterList = new AdapterList(ContratanteMeusEventosActivity.this, meusEventos);
            contratante_eventos_content_listview_evento.setAdapter(adapterList);
        }
    }
}
