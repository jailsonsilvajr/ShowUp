package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jailson on 03/02/17.
 */

public class Contratante extends Usuario implements Parcelable{

    public Contratante(){}

    protected Contratante(Parcel in) {

        setCpf(in.readString());
        setEmail(in.readString());
        setNome(in.readString());
        setSobrenome(in.readString());
        setId_endereco(in.readInt());
        setTelefone(in.readString());
        setUrl_foto_perfil(in.readString());

        //setEnderecoUsuario(in.createTypedArray(EnderecoUsuario.CREATOR));
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

        dest.writeString(getCpf());
        dest.writeString(getEmail());
        dest.writeString(getNome());
        dest.writeString(getSobrenome());
        dest.writeInt(getId_endereco());
        dest.writeString(getTelefone());
        dest.writeString(getUrl_foto_perfil());

        //dest.writeTypedArray(getEnderecoUsuario(), flags);
    }
}
