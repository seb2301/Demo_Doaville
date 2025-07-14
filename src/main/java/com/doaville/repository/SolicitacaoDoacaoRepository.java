package com.doaville.repository;

import com.doaville.entity.SolicitacaoDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitacaoDoacaoRepository extends JpaRepository<SolicitacaoDoacao, Long> {
    List<SolicitacaoDoacao> findByItemDoacao_Id(Long itemId);
}
