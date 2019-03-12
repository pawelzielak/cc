package org.apz.cc.web.rest;
import org.apz.cc.domain.Folder;
import org.apz.cc.service.FolderService;
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
 * REST controller for managing Folder.
 */
@RestController
@RequestMapping("/api")
public class FolderResource {

    private final Logger log = LoggerFactory.getLogger(FolderResource.class);

    private static final String ENTITY_NAME = "folder";

    private final FolderService folderService;

    public FolderResource(FolderService folderService) {
        this.folderService = folderService;
    }

    /**
     * POST  /folders : Create a new folder.
     *
     * @param folder the folder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new folder, or with status 400 (Bad Request) if the folder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/folders")
    public ResponseEntity<Folder> createFolder(@Valid @RequestBody Folder folder) throws URISyntaxException {
        log.debug("REST request to save Folder : {}", folder);
        if (folder.getId() != null) {
            throw new BadRequestAlertException("A new folder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Folder result = folderService.save(folder);
        return ResponseEntity.created(new URI("/api/folders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /folders : Updates an existing folder.
     *
     * @param folder the folder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated folder,
     * or with status 400 (Bad Request) if the folder is not valid,
     * or with status 500 (Internal Server Error) if the folder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/folders")
    public ResponseEntity<Folder> updateFolder(@Valid @RequestBody Folder folder) throws URISyntaxException {
        log.debug("REST request to update Folder : {}", folder);
        if (folder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Folder result = folderService.save(folder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, folder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /folders : get all the folders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of folders in body
     */
    @GetMapping("/folders")
    public List<Folder> getAllFolders() {
        log.debug("REST request to get all Folders");
        return folderService.findAll();
    }

    /**
     * GET  /folders/:id : get the "id" folder.
     *
     * @param id the id of the folder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the folder, or with status 404 (Not Found)
     */
    @GetMapping("/folders/{id}")
    public ResponseEntity<Folder> getFolder(@PathVariable Long id) {
        log.debug("REST request to get Folder : {}", id);
        Optional<Folder> folder = folderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(folder);
    }

    /**
     * DELETE  /folders/:id : delete the "id" folder.
     *
     * @param id the id of the folder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/folders/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        log.debug("REST request to delete Folder : {}", id);
        folderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
