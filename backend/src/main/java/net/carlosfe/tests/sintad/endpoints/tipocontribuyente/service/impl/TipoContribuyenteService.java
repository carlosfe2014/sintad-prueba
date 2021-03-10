package net.carlosfe.tests.sintad.endpoints.tipocontribuyente.service.impl;

import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.entity.TipoContribuyente;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.repository.ITipoContribuyenteRepository;
import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.service.ITipoContribuyenteService;
import net.carlosfe.tests.sintad.global.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoContribuyenteService implements ITipoContribuyenteService {

    final ITipoContribuyenteRepository tipoContribuyenteRepository;

    public TipoContribuyenteService(final ITipoContribuyenteRepository tipoContribuyenteRepository) {
        this.tipoContribuyenteRepository = tipoContribuyenteRepository;
    }

    @Override
    @Transactional
    public TipoContribuyente save(TipoContribuyente object) {
        object.setEstado(true);
        this.tipoContribuyenteRepository.save(object);
        return object;
    }

    @Override
    @Transactional
    public TipoContribuyente update(Long id, TipoContribuyente object) {
        TipoContribuyente tipoContribuyenteGuardado = findByIdAndEstado(id, true);
        tipoContribuyenteGuardado.setNombre(object.getNombre());
        this.tipoContribuyenteRepository.save(tipoContribuyenteGuardado);
        return tipoContribuyenteGuardado;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        TipoContribuyente tipoContribuyenteGuardado = findByIdAndEstado(id, true);
        tipoContribuyenteGuardado.setEstado(false);
        this.tipoContribuyenteRepository.save(tipoContribuyenteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoContribuyente findById(Long id) {
        return this.tipoContribuyenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoContribuyenteService.findById.tipoContribuyenteNotFound"));
    }

    @Override
    public TipoContribuyente findByIdAndEstado(Long id, boolean estado) {
        return this.tipoContribuyenteRepository.findFirstByIdAndEstado(id, estado)
                .orElseThrow(() -> new ResourceNotFoundException("TipoContribuyenteService.findByIdAndEstado.tipoContribuyenteNotFound"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoContribuyente> findAll() {
        return this.tipoContribuyenteRepository.findAllByEstadoOrderByIdDesc(true);
    }
}
