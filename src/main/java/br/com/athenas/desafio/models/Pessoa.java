package br.com.athenas.desafio.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    private Double altura;

    @Column(name = "Peso")
    private Double peso;

    public Pessoa() { }

    public Pessoa(String pNome, LocalDate pData, Character pSexo, Double pAltura, Double pPeso) {
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

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
    // #endregion

}
