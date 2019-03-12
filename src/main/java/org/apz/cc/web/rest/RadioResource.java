package org.apz.cc.web.rest;
import org.apz.cc.domain.Radio;
import org.apz.cc.service.RadioService;
import org.apz.cc.web.rest.errors.BadRequestAlertException;
import org.apz.cc.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Radio.
 */
@RestController
@RequestMapping("/api")
public class RadioResource {

    private final Logger log = LoggerFactory.getLogger(RadioResource.class);

    private static final String ENTITY_NAME = "radio";

    private final RadioService radioService;

    public RadioResource(RadioService radioService) {
        this.radioService = radioService;
    }

    /**
     * POST  /radios : Create a new radio.
     *
     * @param radio the radio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new radio, or with status 400 (Bad Request) if the radio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/radios")
    public ResponseEntity<Radio> createRadio(@Valid @RequestBody Radio radio) throws URISyntaxException {
        log.debug("REST request to save Radio : {}", radio);
        if (radio.getId() != null) {
            throw new BadRequestAlertException("A new radio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Radio result = radioService.save(radio);
        return ResponseEntity.created(new URI("/api/radios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /radios : Updates an existing radio.
     *
     * @param radio the radio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated radio,
     * or with status 400 (Bad Request) if the radio is not valid,
     * or with status 500 (Internal Server Error) if the radio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/radios")
    public ResponseEntity<Radio> updateRadio(@Valid @RequestBody Radio radio) throws URISyntaxException {
        log.debug("REST request to update Radio : {}", radio);
        if (radio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Radio result = radioService.save(radio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, radio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /radios : get all the radios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of radios in body
     */
    @GetMapping("/radios")
    public List<Radio> getAllRadios() {
        log.debug("REST request to get all Radios");
        return radioService.findAll();
    }

    /**
     * GET  /radios/:id : get the "id" radio.
     *
     * @param id the id of the radio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the radio, or with status 404 (Not Found)
     */
    @GetMapping("/radios/{id}")
    public ResponseEntity<Radio> getRadio(@PathVariable Long id) {
        log.debug("REST request to get Radio : {}", id);
        Optional<Radio> radio = radioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(radio);
    }

    /**
     * DELETE  /radios/:id : delete the "id" radio.
     *
     * @param id the id of the radio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/radios/{id}")
    public ResponseEntity<Void> deleteRadio(@PathVariable Long id) {
        log.debug("REST request to delete Radio : {}", id);
        radioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
