package br.com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DataLivro(@JsonAlias("Title") String titulo,
                        @JsonAlias("authors") List<DataAutor> autor,
                        @JsonAlias("languages") List<String> languages,
                        @JsonAlias("download_count") int downloads) {
}
