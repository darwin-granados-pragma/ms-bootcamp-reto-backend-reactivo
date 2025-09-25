package co.com.bootcamp.r2dbc.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "bootcamp")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BootcampEntity implements Persistable<String> {

  @Id
  @Column("id_bootcamp")
  private String id;

  @Column("nombre")
  private String name;

  @Column("descripcion")
  private String description;

  @Column("fecha_lanzamiento")
  private LocalDate releaseDate;

  @Column("duracion")
  private Integer duration;

  @Transient
  private boolean isNew;

  @Override
  public boolean isNew() {
    return this.isNew || this.id == null;
  }
}
