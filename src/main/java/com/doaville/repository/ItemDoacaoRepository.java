package com.doaville.repository;

import com.doaville.entity.ItemDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemDoacaoRepository extends JpaRepository<ItemDoacao, Long> {
    Optional<ItemDoacao> findByNome(String nome);
}
