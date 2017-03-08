package br.com.appshow.showup.entidades;

/**
 * Created by jailson on 05/02/17.
 */

public class Usuario{

    private String cpf;
    private String email;
    private String nome;
    private String sobrenome;
    private int id_endereco;
    private String telefone;
    private String url_foto_perfil;

    //private EnderecoUsuario[] enderecoUsuario;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUrl_foto_perfil() {
        return url_foto_perfil;
    }

    public void setUrl_foto_perfil(String url_foto_perfil) {
        this.url_foto_perfil = url_foto_perfil;
    }

    /*public EnderecoUsuario[] getEnderecoUsuario() {
        return enderecoUsuario;
    }

    public void setEnderecoUsuario(EnderecoUsuario[] enderecoUsuario) {
        this.enderecoUsuario = enderecoUsuario;
    }*/
}
