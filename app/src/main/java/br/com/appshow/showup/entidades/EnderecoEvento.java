package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/03/17.
 */

public class EnderecoEvento implements Parcelable{

    private int id_endereco;
    private String cep;
    private String pais;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String complemento;

    public EnderecoEvento(){}

    protected EnderecoEvento(Parcel in) {

        id_endereco = in.readInt();
        cep = in.readString();
        pais = in.readString();
        estado = in.readString();
        cidade = in.readString();
        bairro = in.readString();
        rua = in.readString();
        numero = in.readInt();
        complemento = in.readString();
    }

    public static final Creator<EnderecoEvento> CREATOR = new Creator<EnderecoEvento>() {

        @Override
        public EnderecoEvento createFromParcel(Parcel in) {
            return new EnderecoEvento(in);
        }

        @Override
        public EnderecoEvento[] newArray(int size) {
            return new EnderecoEvento[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id_endereco);
        dest.writeString(cep);
        dest.writeString(pais);
        dest.writeString(estado);
        dest.writeString(cidade);
        dest.writeString(bairro);
        dest.writeString(rua);
        dest.writeInt(numero);
        dest.writeString(complemento);
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
