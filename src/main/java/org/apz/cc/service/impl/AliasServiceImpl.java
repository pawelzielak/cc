package org.apz.cc.service.impl;

import org.apz.cc.service.AliasService;
import org.apz.cc.domain.Alias;
import org.apz.cc.repository.AliasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Alias.
 */
@Service
@Transactional
public class AliasServiceImpl implements AliasService {

    private final Logger log = LoggerFactory.getLogger(AliasServiceImpl.class);

    private final AliasRepository aliasRepository;

    public AliasServiceImpl(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }

    /**
     * Save a alias.
     *
     * @param alias the entity to save
     * @return the persisted entity
     */
    @Override
    public Alias save(Alias alias) {
        log.debug("Request to save Alias : {}", alias);
        return aliasRepository.save(alias);
    }

    /**
     * Get all the aliases.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Alias> findAll() {
        log.debug("Request to get all Aliases");
        return aliasRepository.findAll();
    }


    /**
     * Get one alias by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Alias> findOne(Long id) {
        log.debug("Request to get Alias : {}", id);
        return aliasRepository.findById(id);
    }

    /**
     * Delete the alias by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alias : {}", id);
        aliasRepository.deleteById(id);
    }
}
