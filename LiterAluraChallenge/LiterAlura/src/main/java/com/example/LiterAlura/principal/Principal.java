package com.example.LiterAlura.principal;

import com.example.LiterAlura.model.Autor;
import com.example.LiterAlura.model.Data;
import com.example.LiterAlura.model.DatosLibro;
import com.example.LiterAlura.model.Libro;
import com.example.LiterAlura.repository.AutorRepository;
import com.example.LiterAlura.repository.LibroRepository;
import com.example.LiterAlura.service.API;
import com.example.LiterAlura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private API api = new API();
    private final String URL = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository lib, AutorRepository aut){
        this.libroRepository = lib;
        this.autorRepository = aut;
    }

    public void showMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPoridioma();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que buscas");
        var nombreLibro = teclado.nextLine();
        var json = api.obtenerDatos(URL+nombreLibro.replace(" ", "+"));

        Data datos = conversor.obtenerDatos(json, Data.class);

        Optional<DatosLibro> libroEncontrado = datos.results().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if(libroEncontrado.isPresent()){
            DatosLibro datosLibro = libroEncontrado.get();
            Optional<Libro> existeLibro = libroRepository.findByTitulo(datosLibro.titulo());

            if(existeLibro.isPresent()){
                System.out.println("El libro con el titulo '" + datosLibro.titulo() + "' ya existe");
            }else{
                Libro libro = new Libro(datosLibro);
                Autor autor = new Autor(datosLibro.autor().getFirst());
                libro.setAutor(autor);
                libroRepository.save(libro);
                autorRepository.save(autor);
            }
        }else{
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.stream()
                .forEach(s->System.out.println("Titulo: " + s.getTitulo()
                +"\nAutor: " + s.getAutorNombre() + "\nIdioma: " + s.getIdiomas() + "\nNumero de descargas: " + s.getNumeroDescargas() + "\n"));
    }

    public void listarAutoresRegistrados(){
        autores = autorRepository.findAll();
        autores.stream()
                .forEach(a-> System.out.println("Nombre: " + a.getNombre() + "\nFecha de nacimiento: " + a.getBirthYear() + "\nFecha de fallecimiento: " + a.getDeathYear() + "\n"));
    }

    public void listarAutoresVivos(){
        System.out.println("Ingresa el año para buscar a los autores vivos en ese año: ");
        var year = teclado.nextInt();
        List<Autor> autoresVivos = autorRepository.autoresVivos(year);
        if(autoresVivos.isEmpty()){
            System.out.println("No hay ningun autor registrado que haya estado vivo en el año "+year);
        }else {
            System.out.println("Los autores vivos en el año "+year);
            autoresVivos.stream()
                    .forEach(a-> System.out.println("Nombre: " + a.getNombre() + "\nFecha de nacimiento: " + a.getBirthYear() + "\nFecha de fallecimiento: " + a.getDeathYear() + "\n"));
        }
    }

    public void listarLibrosPoridioma(){
        System.out.println("""
                Escribe uno de los siguientes idiomas:
                es - español
                en - ingles
                fr - frances
                pt - portugués 
                """);
        var idioma = teclado.nextLine();
        List<Libro> librosPoridioma = libroRepository.findByIdiomas(idioma);
        if (librosPoridioma.isEmpty()){
            System.out.println("No hay libros registrados en el idioma " + idioma);
        }else {
            System.out.println("Los libros del idioma: " + idioma);
            librosPoridioma.stream()
                    .forEach(s->System.out.println("Titulo: " + s.getTitulo()
                            +"\nAutor: " + s.getAutorNombre() + "\nIdioma: " + s.getIdiomas() + "\nNumero de descargas: " + s.getNumeroDescargas() + "\n"));
        }
    }

}
