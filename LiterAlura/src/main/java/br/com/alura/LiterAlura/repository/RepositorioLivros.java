package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositorioLivros  extends  JpaRepository<Livro, Long>{
    @Query("SELECT COUNT(b) > 0 FROM Livro b WHERE b.titulo LIKE %:titulo%")
    Boolean verifiedBDExistence(@Param("titulo") String titulo);

    @Query(value = "SELECT * FROM livro WHERE :language = ANY (livro.languages)", nativeQuery = true)
    List<Livro> findBooksByLanguage(@Param("language") String language);

    @Query("SELECT b FROM Livro b ORDER BY b.downloads DESC LIMIT 10")
    List<Livro> findTop10();
}


