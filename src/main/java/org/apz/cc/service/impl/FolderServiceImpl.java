package org.apz.cc.service.impl;

import org.apz.cc.service.FolderService;
import org.apz.cc.domain.Folder;
import org.apz.cc.repository.FolderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Folder.
 */
@Service
@Transactional
public class FolderServiceImpl implements FolderService {

    private final Logger log = LoggerFactory.getLogger(FolderServiceImpl.class);

    private final FolderRepository folderRepository;

    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    /**
     * Save a folder.
     *
     * @param folder the entity to save
     * @return the persisted entity
     */
    @Override
    public Folder save(Folder folder) {
        log.debug("Request to save Folder : {}", folder);
        return folderRepository.save(folder);
    }

    /**
     * Get all the folders.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Folder> findAll() {
        log.debug("Request to get all Folders");
        return folderRepository.findAll();
    }


    /**
     * Get one folder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Folder> findOne(Long id) {
        log.debug("Request to get Folder : {}", id);
        return folderRepository.findById(id);
    }

    /**
     * Delete the folder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Folder : {}", id);
        folderRepository.deleteById(id);
    }
}
