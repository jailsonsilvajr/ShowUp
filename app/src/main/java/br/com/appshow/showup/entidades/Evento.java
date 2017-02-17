package br.com.appshow.showup.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jailson on 03/02/17.
 */

public class Evento implements Parcelable {

    private String id_evento;
    private String nome;
    private String local;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String descricao;
    private String dia;
    private String mes;
    private String ano;
    private String horario_inicio;
    private String horario_fim;
    private String equipamentos;
    private String requisito;
    private String url_imagem_principal;
    private String url_imagem_secundaria;
    private String url_imagem_redonda;

    public Evento(){}

    protected Evento(Parcel in) {
        id_evento = in.readString();
        nome = in.readString();
        local = in.readString();
        estado = in.readString();
        cidade = in.readString();
        bairro = in.readString();
        rua = in.readString();
        numero = in.readString();
        descricao = in.readString();
        dia = in.readString();
        mes = in.readString();
        ano = in.readString();
        horario_inicio = in.readString();
        horario_fim = in.readString();
        equipamentos = in.readString();
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
        dest.writeString(local);
        dest.writeString(estado);
        dest.writeString(cidade);
        dest.writeString(bairro);
        dest.writeString(rua);
        dest.writeString(numero);
        dest.writeString(descricao);
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(ano);
        dest.writeString(horario_inicio);
        dest.writeString(horario_fim);
        dest.writeString(equipamentos);
        dest.writeString(requisito);
        dest.writeString(url_imagem_principal);
        dest.writeString(url_imagem_secundaria);
        dest.writeString(url_imagem_redonda);
    }

    public String getEndereco(){

        return cidade + ", " + bairro + ", " + rua + ", " + numero;
    }

    public String getHorario(){

        return "HORA: " + horario_inicio + "H às " + horario_fim + "H";
    }

    public String getData(){

        return "DIA: " + dia + "/" + mes + "/" + ano;
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
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
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
