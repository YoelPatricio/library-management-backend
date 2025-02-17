-- Tabla Autor
CREATE TABLE Autor (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(255) NOT NULL,
    nacionalidad VARCHAR2(255),
    fecha_nacimiento DATE
);

COMMENT ON TABLE Autor IS 'Tabla que almacena información sobre los autores';
COMMENT ON COLUMN Autor.id IS 'ID del autor';
COMMENT ON COLUMN Autor.nombre IS 'Nombre del autor';
COMMENT ON COLUMN Autor.nacionalidad IS 'Nacionalidad del autor';
COMMENT ON COLUMN Autor.fecha_nacimiento IS 'Fecha de nacimiento del autor';

-- Tabla Libro
CREATE TABLE Libro (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    titulo VARCHAR2(255) NOT NULL,
    autor_id NUMBER NOT NULL,
    isbn VARCHAR2(20),
    fecha_publicacion DATE,
    estado VARCHAR2(50),
    CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES Autor(id)
);

COMMENT ON TABLE Libro IS 'Tabla que almacena información sobre los libros';
COMMENT ON COLUMN Libro.id IS 'ID del libro';
COMMENT ON COLUMN Libro.titulo IS 'Título del libro';
COMMENT ON COLUMN Libro.autor_id IS 'ID del autor del libro';
COMMENT ON COLUMN Libro.isbn IS 'ISBN del libro';
COMMENT ON COLUMN Libro.fecha_publicacion IS 'Fecha de publicación del libro';
COMMENT ON COLUMN Libro.estado IS 'Estado del libro';

-- Tabla Prestamo
CREATE TABLE Prestamo (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    libro_id NUMBER NOT NULL,
    lector VARCHAR2(255) NOT NULL,
    fecha_prestamo DATE,
    fecha_devolucion DATE,
    estado VARCHAR2(50),
    CONSTRAINT fk_libro FOREIGN KEY (libro_id) REFERENCES Libro(id)
);

COMMENT ON TABLE Prestamo IS 'Tabla que almacena información sobre los préstamos';
COMMENT ON COLUMN Prestamo.id IS 'ID del préstamo';
COMMENT ON COLUMN Prestamo.libro_id IS 'ID del libro prestado';
COMMENT ON COLUMN Prestamo.lector IS 'Nombre del lector';
COMMENT ON COLUMN Prestamo.fecha_prestamo IS 'Fecha del préstamo';
COMMENT ON COLUMN Prestamo.fecha_devolucion IS 'Fecha de devolución del préstamo';
COMMENT ON COLUMN Prestamo.estado IS 'Estado del préstamo';