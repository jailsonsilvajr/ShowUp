package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Contratante implements Parcelable{

    String nome;

    public Contratante(String nome){

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    protected Contratante(Parcel in) {
        nome = in.readString();
    }

    public static final Parcelable.Creator<Contratante> CREATOR = new Parcelable.Creator<Contratante>() {

        @Override
        public Contratante createFromParcel(Parcel in) {
            return new Contratante(in);
        }

        @Override
        public Contratante[] newArray(int size) {
            return new Contratante[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
    }
}
