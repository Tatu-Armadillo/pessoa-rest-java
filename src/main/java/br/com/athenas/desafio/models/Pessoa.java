package br.com.athenas.desafio.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Nome")
    private String nome;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Data_Nasc")
    private LocalDate dataNasc;

    @Column(name = "Sexo")
    private Character sexo;

    @Column(name = "Altura")
    private Float altura;

    @Column(name = "Peso")
    private Float peso;

    public Pessoa() { }

    public Pessoa(String pNome, LocalDate pData, Character pSexo, Float pAltura, Float pPeso) {
        this.nome = pNome;
        this.dataNasc = pData;
        this.sexo = pSexo;
        this.altura = pAltura;
        this.peso = pPeso;
    }

    // #region Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }
    // #endregion

}
