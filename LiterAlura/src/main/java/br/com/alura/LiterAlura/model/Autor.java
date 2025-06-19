package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String ano_nacimento;
    private String ano_morte;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DataAutor dataAutor) {
        this.nome = dataAutor.nome();
        this.ano_nacimento = dataAutor.ano_nacimento();
        this.ano_morte = dataAutor.ano_morte();
    }

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

    public String getAno_nacimento() {
        return ano_nacimento;
    }

    public void setAno_nacimento(String ano_nacimento) {
        this.ano_nacimento = ano_nacimento;
    }

    public String getAno_morte() {
        return ano_morte;
    }

    public void setAno_morte(String ano_morte) {
        this.ano_morte = ano_morte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public void setBooks(List<Livro> livros) {
        this.livros = new ArrayList<>();
        livros.forEach(b -> {
            b.setAutor(this);
            this.livros.add(b);
        });
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", Autor='" + nome + '\'' +
                ", ano_nacimento='" + ano_nacimento + '\'' +
                ", ano_morte='" + ano_morte + '\'' +
                ", livros=" + livros;
    }
}


