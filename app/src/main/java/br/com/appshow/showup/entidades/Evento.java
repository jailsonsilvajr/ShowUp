package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Evento implements Parcelable {

    private String id_evento;
    private String nome;
    private String tempo;
    private String local;
    private String endereco;
    private String descricao;
    private String data;
    private String horario;
    private String requisito;
    private String url_imagem_principal;
    private String url_imagem_secundaria;
    private String url_imagem_redonda;

    public Evento(){}

    protected Evento(Parcel in) {
        id_evento = in.readString();
        nome = in.readString();
        tempo = in.readString();
        local = in.readString();
        endereco = in.readString();
        descricao = in.readString();
        data = in.readString();
        horario = in.readString();
        requisito = in.readString();
        url_imagem_principal = in.readString();
        url_imagem_secundaria = in.readString();
        url_imagem_redonda = in.readString();
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {

        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_evento);
        dest.writeString(nome);
        dest.writeString(tempo);
        dest.writeString(local);
        dest.writeString(endereco);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(horario);
        dest.writeString(requisito);
        dest.writeString(url_imagem_principal);
        dest.writeString(url_imagem_secundaria);
        dest.writeString(url_imagem_redonda);
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getUrl_imagem_principal() {
        return url_imagem_principal;
    }

    public void setUrl_imagem_principal(String url_imagem_principal) {
        this.url_imagem_principal = url_imagem_principal;
    }

    public String getUrl_imagem_secundaria() {
        return url_imagem_secundaria;
    }

    public void setUrl_imagem_secundaria(String url_imagem_secundaria) {
        this.url_imagem_secundaria = url_imagem_secundaria;
    }

    public String getUrl_imagem_redonda() {
        return url_imagem_redonda;
    }

    public void setUrl_imagem_redonda(String url_imagem_redonda) {
        this.url_imagem_redonda = url_imagem_redonda;
    }
}
