package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Artista implements Parcelable{

    private String id_artista;
    private String nome;
    private String estilo;
    private String site;
    private String descricao;

    public Artista(){}

    public String getId_artista() {
        return id_artista;
    }

    public void setId_artista(String id_artista) {
        this.id_artista = id_artista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    protected Artista(Parcel in) {
        id_artista = in.readString();
        nome = in.readString();
        estilo = in.readString();
        site = in.readString();
        descricao = in.readString();
    }

    public static final Creator<Artista> CREATOR = new Creator<Artista>() {

        @Override
        public Artista createFromParcel(Parcel in) {
            return new Artista(in);
        }

        @Override
        public Artista[] newArray(int size) {
            return new Artista[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_artista);
        dest.writeString(nome);
        dest.writeString(estilo);
        dest.writeString(site);
        dest.writeString(descricao);
    }
}
