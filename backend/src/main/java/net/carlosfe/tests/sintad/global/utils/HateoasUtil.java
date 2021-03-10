package net.carlosfe.tests.sintad.global.utils;

import net.carlosfe.tests.sintad.endpoints.entidades.controller.EntidadController;
import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.controller.TipoContribuyenteController;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.entity.TipoContribuyente;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.controller.TipoDocumentoController;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.entity.TipoDocumento;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class HateoasUtil {
    public static void proccess(Entidad entidad){
        entidad.add(linkTo(methodOn(EntidadController.class).getById(entidad.getId())).withSelfRel());
        entidad.add(linkTo(methodOn(TipoDocumentoController.class).getById(entidad.getTipoDocumentoId())).withRel("tipoDocumento"));
        if(entidad.getTipoContribuyenteId() != null){
            entidad.add(linkTo(methodOn(TipoContribuyenteController.class).getById(entidad.getTipoContribuyenteId())).withRel("tipoContribuyente"));
        }
    }

    public static void proccess(TipoContribuyente tipoContribuyente){
        tipoContribuyente.add(linkTo(methodOn(TipoContribuyenteController.class).getById(tipoContribuyente.getId())).withSelfRel());
        tipoContribuyente.add(linkTo(methodOn(TipoContribuyenteController.class).getEntidadesById(tipoContribuyente.getId())).withRel("entidades"));
    }

    public static void proccess(TipoDocumento tipoDocumento){
        tipoDocumento.add(linkTo(methodOn(TipoDocumentoController.class).getById(tipoDocumento.getId())).withSelfRel());
        tipoDocumento.add(linkTo(methodOn(TipoDocumentoController.class).getEntidadesById(tipoDocumento.getId())).withRel("entidades"));
    }
}
