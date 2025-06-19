package br.com.alura.LiterAlura;

import br.com.alura.LiterAlura.principal.Principal;
import br.com.alura.LiterAlura.repository.RepositorioAutor;
import br.com.alura.LiterAlura.repository.RepositorioLivros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner  {
    @Autowired
    RepositorioLivros repositorioLivros;
    @Autowired
    RepositorioAutor repositorioAutor;
    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }
    @Override
    public void run (String... args) throws Exception{
        Principal principal = new Principal(repositorioLivros, repositorioAutor);
        principal.start();
    }


}
