package mobi.carson.paycel.repository;

import mobi.carson.paycel.domain.SmsRecibido;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsRecibido entity.
 */
public interface SmsRecibidoRepository extends JpaRepository<SmsRecibido,Long> {

}
