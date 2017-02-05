package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 05/02/17.
 */

public class Usuario implements Parcelable {

    private Artista artista;
    private Contratante contratante;

    public Usuario(Artista artista, Contratante contratante){

        this.artista = artista;
        this.contratante = contratante;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    protected Usuario(Parcel in) {

        artista = (Artista) in.readParcelable(Artista.class.getClassLoader());
        contratante = (Contratante) in.readParcelable(Contratante.class.getClassLoader());
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {

        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(artista, flags);
        dest.writeParcelable(contratante, flags);
    }
}
