package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Evento implements Parcelable {

    private String id_evento;
    private String nome;
    private String nome_local;
    private EnderecoEvento endereco_evento;
    private String descricao;
    private String data;
    private String horario_inicio;
    private String horario_fim;
    private String equipamentos;
    private String requisitos;
    private String url_imagem1;
    private String url_imagem2;
    private String url_imagem3;

    public Evento(){}

    protected Evento(Parcel in) {
        id_evento = in.readString();
        nome = in.readString();
        nome_local = in.readString();
        endereco_evento = in.readParcelable(EnderecoEvento.class.getClassLoader());
        descricao = in.readString();
        data = in.readString();
        horario_inicio = in.readString();
        horario_fim = in.readString();
        equipamentos = in.readString();
        requisitos = in.readString();
        url_imagem1 = in.readString();
        url_imagem2 = in.readString();
        url_imagem3 = in.readString();
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
        dest.writeString(nome_local);
        dest.writeParcelable(endereco_evento, flags);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(horario_inicio);
        dest.writeString(horario_fim);
        dest.writeString(equipamentos);
        dest.writeString(requisitos);
        dest.writeString(url_imagem1);
        dest.writeString(url_imagem2);
        dest.writeString(url_imagem3);
    }

    public EnderecoEvento getEndereco(){

        return endereco_evento;
    }

    public String getHorario(){

        return "HORA: " + horario_inicio + "H às " + horario_fim + "H";
    }

    public String getData(){

        return data;
    }

    public void setData(String data){

        this.data = data;
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

    public String getLocal() {
        return nome_local;
    }

    public void setLocal(String local) {
        this.nome_local = local;
    }

    public EnderecoEvento getEndereco_evento() {
        return endereco_evento;
    }

    public void setEndereco_evento(EnderecoEvento endereco_evento) {
        this.endereco_evento = endereco_evento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(String horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public String getHorario_fim() {
        return horario_fim;
    }

    public void setHorario_fim(String horario_fim) {
        this.horario_fim = horario_fim;
    }

    public String getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(String equipamentos) {
        this.equipamentos = equipamentos;
    }

    public String getRequisito() {
        return requisitos;
    }

    public void setRequisito(String requisito) {
        this.requisitos = requisito;
    }

    public String getUrl_imagem1() {
        return url_imagem1;
    }

    public void setUrl_imagem1(String url_imagem1) {
        this.url_imagem1 = url_imagem1;
    }

    public String getUrl_imagem2() {
        return url_imagem2;
    }

    public void setUrl_imagem2(String url_imagem2) {
        this.url_imagem2 = url_imagem2;
    }

    public String getUrl_imagem3() {
        return url_imagem3;
    }

    public void setUrl_imagem3(String url_imagem3) {
        this.url_imagem3 = url_imagem3;
    }
}
