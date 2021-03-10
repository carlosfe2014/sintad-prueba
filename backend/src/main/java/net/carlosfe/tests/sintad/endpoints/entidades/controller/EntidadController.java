package net.carlosfe.tests.sintad.endpoints.entidades.controller;

import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.endpoints.entidades.service.IEntidadService;
import net.carlosfe.tests.sintad.endpoints.entidades.validators.groups.EntidadGroup;
import net.carlosfe.tests.sintad.global.utils.HateoasUtil;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/entidades")
@CrossOrigin(origins = "*")
public class EntidadController {

    private final IEntidadService entidadService;

    public EntidadController(final IEntidadService entidadService) {
        this.entidadService = entidadService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Entidad>> getAll(){
        List<Entidad> entidades = this.entidadService.findAll();
        entidades.forEach(HateoasUtil::proccess);
        Link allEntiadesLink = linkTo(methodOn(EntidadController.class).getAll()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(entidades, allEntiadesLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entidad> getById(@PathVariable Long id){
        Entidad entidad = this.entidadService.findByIdAndEstado(id, true);
        HateoasUtil.proccess(entidad);
        return ResponseEntity.ok(entidad);
    }

    @PostMapping
    public ResponseEntity<Entidad> create(@RequestBody @Validated({EntidadGroup.class}) Entidad entidad){
        Entidad nuevaEntidad = this.entidadService.save(entidad);
        HateoasUtil.proccess(nuevaEntidad);
        return ResponseEntity.ok(nuevaEntidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entidad> update(@PathVariable Long id, @RequestBody @Validated({EntidadGroup.class}) Entidad entidad){
        Entidad nuevaGuardada = this.entidadService.update(id, entidad);
        HateoasUtil.proccess(nuevaGuardada);
        return ResponseEntity.ok(nuevaGuardada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.entidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
