package br.com.gabrielflorentino.api_boleto.repository;

import br.com.gabrielflorentino.api_boleto.entity.BoletoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoletoRepository extends CrudRepository<BoletoEntity, Long> {

    Optional<BoletoEntity> findByCodigoBarras(String codigoBarras);

}
