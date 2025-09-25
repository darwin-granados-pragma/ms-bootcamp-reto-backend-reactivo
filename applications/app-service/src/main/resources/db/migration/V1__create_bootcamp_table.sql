CREATE TABLE bootcamp (
  id_bootcamp VARCHAR(50) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(90) NOT NULL,
  fecha_lanzamiento Date NOT NULL,
  duracion INT NOT NULL
);
