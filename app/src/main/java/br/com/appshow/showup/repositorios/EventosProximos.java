package br.com.appshow.showup.repositorios;

import java.util.ArrayList;

import br.com.appshow.showup.entidades.Evento;

/**
 * Created by jailson on 03/02/17.
 */

public class EventosProximos {

    private ArrayList<Evento> eventosProximos;

    public EventosProximos(ArrayList<Evento> eventos){

        this.eventosProximos = eventos;
    }

    public ArrayList<Evento> getArrayEvento(){

        return this.eventosProximos;
    }

    public Evento getEventoByIndex(int index){

        return this.eventosProximos.get(index);
    }

    public Evento getEventoByCod(String codigo){

        int ans = searchIndexByCod(codigo);
        if(ans == -1) return null;
        else return this.eventosProximos.get(ans);
    }

    public int searchIndexByCod(String codigo){

        for(int i = 0; i < this.eventosProximos.size(); i++){

            if(this.eventosProximos.get(i).getId_evento() == codigo) return i;
        }
        return -1;
    }
}
