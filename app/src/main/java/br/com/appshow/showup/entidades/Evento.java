package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Evento implements Parcelable {

    private String codigo;
    private String nomeEvento;
    private String tempoEvento;
    private String nomeLocal;
    private String enderecoLocal;
    private String descricao;
    private String data;
    private String horario;
    private String requisito;
    private String url_imagem;

    public Evento(){}

    protected Evento(Parcel in) {
        codigo = in.readString();
        nomeEvento = in.readString();
        tempoEvento = in.readString();
        nomeLocal = in.readString();
        enderecoLocal = in.readString();
        descricao = in.readString();
        data = in.readString();
        horario = in.readString();
        requisito = in.readString();
        url_imagem = in.readString();
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
        dest.writeString(codigo);
        dest.writeString(nomeEvento);
        dest.writeString(tempoEvento);
        dest.writeString(nomeLocal);
        dest.writeString(enderecoLocal);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(horario);
        dest.writeString(requisito);
        dest.writeString(url_imagem);
    }

    public String getCod(){

        return this.codigo;
    }

    public void setCod(String codigo){

        this.codigo = codigo;
    }

    public String getNomeEvento(){

        return this.nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getTempoEvento(){

        return this.tempoEvento;
    }

    public void setTempoEvento(String tempoEvento) {
        this.tempoEvento = tempoEvento;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getEnderecoLocal() {
        return enderecoLocal;
    }

    public void setEnderecoLocal(String enderecoLocal) {
        this.enderecoLocal = enderecoLocal;
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

    public String getUrl_imagem() {
        return url_imagem;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }
}
