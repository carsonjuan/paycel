package mobi.carson.paycel.web.rest;

import com.codahale.metrics.annotation.Timed;
import mobi.carson.paycel.domain.SmsRecibido;
import mobi.carson.paycel.repository.SmsRecibidoRepository;
import mobi.carson.paycel.web.rest.util.HeaderUtil;
import mobi.carson.paycel.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SmsRecibido.
 */
@RestController
@RequestMapping("/api")
public class SmsRecibidoResource {

    private final Logger log = LoggerFactory.getLogger(SmsRecibidoResource.class);
        
    @Inject
    private SmsRecibidoRepository smsRecibidoRepository;
    
    /**
     * POST  /sms-recibidos : Create a new smsRecibido.
     *
     * @param smsRecibido the smsRecibido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smsRecibido, or with status 400 (Bad Request) if the smsRecibido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sms-recibidos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsRecibido> createSmsRecibido(@RequestBody SmsRecibido smsRecibido) throws URISyntaxException {
        log.debug("REST request to save SmsRecibido : {}", smsRecibido);
        if (smsRecibido.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsRecibido", "idexists", "A new smsRecibido cannot already have an ID")).body(null);
        }
        SmsRecibido result = smsRecibidoRepository.save(smsRecibido);
        return ResponseEntity.created(new URI("/api/sms-recibidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsRecibido", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms-recibidos : Updates an existing smsRecibido.
     *
     * @param smsRecibido the smsRecibido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smsRecibido,
     * or with status 400 (Bad Request) if the smsRecibido is not valid,
     * or with status 500 (Internal Server Error) if the smsRecibido couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sms-recibidos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsRecibido> updateSmsRecibido(@RequestBody SmsRecibido smsRecibido) throws URISyntaxException {
        log.debug("REST request to update SmsRecibido : {}", smsRecibido);
        if (smsRecibido.getId() == null) {
            return createSmsRecibido(smsRecibido);
        }
        SmsRecibido result = smsRecibidoRepository.save(smsRecibido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsRecibido", smsRecibido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms-recibidos : get all the smsRecibidos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of smsRecibidos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/sms-recibidos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsRecibido>> getAllSmsRecibidos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsRecibidos");
        Page<SmsRecibido> page = smsRecibidoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sms-recibidos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sms-recibidos/:id : get the "id" smsRecibido.
     *
     * @param id the id of the smsRecibido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsRecibido, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sms-recibidos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsRecibido> getSmsRecibido(@PathVariable Long id) {
        log.debug("REST request to get SmsRecibido : {}", id);
        SmsRecibido smsRecibido = smsRecibidoRepository.findOne(id);
        return Optional.ofNullable(smsRecibido)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sms-recibidos/:id : delete the "id" smsRecibido.
     *
     * @param id the id of the smsRecibido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sms-recibidos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsRecibido(@PathVariable Long id) {
        log.debug("REST request to delete SmsRecibido : {}", id);
        smsRecibidoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsRecibido", id.toString())).build();
    }

}
