package net.carlosfe.tests.sintad.endpoints.tipodocumento.repository;

import net.carlosfe.tests.sintad.endpoints.tipodocumento.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    List<TipoDocumento> findAllByEstadoOrderByIdDesc(Boolean estado);
    Optional<TipoDocumento> findFirstByIdAndEstado(Long id, Boolean estado);
}
