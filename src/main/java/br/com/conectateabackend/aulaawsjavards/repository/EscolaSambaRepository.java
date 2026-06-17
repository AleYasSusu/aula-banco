package br.com.conectateabackend.aulaawsjavards.repository;


import br.com.conectateabackend.aulaawsjavards.domain.EscolaSamba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaSambaRepository extends JpaRepository<EscolaSamba, Long> {
}
