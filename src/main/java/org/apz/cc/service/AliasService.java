package org.apz.cc.service;

import org.apz.cc.domain.Alias;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Alias.
 */
public interface AliasService {

    /**
     * Save a alias.
     *
     * @param alias the entity to save
     * @return the persisted entity
     */
    Alias save(Alias alias);

    /**
     * Get all the aliases.
     *
     * @return the list of entities
     */
    List<Alias> findAll();


    /**
     * Get the "id" alias.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Alias> findOne(Long id);

    /**
     * Delete the "id" alias.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
