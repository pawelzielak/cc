package org.apz.cc.web.rest;
import org.apz.cc.domain.Alias;
import org.apz.cc.service.AliasService;
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
 * REST controller for managing Alias.
 */
@RestController
@RequestMapping("/api")
public class AliasResource {

    private final Logger log = LoggerFactory.getLogger(AliasResource.class);

    private static final String ENTITY_NAME = "alias";

    private final AliasService aliasService;

    public AliasResource(AliasService aliasService) {
        this.aliasService = aliasService;
    }

    /**
     * POST  /aliases : Create a new alias.
     *
     * @param alias the alias to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alias, or with status 400 (Bad Request) if the alias has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aliases")
    public ResponseEntity<Alias> createAlias(@Valid @RequestBody Alias alias) throws URISyntaxException {
        log.debug("REST request to save Alias : {}", alias);
        if (alias.getId() != null) {
            throw new BadRequestAlertException("A new alias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alias result = aliasService.save(alias);
        return ResponseEntity.created(new URI("/api/aliases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aliases : Updates an existing alias.
     *
     * @param alias the alias to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alias,
     * or with status 400 (Bad Request) if the alias is not valid,
     * or with status 500 (Internal Server Error) if the alias couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aliases")
    public ResponseEntity<Alias> updateAlias(@Valid @RequestBody Alias alias) throws URISyntaxException {
        log.debug("REST request to update Alias : {}", alias);
        if (alias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Alias result = aliasService.save(alias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alias.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aliases : get all the aliases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aliases in body
     */
    @GetMapping("/aliases")
    public List<Alias> getAllAliases() {
        log.debug("REST request to get all Aliases");
        return aliasService.findAll();
    }

    /**
     * GET  /aliases/:id : get the "id" alias.
     *
     * @param id the id of the alias to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alias, or with status 404 (Not Found)
     */
    @GetMapping("/aliases/{id}")
    public ResponseEntity<Alias> getAlias(@PathVariable Long id) {
        log.debug("REST request to get Alias : {}", id);
        Optional<Alias> alias = aliasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alias);
    }

    /**
     * DELETE  /aliases/:id : delete the "id" alias.
     *
     * @param id the id of the alias to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aliases/{id}")
    public ResponseEntity<Void> deleteAlias(@PathVariable Long id) {
        log.debug("REST request to delete Alias : {}", id);
        aliasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
