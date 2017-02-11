package br.com.appshow.showup.repositorios;

import java.util.ArrayList;

import br.com.appshow.showup.entidades.Evento;

/**
 * Created by jailson on 03/02/17.
 */

public class EventosBuscados {

    private ArrayList<Evento> eventosBuscados;

    public EventosBuscados(ArrayList<Evento> eventos){

        this.eventosBuscados = eventos;
    }

    public ArrayList<Evento> getArrayEvento(){

        return this.eventosBuscados;
    }

    public Evento getEventoByIndex(int index){

        return this.eventosBuscados.get(index);
    }

    public Evento getEventoByCod(String codigo){

        int ans = searchIndexByCod(codigo);
        if(ans == -1) return null;
        else return this.eventosBuscados.get(ans);
    }

    public int searchIndexByCod(String codigo){

        for(int i = 0; i < this.eventosBuscados.size(); i++){

            if(this.eventosBuscados.get(i).getId_evento() == codigo) return i;
        }
        return -1;
    }
}
