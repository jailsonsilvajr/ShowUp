package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jailson on 03/02/17.
 */

public class Contratante implements Parcelable{

    private String nome;
    private String email;
    private String cpf_cnpj;
    private String cep;
    private String nascimento; //TROCAR DE STRING PARA DATE!!
    private String numero_celular;

    public Contratante(String nome){

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    protected Contratante(Parcel in) {
        this.nome = in.readString();
        this.email = in.readString();
        this.cpf_cnpj = in.readString();
        this.cep = in.readString();
        this.nascimento = in.readString();
        this.numero_celular = in.readString();
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
        dest.writeString(email);
        dest.writeString(cpf_cnpj);
        dest.writeString(cep);
        dest.writeString(nascimento);
        dest.writeString(numero_celular);
    }
}
