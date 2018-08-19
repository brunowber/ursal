package model;

import java.util.Date;

public class Usuario {
    String nome;
    String guerrilheiro;
    Date  Entrou;

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getGuerrilheiro() {return guerrilheiro;}
    public void setGuerrilheiro(String guerrilheiro) {this.guerrilheiro = guerrilheiro;}

    public Date getEntrou() {return Entrou;}
    public void setEntrou(Date entrou) {Entrou = entrou;}
}
