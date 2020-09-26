package com.evoltech.library.model.jpa;

import com.evoltech.library.model.base.BaseJpaEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class Usuario extends BaseJpaEntity<Long> implements Serializable {

    public Usuario(String email, String nombre) {
        this.email= email;
        this.nombre= nombre;
        this.role = "Maestra";
    }

    public Usuario(String email, String nombre, String role){
        this(email, nombre);
        this.role = role;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty @NotBlank @Column(unique = true)
    String email;

    @NotEmpty @NotBlank
    String nombre;

    @NotEmpty
    String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escuela_id")
    private Escuela escuela;

}