package org.apz.cc.repository;

import org.apz.cc.domain.CCG;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CCG entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCGRepository extends JpaRepository<CCG, Long> {

}
