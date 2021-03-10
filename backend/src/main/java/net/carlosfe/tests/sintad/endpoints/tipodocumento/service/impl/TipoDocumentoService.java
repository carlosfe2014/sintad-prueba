package net.carlosfe.tests.sintad.endpoints.tipodocumento.service.impl;

import net.carlosfe.tests.sintad.endpoints.tipodocumento.entity.TipoDocumento;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.repository.ITipoDocumentoRepository;
import net.carlosfe.tests.sintad.endpoints.tipodocumento.service.ITipoDocumentoService;
import net.carlosfe.tests.sintad.global.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {

    private final ITipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(final ITipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public TipoDocumento save(TipoDocumento object) {
        object.setEstado(true);
        this.tipoDocumentoRepository.save(object);
        return object;
    }

    @Override
    public TipoDocumento update(Long id, TipoDocumento object) {
        TipoDocumento tipoDocumentoGuardado = findByIdAndEstado(id, true);
        tipoDocumentoGuardado.setNombre(object.getNombre());
        tipoDocumentoGuardado.setCodigo(object.getCodigo());
        tipoDocumentoGuardado.setDescripcion(object.getDescripcion());
        this.tipoDocumentoRepository.save(tipoDocumentoGuardado);
        return tipoDocumentoGuardado;
    }

    @Override
    public void deleteById(Long id) {
        TipoDocumento tipoDocumentoGuardado = findByIdAndEstado(id, true);
        tipoDocumentoGuardado.setEstado(false);
        this.tipoDocumentoRepository.save(tipoDocumentoGuardado);
    }

    @Override
    public TipoDocumento findById(Long id) {
        return this.tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoDocumentoService.findById.tipoDocumentoNotFound"));
    }

    @Override
    public TipoDocumento findByIdAndEstado(Long id, boolean estado) {
        return this.tipoDocumentoRepository.findFirstByIdAndEstado(id, estado)
                .orElseThrow(() -> new ResourceNotFoundException("TipoDocumentoService.findByIdAndEstado.tipoDocumentoNotFound"));
    }

    @Override
    public List<TipoDocumento> findAll() {
        return this.tipoDocumentoRepository.findAllByEstadoOrderByIdDesc(true);
    }
}
