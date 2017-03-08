package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Artista extends Usuario implements Parcelable{

    private String estilo_musical;
    private String site;
    private String descricao;

    public Artista(){}

    public String getEstilo() {
        return estilo_musical;
    }

    public void setEstilo(String estilo) {
        this.estilo_musical = estilo;
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

        setCpf(in.readString());
        setEmail(in.readString());
        setNome(in.readString());
        setSobrenome(in.readString());
        setId_endereco(in.readInt());
        setTelefone(in.readString());
        setUrl_foto_perfil(in.readString());
        estilo_musical = in.readString();
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

        dest.writeString(getCpf());
        dest.writeString(getEmail());
        dest.writeString(getNome());
        dest.writeString(getSobrenome());
        dest.writeInt(getId_endereco());
        dest.writeString(getTelefone());
        dest.writeString(getUrl_foto_perfil());
        dest.writeString(estilo_musical);
        dest.writeString(site);
        dest.writeString(descricao);
    }
}
