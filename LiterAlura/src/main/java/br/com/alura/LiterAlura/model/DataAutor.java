package br.com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataAutor(  @JsonAlias("name") String nome,
                          @JsonAlias("birth_year") String ano_nacimento ,
                          @JsonAlias("death_year") String ano_morte ) {

}
