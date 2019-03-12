package org.apz.cc.service;

import org.apz.cc.domain.CCG;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CCG.
 */
public interface CCGService {

    /**
     * Save a cCG.
     *
     * @param cCG the entity to save
     * @return the persisted entity
     */
    CCG save(CCG cCG);

    /**
     * Get all the cCGS.
     *
     * @return the list of entities
     */
    List<CCG> findAll();


    /**
     * Get the "id" cCG.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CCG> findOne(Long id);

    /**
     * Delete the "id" cCG.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
