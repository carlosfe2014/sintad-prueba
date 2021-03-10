package net.carlosfe.tests.sintad.endpoints.entidades.entity;

import net.carlosfe.tests.sintad.endpoints.entidades.validators.groups.EntidadGroup;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity(name = "tb_entidad")
public class Entidad extends RepresentationModel<Entidad> implements Serializable {
    private static final long serialVersionUID = 8969743127879413332L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entidad")
    @Null(groups = {EntidadGroup.class})
    private Long id;

    @NotNull(groups = {EntidadGroup.class})
    @Column(name = "id_tipo_documento")
    private Long tipoDocumentoId;

    @NotBlank(groups = {EntidadGroup.class})
    @Size(max = 25, groups = {EntidadGroup.class})
    @Column(name = "nro_documento")
    private String numeroDocumento;

    @NotBlank(groups = {EntidadGroup.class})
    @Size(max = 100, groups = {EntidadGroup.class})
    @Column(name = "razon_social")
    private String razonSocial;

    @Size(max = 100, groups = {EntidadGroup.class})
    @Column(name = "nombre_comercial")
    private String nombreComercial;


    @Column(name = "id_tipo_contribuyente")
    private Long tipoContribuyenteId;

    @Size(max = 250, groups = {EntidadGroup.class})
    private String direccion;

    @Size(max = 50, groups = {EntidadGroup.class})
    private String telefono;

    @Null(groups = {EntidadGroup.class})
    private Boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumento(Long tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Long getTipoContribuyenteId() {
        return tipoContribuyenteId;
    }

    public void setTipoContribuyente(Long tipoContribuyenteId) {
        this.tipoContribuyenteId = tipoContribuyenteId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
