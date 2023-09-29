package br.com.athenas.desafio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.athenas.desafio.models.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Modifying
    @Query("UPDATE Pessoa p SET p.peso = :peso WHERE p.id = :id")
    void updatedPeso(
            @Param("id") Long id,
            @Param("peso") Double peso);

    @Query("SELECT p FROM Pessoa p WHERE p.nome LIKE CONCAT('%', :nome, '%')")
    Page<Pessoa> findPersonsByName(
            Pageable pageable,
            @Param("nome") String nome);
}
