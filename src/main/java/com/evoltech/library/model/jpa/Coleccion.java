package com.evoltech.library.model.jpa;

import com.evoltech.library.model.base.BaseJpaEntity;
import lombok.*;

import javax.persistence.*;
import javax.print.Doc;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Entity
public class Coleccion extends BaseJpaEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String nivel;
    @NotEmpty
    private String edicion;

    @Transient
    private boolean isEnable = false;

    @OneToMany
    private List<Documento> documentos = new ArrayList<>();

    public Coleccion(String nombre, String nivel, String edicion){
        this.nombre = nombre;
        this. nivel = nivel;
        this. edicion = edicion;
    }

    public void addDocumento(Documento documento){
        this.documentos.add(documento);
    }

    public void removeDocumento(Documento documento){
        this.documentos.remove(documento);
    }

    public void removeDocumentos(){
        Iterator<Documento> iterator = this.documentos.iterator();

        while(iterator.hasNext()){
            Documento documento = iterator.next();
            iterator.remove();
        }
    }

}