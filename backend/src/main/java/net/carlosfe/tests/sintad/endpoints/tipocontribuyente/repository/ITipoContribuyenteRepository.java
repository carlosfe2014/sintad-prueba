package net.carlosfe.tests.sintad.endpoints.tipocontribuyente.repository;

import net.carlosfe.tests.sintad.endpoints.tipocontribuyente.entity.TipoContribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITipoContribuyenteRepository extends JpaRepository<TipoContribuyente, Long> {
    List<TipoContribuyente> findAllByEstadoOrderByIdDesc(Boolean estado);
    Optional<TipoContribuyente> findFirstByIdAndEstado(Long id, Boolean estado);
}
