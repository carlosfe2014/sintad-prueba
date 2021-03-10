package net.carlosfe.tests.sintad.endpoints.entidades.service;

import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.global.services.IGenericService;

import java.util.List;

public interface IEntidadService extends IGenericService<Entidad, Long> {
    List<Entidad> findByTipoDocumentoId(Long id);
    List<Entidad> findByTipoContribuyenteId(Long id);
}
