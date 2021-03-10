package net.carlosfe.tests.sintad.endpoints.tipocontribuyente.entity;


import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.global.validators.groups.Create;
import net.carlosfe.tests.sintad.global.validators.groups.Update;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity(name = "tb_tipo_contribuyente")
public class TipoContribuyente extends RepresentationModel<Entidad> implements Serializable {

    private static final long serialVersionUID = -4727577322039397070L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_contribuyente")
    @Null(groups = {Create.class, Update.class})
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @Null(groups = {Create.class, Update.class})
    private Boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
