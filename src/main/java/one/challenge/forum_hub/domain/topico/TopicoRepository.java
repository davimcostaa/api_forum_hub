package one.challenge.forum_hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findAllByCursoId(Long id, Pageable pageable);

    @Query("SELECT COUNT(r) > 0 FROM Resposta r WHERE r.topico.id = :topicoId AND r.solucao = true")
    boolean topicoJaSolucionado(Long topicoId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Topico t WHERE t.id = :topicoId AND t.status = 'concluido'")
    boolean topicoJaFechado(Long topicoId);
}
