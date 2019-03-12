package org.apz.cc.web.rest;
import org.apz.cc.domain.CCG;
import org.apz.cc.service.CCGService;
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
 * REST controller for managing CCG.
 */
@RestController
@RequestMapping("/api")
public class CCGResource {

    private final Logger log = LoggerFactory.getLogger(CCGResource.class);

    private static final String ENTITY_NAME = "cCG";

    private final CCGService cCGService;

    public CCGResource(CCGService cCGService) {
        this.cCGService = cCGService;
    }

    /**
     * POST  /ccgs : Create a new cCG.
     *
     * @param cCG the cCG to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cCG, or with status 400 (Bad Request) if the cCG has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ccgs")
    public ResponseEntity<CCG> createCCG(@Valid @RequestBody CCG cCG) throws URISyntaxException {
        log.debug("REST request to save CCG : {}", cCG);
        if (cCG.getId() != null) {
            throw new BadRequestAlertException("A new cCG cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCG result = cCGService.save(cCG);
        return ResponseEntity.created(new URI("/api/ccgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ccgs : Updates an existing cCG.
     *
     * @param cCG the cCG to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cCG,
     * or with status 400 (Bad Request) if the cCG is not valid,
     * or with status 500 (Internal Server Error) if the cCG couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ccgs")
    public ResponseEntity<CCG> updateCCG(@Valid @RequestBody CCG cCG) throws URISyntaxException {
        log.debug("REST request to update CCG : {}", cCG);
        if (cCG.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCG result = cCGService.save(cCG);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cCG.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ccgs : get all the cCGS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cCGS in body
     */
    @GetMapping("/ccgs")
    public List<CCG> getAllCCGS() {
        log.debug("REST request to get all CCGS");
        return cCGService.findAll();
    }

    /**
     * GET  /ccgs/:id : get the "id" cCG.
     *
     * @param id the id of the cCG to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cCG, or with status 404 (Not Found)
     */
    @GetMapping("/ccgs/{id}")
    public ResponseEntity<CCG> getCCG(@PathVariable Long id) {
        log.debug("REST request to get CCG : {}", id);
        Optional<CCG> cCG = cCGService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCG);
    }

    /**
     * DELETE  /ccgs/:id : delete the "id" cCG.
     *
     * @param id the id of the cCG to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ccgs/{id}")
    public ResponseEntity<Void> deleteCCG(@PathVariable Long id) {
        log.debug("REST request to delete CCG : {}", id);
        cCGService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
