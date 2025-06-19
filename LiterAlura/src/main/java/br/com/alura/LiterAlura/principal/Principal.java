package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.model.*;
import br.com.alura.LiterAlura.repository.RepositorioAutor;
import br.com.alura.LiterAlura.repository.RepositorioLivros;
import br.com.alura.LiterAlura.service.ConsumoAPI;
import br.com.alura.LiterAlura.service.ConverteDados;
import jakarta.transaction.Transactional;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final Scanner keyboard = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConverteDados converteDados = new ConverteDados();
    private final RepositorioLivros repositorioLivros;
    private final RepositorioAutor repositorioAutor;

    public Principal(RepositorioLivros bookRepository, RepositorioAutor authorRepository) { this.repositorioLivros = bookRepository; this.repositorioAutor = authorRepository; }

    public void start() {
        var option = -1;

        while (option != 0) {
            var menu = """
                    \n
                    ======================================
                                  LiterAlura
                    ======================================
                    \n
                    --- Select an option ---
                    
                    1 - buscar livros pelo titulo
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em determinado ano
                    5 - lista livors em um determinado indioma 
                    6 - lista os Top 10 livros
                    7 - mostra estetistica de livros
                                        
                    0 - Sair
                    """;

            System.out.println(menu);

            if (keyboard.hasNextInt()) {
                option = keyboard.nextInt();
                keyboard.nextLine();

                switch (option) {
                    case 1:
                        searchBookByTitle();
                        break;
                    case 2:
                        listRegisteredBooks();
                        break;
                    case 3:
                        listRegisteredAuthors();
                        break;
                    case 4:
                        ListAuthorsAliveInAGivenYear();
                        break;
                    case 5:
                        listBooksByLanguage();
                        break;
                    case 6:
                        listTop10();
                        break;
                    case 7:
                        showDbStatistics();
                        break;
                    case 0:
                        System.out.println("\nSaindo...");
                        break;
                    default:
                        System.out.println("\nOpçao Invalida");
                }

            } else {
                System.out.println("\nEntrada invalida");
                keyboard.next();
            }
        }
    }

    @Transactional
    private void searchBookByTitle() {
        String BASE_URL = "https://gutendex.com/books/?search=";
        System.out.println("\nDigite o nome do livro que deseja pesquisar:");
        var title = keyboard.nextLine();

        if (!title.isBlank() && !isANumber(title)) {

            var json = consumoAPI.obtainData(BASE_URL + title.replace(" ", "%20"));
            var data = converteDados.obtainData(json, Data.class);
            Optional<DataLivro> searchBook = data.results()
                    .stream()
                    .filter(b -> b.titulo().toLowerCase().contains(b.titulo().toLowerCase()))
                    .findFirst();

            if (searchBook.isPresent()) {
                DataLivro dataLivro = searchBook.get();

                if (!verifiedBookExistence(dataLivro)) {
                    Livro book = new Livro(dataLivro);
                    DataAutor authorData = dataLivro.autor().get(0);
                    Optional<Autor> optionalAuthor = repositorioAutor.findByName(authorData.nome());

                    if (optionalAuthor.isPresent()) {
                        Autor existingAuthor = optionalAuthor.get();
                        book.setAutor(existingAuthor);
                        existingAuthor.getLivros().add(book);
                        repositorioAutor.save(existingAuthor);
                    } else {
                        Autor newAuthor = new Autor(authorData);
                        book.setAutor(newAuthor);
                        newAuthor.getLivros().add(book);
                        repositorioAutor.save(newAuthor);
                    }

                    repositorioLivros.save(book);

                } else {
                    System.out.println("\nLivro ja adicionado no Banco");
                }

            } else {
                System.out.println("\nLivro não encontrado ");
            }

        } else {
            System.out.println("\nEntrada invalida ");
        }

    }

    private void listRegisteredBooks() {
        List<Livro> books = repositorioLivros.findAll();

        if(!books.isEmpty()) {
            System.out.println("\n----- Livros registrados -----");
            books.forEach(System.out::println);
        } else {
            System.out.println("\nNada aqui, ainda");
        }

    }

    private void listRegisteredAuthors() {
        List<Autor> authors = repositorioAutor.findAll();

        if(!authors.isEmpty()) {
            System.out.println("\n----- Autores registrados -----");
            authors.forEach(System.out::println);
        } else {
            System.out.println("\nNada aqui, ainda");
        }

    }

    private boolean verifiedBookExistence(DataLivro bookData) {
        Livro book = new Livro(bookData);
        return repositorioLivros.verifiedBDExistence(book.getTitle());
    }

    private void ListAuthorsAliveInAGivenYear() {
        System.out.println("\nDigite o ano que deseja consultar: ");

        if (keyboard.hasNextInt()) {
            var year = keyboard.nextInt();
            List<Autor> authors = repositorioAutor.findAuthorsAlive(year);

            if (!authors.isEmpty()) {
                System.out.println("\n----- Autores registrados que vivem em " + year + " -----");
                authors.forEach(System.out::println);
            } else {
                System.out.println("\nNenhum resultado, entre em outro ano");
            }

        } else {
            System.out.println("\nEntrada inválida");
            keyboard.next();
        }

    }

    private void listBooksByLanguage() {
        var option = -1;
        String language = "";

        System.out.println("\nSelecione o idioma que deseja consultar");
        var languagesMenu = """
               \n
               1 - Inglês
               2 - Francês
               3 - Alemão
               4 - Portugues
               5 - Espanhol
               """;

        System.out.println(languagesMenu);

        if (keyboard.hasNextInt()) {
            option = keyboard.nextInt();

            switch (option) {
                case 1:
                    language = "en";
                    break;
                case 2:
                    language = "fr";
                    break;
                case 3:
                    language = "de";
                    break;
                case 4:
                    language = "pt";
                    break;
                case 5:
                    language = "es";
                    break;
                default:
                    System.out.println("\nOpção invalida");
            }

            System.out.println("\nLivros Registrados:");
            List<Livro> books = repositorioLivros.findBooksByLanguage(language);

            if (!books.isEmpty()) {
                books.forEach(System.out::println);
            } else {
                System.out.println("\nIdioma nao foi registrado");
            }

        } else {
            System.out.println("\nEntrada invalida");
            keyboard.next();
        }

    }

    private boolean isANumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void listTop10() {
        List<Livro> books = repositorioLivros.findTop10();

        if (!books.isEmpty()) {
            System.out.println("\n----- Os Top 10 livros baixados -----");
            books.forEach(b -> System.out.println(b.getTitle()));
        } else {
            System.out.println("\nNada aqui, ainda");
        }

    }

    private void showDbStatistics() {
        List<Livro> books = repositorioLivros.findAll();

        if (!books.isEmpty()) {
            IntSummaryStatistics sta = books.stream()
                    .filter(b -> b.getDownloads() > 0)
                    .collect(Collectors.summarizingInt(Livro::getDownloads));

            System.out.println("\n----- Estatistica de banco -----");
            System.out.println("Average downloads: " + sta.getAverage());
            System.out.println("Max downloads: " + sta.getMax());
            System.out.println("Min downloads: " + sta.getMin());
            System.out.println("Registered book/s: " + sta.getCount());
        } else {
            System.out.println("\nNothing here, yet");
        }

    }

}
