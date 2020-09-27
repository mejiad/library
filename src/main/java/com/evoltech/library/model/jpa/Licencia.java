package com.evoltech.library.model.jpa;

import com.evoltech.library.model.base.BaseJpaEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class Licencia extends BaseJpaEntity<Long> implements Serializable {

    public Licencia(String nombre, Coleccion coleccion){
        this.nombre = nombre;
        this.isEnable = true;
        this.setColeccion(coleccion);
    }

    public Licencia(String nombre, Coleccion coleccion, boolean isEnable){
        this.nombre = nombre;
        this.setColeccion(coleccion);
        this.isEnable = isEnable;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty @NotBlank
    private String nombre;
    private int asientos;

    private boolean isEnable;

    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escuela_id")
    private Escuela escuela;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coleccion_id")
    private Coleccion coleccion;

}
