package net.carlosfe.tests.sintad.endpoints.entidades.service.impl;

import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import net.carlosfe.tests.sintad.endpoints.entidades.repository.IEntidadRepository;
import net.carlosfe.tests.sintad.endpoints.entidades.service.IEntidadService;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.repository.ITipoContribuyenteRepository;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.repository.ITipoDocumentoRepository;
import net.carlosfe.tests.sintad.global.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntidadService implements IEntidadService {

    private final IEntidadRepository entidadRepository;
    private final ITipoDocumentoRepository tipoDocumentoRepository;
    private final ITipoContribuyenteRepository tipoContribuyenteRepository;

    public EntidadService(final IEntidadRepository entidadRepository, final ITipoDocumentoRepository tipoDocumentoRepository, final ITipoContribuyenteRepository tipoContribuyenteRepository) {
        this.entidadRepository = entidadRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.tipoContribuyenteRepository = tipoContribuyenteRepository;
    }

    @Override
    @Transactional
    public Entidad save(Entidad object) {
        this.tipoDocumentoRepository.findFirstByIdAndEstado(object.getTipoDocumentoId(), true)
                .orElseThrow(() -> new ResourceNotFoundException("EntidadService.save.tipoDocumentoNotFound"));
        if(object.getTipoContribuyenteId() != null){
            this.tipoContribuyenteRepository.findFirstByIdAndEstado(object.getTipoContribuyenteId(), true)
                    .orElseThrow(() -> new ResourceNotFoundException("EntidadService.save.tipoContribuyenteNotFound"));
        }
        object.setEstado(true);
        this.entidadRepository.save(object);
        return object;
    }

    @Override
    @Transactional
    public Entidad update(Long id, Entidad object) {
        Entidad entidadGuardada = findByIdAndEstado(id, true);

        this.tipoDocumentoRepository.findFirstByIdAndEstado(object.getTipoDocumentoId(), true)
                .orElseThrow(() -> new ResourceNotFoundException("EntidadService.update.tipoDocumentoNotFound"));

        if(object.getTipoContribuyenteId() != null){
            this.tipoContribuyenteRepository.findFirstByIdAndEstado(object.getTipoContribuyenteId(), true)
                    .orElseThrow(() -> new ResourceNotFoundException("EntidadService.update.tipoContribuyenteNotFound"));
        }

        entidadGuardada.setTipoDocumento(object.getTipoDocumentoId());
        entidadGuardada.setNumeroDocumento(object.getNumeroDocumento());
        entidadGuardada.setRazonSocial(object.getRazonSocial());
        entidadGuardada.setNombreComercial(object.getNombreComercial());
        entidadGuardada.setTipoContribuyente(object.getTipoContribuyenteId());
        entidadGuardada.setDireccion(object.getDireccion());
        entidadGuardada.setTelefono(object.getTelefono());
        this.entidadRepository.save(entidadGuardada);
        return entidadGuardada;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Entidad entidad = findByIdAndEstado(id, true);
        entidad.setEstado(false);
        this.entidadRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Entidad findById(Long id) {
        return this.entidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EntidadService.findById.entidadNotFound"));
    }

    @Override
    public Entidad findByIdAndEstado(Long id, boolean estado) {
        return this.entidadRepository.findFirstByIdAndEstado(id, estado)
                .orElseThrow(() -> new ResourceNotFoundException("EntidadService.findByIdAndEstado.entidadNotFound"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entidad> findAll() {
        return this.entidadRepository.findAllByEstadoOrderByIdDesc(true);
    }

    @Override
    public List<Entidad> findByTipoDocumentoId(Long id) {
        return this.entidadRepository.findAllByTipoDocumentoIdAndEstado(id, true);
    }

    @Override
    public List<Entidad> findByTipoContribuyenteId(Long id) {
        return this.entidadRepository.findAllByTipoContribuyenteIdAndEstado(id, true);
    }
}
