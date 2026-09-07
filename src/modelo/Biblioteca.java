package modelo;

import java.util.*;

    public class Biblioteca {
        // Array de tamaño fijo para géneros predefinidos
        public static final String[] GENEROS_PREDEFINIDOS = {"Novela", "Ciencia", "Historia", "Infantil", "Técnico"};

        // Colección Principal
        private List<Libro> listaLibros;

        // BONUS 1: HashSet para control de ISBN unicos en O(1)
        private Set<String> isbnSet;

        // BONUS 2: TreeMap para mantener autores e índice ordenados alfabéticamente
        private Map<String, List<Libro>> indicePorAutor;

        // BONUS 3: Stack para historial de eliminación (Deshacer)
        private Stack<Libro> historialEliminados;

        public Biblioteca() {
            this.listaLibros = new ArrayList<>();
            this.isbnSet = new HashSet<>();
            this.indicePorAutor = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            this.historialEliminados = new Stack<>();
        }

        // Método con retorno boolean para validar y agregar
        public boolean agregarLibro(Libro libro) {
            if (isbnSet.contains(libro.getIsbn())) {
                return false; // ISBN Ya existe
            }

            listaLibros.add(libro);
            isbnSet.add(libro.getIsbn());

            // Actualizar el TreeMap por autor
            indicePorAutor.computeIfAbsent(libro.getAutor().trim(), k -> new ArrayList<>()).add(libro);
            return true;
        }

        // Método para eliminar libro por ISBN
        public boolean eliminarLibro(String isbn) {
            Libro libroEncontrado = null;
            for (Libro l : listaLibros) {
                if (l.getIsbn().equalsIgnoreCase(isbn)) {
                    libroEncontrado = l;
                    break;
                }
            }

            if (libroEncontrado != null) {
                listaLibros.remove(libroEncontrado);
                isbnSet.remove(libroEncontrado.getIsbn());

                // Remover del TreeMap
                List<Libro> librosAutor = indicePorAutor.get(libroEncontrado.getAutor());
                if (librosAutor != null) {
                    librosAutor.remove(libroEncontrado);
                    if (librosAutor.isEmpty()) {
                        indicePorAutor.remove(libroEncontrado.getAutor());
                    }
                }

                // Guardar en Stack para deshacer (Bonus)
                historialEliminados.push(libroEncontrado);
                return true;
            }
            return false;
        }

        // BONUS: Restaurar el último libro eliminado
        public boolean deshacerUltimaEliminacion() {
            if (!historialEliminados.isEmpty()) {
                Libro ultimo = historialEliminados.pop();
                return agregarLibro(ultimo);
            }
            return false;
        }

        // Método con retorno ArrayList para filtrar por Autor
        public List<Libro> filtrarPorAutor(String autor) {
            List<Libro> resultado = indicePorAutor.get(autor.trim());
            return (resultado != null) ? new ArrayList<>(resultado) : new ArrayList<>();
        }

        // BONUS Avanzado ArrayList: Filtrar con Stream / Lambda
        public List<Libro> filtrarPorTextoAutor(String texto) {
            List<Libro> filtrados = new ArrayList<>();
            for (Libro l : listaLibros) {
                if (l.getAutor().toLowerCase().contains(texto.toLowerCase())) {
                    filtrados.add(l);
                }
            }
            return filtrados;
        }

        public List<Libro> obtenerTodos() {
            return new ArrayList<>(listaLibros);
        }

        public boolean existeIsbn(String isbn) {
            return isbnSet.contains(isbn);
        }
    }


