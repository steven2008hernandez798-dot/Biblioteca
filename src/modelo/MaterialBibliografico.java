 package modelo;

    public abstract class MaterialBibliografico {

        protected String titulo;
        protected String autor;
        protected String isbn;
        protected String genero;
        protected int anioPublicacion;
        protected int copiasDisponibles;

        public MaterialBibliografico(String titulo, String autor, String isbn,
                                     String genero, int anioPublicacion,
                                     int copiasDisponibles) {
            this.titulo = titulo;
            this.autor = autor;
            this.isbn = isbn;
            this.genero = genero;
            this.anioPublicacion = anioPublicacion;
            this.copiasDisponibles = copiasDisponibles;
        }

        // Método abstracto para aplicar Polimorfismo
        public abstract String getTipoMaterial();

        // Getters y Setters
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }

        public String getAutor() { return autor; }
        public void setAutor(String autor) { this.autor = autor; }

        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }

        public String getGenero() { return genero; }
        public void setGenero(String genero) { this.genero = genero; }

        public int getAnioPublicacion() { return anioPublicacion; }
        public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

        public int getCopiasDisponibles() { return copiasDisponibles; }
        public void setCopiasDisponibles(int copiasDisponibles) { this.copiasDisponibles = copiasDisponibles; }
    }

