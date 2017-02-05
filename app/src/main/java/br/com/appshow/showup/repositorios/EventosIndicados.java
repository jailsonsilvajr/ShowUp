package br.com.appshow.showup.repositorios;

import java.util.ArrayList;

import br.com.appshow.showup.entidades.Evento;

/**
 * Created by jailson on 03/02/17.
 */

public class EventosIndicados {

    private ArrayList<Evento> eventosIndicados;

    public EventosIndicados(ArrayList<Evento> eventos){

        this.eventosIndicados = eventos;
    }

    public ArrayList<Evento> getArrayEvento(){

        return this.eventosIndicados;
    }

    public Evento getEventoByIndex(int index){

        return this.eventosIndicados.get(index);
    }

    public Evento getEventoByCod(String codigo){

        int ans = searchIndexByCod(codigo);
        if(ans == -1) return null;
        else return this.eventosIndicados.get(ans);
    }

    public int searchIndexByCod(String codigo){

        for(int i = 0; i < this.eventosIndicados.size(); i++){

            if(this.eventosIndicados.get(i).getCod() == codigo) return i;
        }
        return -1;
    }
}
