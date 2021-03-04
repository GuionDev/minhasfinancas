package com.anselmoguion.minhasfinancas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.anselmoguion.minhasfinancas.model.entity.Lancamento;
import com.anselmoguion.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar( Lancamento lancamentofiltro);
	
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    
    void validar(Lancamento lancamento);
    
    Optional<Lancamento> obterPorId(Long id);
    
    BigDecimal obterSaldoPorUsuario(Long id);

}
