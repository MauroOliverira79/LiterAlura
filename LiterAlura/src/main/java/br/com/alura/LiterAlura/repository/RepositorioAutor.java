package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RepositorioAutor  extends JpaRepository<Autor, Long>{

    @Query("SELECT a FROM Autor a WHERE a.nome LIKE %:nome%")
    Optional<Autor> findByName(@Param("nome") String nome);

    @Query("SELECT a FROM Autor a WHERE :year BETWEEN CAST(a.ano_nacimento AS integer) AND CAST(a.ano_morte AS integer)")
    List<Autor> findAuthorsAlive(@Param("year") int year);
}



