package net.carlosfe.tests.sintad.endpoints.tipocontribuyente.controller;

import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.endpoints.entidades.service.IEntidadService;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.entity.TipoContribuyente;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.service.ITipoContribuyenteService;
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
@RequestMapping("/tipocontribuyente")
@CrossOrigin(origins = "*")
public class TipoContribuyenteController {

    private final ITipoContribuyenteService tipoContribuyenteService;
    private final IEntidadService entidadService;

    public TipoContribuyenteController(final ITipoContribuyenteService tipoContribuyenteService, final IEntidadService entidadService) {
        this.tipoContribuyenteService = tipoContribuyenteService;
        this.entidadService = entidadService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<TipoContribuyente>> getAll(){
        List<TipoContribuyente> tipoContribuyentes = this.tipoContribuyenteService.findAll();
        tipoContribuyentes.forEach(HateoasUtil::proccess);
        Link allContribuyentesLink = linkTo(methodOn(TipoContribuyenteController.class).getAll()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(tipoContribuyentes, allContribuyentesLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoContribuyente> getById(@PathVariable Long id){
        TipoContribuyente tipoContribuyente = this.tipoContribuyenteService.findByIdAndEstado(id, true);
        HateoasUtil.proccess(tipoContribuyente);
        return ResponseEntity.ok(tipoContribuyente);
    }


    @GetMapping("/{id}/entidades")
    public ResponseEntity<CollectionModel<Entidad>> getEntidadesById(@PathVariable Long id){
        List<Entidad> entidades = entidadService.findByTipoContribuyenteId(id);
        entidades.forEach(HateoasUtil::proccess);
        Link allEntidadesLink = linkTo(methodOn(TipoContribuyenteController.class).getEntidadesById(id)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(entidades, allEntidadesLink));
    }


    @PostMapping
    public ResponseEntity<TipoContribuyente> create(@RequestBody @Validated({Default.class, Create.class}) TipoContribuyente tipoContribuyente){
        TipoContribuyente tipoContribuyenteNuevo = this.tipoContribuyenteService.save(tipoContribuyente);
        HateoasUtil.proccess(tipoContribuyenteNuevo);
        return ResponseEntity.ok(tipoContribuyenteNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoContribuyente> update(@PathVariable Long id, @RequestBody @Validated({Default.class, Update.class}) TipoContribuyente tipoContribuyente){
        TipoContribuyente tipoContribuyenteGuardado = this.tipoContribuyenteService.update(id, tipoContribuyente);
        HateoasUtil.proccess(tipoContribuyenteGuardado);
        return ResponseEntity.ok(tipoContribuyenteGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.tipoContribuyenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
