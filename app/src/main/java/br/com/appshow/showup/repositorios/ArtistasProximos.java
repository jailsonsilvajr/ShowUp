package br.com.appshow.showup.repositorios;

import java.util.ArrayList;

import br.com.appshow.showup.entidades.Artista;
import br.com.appshow.showup.entidades.Evento;

/**
 * Created by jailson on 06/02/17.
 */

public class ArtistasProximos {

    private ArrayList<Artista> artistasProximos;

    public ArtistasProximos(ArrayList<Artista> artistas){

        this.artistasProximos = artistas;
    }

    public ArrayList<Artista> getArrayArtista(){

        return this.artistasProximos;
    }

    public Artista getArtistaByIndex(int index){

        return this.artistasProximos.get(index);
    }

    public Artista getArtistaByCod(String codigo){

        int ans = searchIndexByCod(codigo);
        if(ans == -1) return null;
        else return this.artistasProximos.get(ans);
    }

    public int searchIndexByCod(String codigo){

        for(int i = 0; i < this.artistasProximos.size(); i++){

            if(this.artistasProximos.get(i).getId_artista() == codigo) return i;
        }
        return -1;
    }
}
