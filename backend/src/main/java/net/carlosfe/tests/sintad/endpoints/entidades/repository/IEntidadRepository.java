package net.carlosfe.tests.sintad.endpoints.entidades.repository;

import net.carlosfe.tests.sintad.endpoints.entidades.entity.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface IEntidadRepository extends JpaRepository<Entidad, Long> {
    List<Entidad> findAllByTipoContribuyenteIdAndEstado(Long tipoContribuyenteId, Boolean estado);
    List<Entidad> findAllByTipoDocumentoIdAndEstado(Long tipoDocumentoId, Boolean estado);
    List<Entidad> findAllByEstadoOrderByIdDesc(Boolean estado);
    Optional<Entidad> findFirstByIdAndEstado(Long id, Boolean estado);
}
