package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jailson on 03/02/17.
 */

public class Contratante implements Parcelable{

    private String id_contratante;
    private String nome;
    private String email;
    private String cpf_cnpj;
    private String cep;
    private String data_nascimento; //TROCAR DE STRING PARA DATE!!
    private String numero_celular;
    private String url_foto_perfil;
    private String patch_foto_perfil;

    public Contratante(){}

    public String getId_contratante() {
        return id_contratante;
    }

    public void setId_contratante(String id_contratante) {
        this.id_contratante = id_contratante;
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
        return data_nascimento;
    }

    public void setNascimento(String nascimento) {
        this.data_nascimento = nascimento;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getUrl_foto_perfil() {
        return url_foto_perfil;
    }

    public void setUrl_foto_perfil(String url_foto_perfil) {
        this.url_foto_perfil = url_foto_perfil;
    }

    public String getPatch_foto_perfil() {
        return patch_foto_perfil;
    }

    public void setPatch_foto_perfil(String patch_foto_perfil) {
        this.patch_foto_perfil = patch_foto_perfil;
    }

    protected Contratante(Parcel in) {

        this.id_contratante = in.readString();
        this.nome = in.readString();
        this.email = in.readString();
        this.cpf_cnpj = in.readString();
        this.cep = in.readString();
        this.data_nascimento = in.readString();
        this.numero_celular = in.readString();
        this.url_foto_perfil = in.readString();
        this.patch_foto_perfil = in.readString();
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

        dest.writeString(id_contratante);
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(cpf_cnpj);
        dest.writeString(cep);
        dest.writeString(data_nascimento);
        dest.writeString(numero_celular);
        dest.writeString(url_foto_perfil);
        dest.writeString(patch_foto_perfil);
    }
}
