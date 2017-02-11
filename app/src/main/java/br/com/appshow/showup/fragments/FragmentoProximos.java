package br.com.appshow.showup.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.appshow.showup.R;
import br.com.appshow.showup.activitys.ArtistaEventoActivity;
import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Evento;

/**
 * Created by jailson on 03/02/17.
 */

public class FragmentoProximos extends Fragment {

    private ArrayList<Evento> eventos;
    private Artista artista;

    public FragmentoProximos(){}

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.artista_inicio_tab, container, false);

        ListView artista_inicio_tab_listview = (ListView) rootView.findViewById(R.id.artista_inicio_tab_listview);
        artista_inicio_tab_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                open_evento(i);
            }
        });

        AdapterTabLayout adapterTabLayout = new AdapterTabLayout(rootView.getContext(), this.eventos, false);
        artista_inicio_tab_listview.setAdapter(adapterTabLayout);

        return rootView;
    }

    public void open_evento(int position){

        Intent activity_evento = new Intent(getActivity(), ArtistaEventoActivity.class);
        activity_evento.putExtra("paramsArtista", this.artista);
        activity_evento.putExtra("paramsEvento", this.eventos.get(position));
        startActivity(activity_evento);
    }

    public void setArtista(Artista artista){

        this.artista = artista;
    }

    public void setArrayListEventos(ArrayList<Evento> eventos){

        this.eventos = eventos;
    }

    public class AdapterTabLayout extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Evento> mDataSource;
        private boolean comSeta;

        public AdapterTabLayout(Context context, ArrayList<Evento> itens, boolean comSeta){

            mContext = context;
            mDataSource = itens;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.comSeta = comSeta;
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
            View rowView = mInflater.inflate(R.layout.artista_inicio_list_item_tablayout, parent, false);

            TextView artista_inicio_list_item_tablayout_textview_nome_evento = (TextView) rowView.findViewById(R.id.artista_inicio_list_item_tablayout_textview_nome_evento);
            TextView artista_inicio_list_item_tablayout_textview_local_evento = (TextView) rowView.findViewById(R.id.artista_inicio_list_item_tablayout_textview_local_evento);
            ImageView artista_inicio_list_item_tablayout_image_evento = (ImageView) rowView.findViewById(R.id.artista_inicio_list_item_tablayout_image_evento);
            ImageView artista_inicio_list_item_tablayout_button_evento = (ImageView) rowView.findViewById(R.id.artista_inicio_list_item_tablayout_button_evento);

            Evento evento = (Evento) getItem(position);
            artista_inicio_list_item_tablayout_textview_nome_evento.setText(evento.getNome());
            artista_inicio_list_item_tablayout_textview_local_evento.setText(evento.getLocal());
            artista_inicio_list_item_tablayout_image_evento.setImageResource(R.drawable.temp_evento3);
            if(!this.comSeta){

                artista_inicio_list_item_tablayout_button_evento.setImageResource(0);
            }else{

                artista_inicio_list_item_tablayout_button_evento.setImageResource(R.drawable.seta_menor);
            }

        /*Picasso.with(mContext) //Context
                .load("") //URL/FILE
                .into(proxEventoImageView);//an ImageView Object to show the loaded image;*/

            return rowView;
        }
    }
}
