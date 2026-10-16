package br.com.gabrielflorentino.validador_boleto.repository;

import br.com.gabrielflorentino.validador_boleto.entity.BoletoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoRepository extends CrudRepository<BoletoEntity, Long> {

}
