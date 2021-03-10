package net.carlosfe.tests.sintad.endpoints.tipodocumento.controller;

import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.endpoints.entidades.service.IEntidadService;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.entity.TipoDocumento;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.service.ITipoDocumentoService;
import net.carlosfe.tests.sintad.global.utils.HateoasUtil;
import net.carlosfe.tests.sintad.global.validators.groups.Create;
import net.carlosfe.tests.sintad.global.validators.groups.Update;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.groups.Default;
import java.util.List;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/tipodocumento")
@CrossOrigin(origins = "*")
public class TipoDocumentoController {

    private final ITipoDocumentoService tipoDocumentoService;
    private final IEntidadService entidadService;

    public TipoDocumentoController(final ITipoDocumentoService tipoDocumentoService, final IEntidadService entidadService) {
        this.tipoDocumentoService = tipoDocumentoService;
        this.entidadService = entidadService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<TipoDocumento>> getAll(){

        List<TipoDocumento> tipoDocumentos = this.tipoDocumentoService.findAll();
        tipoDocumentos.forEach(HateoasUtil::proccess);
        Link allDocumentosLink = linkTo(methodOn(TipoDocumentoController.class).getAll()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(tipoDocumentos, allDocumentosLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> getById(@PathVariable Long id){
        TipoDocumento tipoDocumento = this.tipoDocumentoService.findByIdAndEstado(id, true);
        HateoasUtil.proccess(tipoDocumento);
        return ResponseEntity.ok(tipoDocumento);
    }

    @GetMapping("/{id}/entidades")
    public ResponseEntity<CollectionModel<Entidad>> getEntidadesById(@PathVariable Long id){
        List<Entidad> entidades = entidadService.findByTipoDocumentoId(id);
        entidades.forEach(HateoasUtil::proccess);
        Link allEntidadesLink = linkTo(methodOn(TipoDocumentoController.class).getEntidadesById(id)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(entidades, allEntidadesLink));
    }


    @PostMapping
    public ResponseEntity<TipoDocumento> create(@RequestBody @Validated({Default.class, Create.class}) TipoDocumento tipoDocumento){
        TipoDocumento tipoDocumentoNuevo = this.tipoDocumentoService.save(tipoDocumento);
        HateoasUtil.proccess(tipoDocumentoNuevo);
        return ResponseEntity.ok(tipoDocumentoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumento> update(@PathVariable Long id, @RequestBody @Validated({Default.class, Update.class}) TipoDocumento tipoDocumento){
        TipoDocumento tipoDocumentoGuardado = this.tipoDocumentoService.update(id, tipoDocumento);
        HateoasUtil.proccess(tipoDocumentoGuardado);
        return ResponseEntity.ok(tipoDocumentoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.tipoDocumentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
