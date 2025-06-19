package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;
    private List<String> languages = new ArrayList<>();
    private int downloads;

    public Livro() { }

    public Livro(DataLivro dataLivro) {
        this.titulo = dataLivro.titulo();
        this.languages = dataLivro.languages();
        this.downloads = dataLivro.downloads();
        this.autor = new Autor(dataLivro.autor().get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return titulo;
    }

    public void setTitle(String title) {
        this.titulo = title;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", Livro='" + titulo + '\'' +
                ", autor=" + autor +
                ", languages=" + languages +
                ", downloads=" + downloads;
    }
}
