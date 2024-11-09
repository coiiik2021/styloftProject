package org.sale.project.repository;


import org.sale.project.entity.HistorySearch;
import org.sale.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorySearchRepository extends JpaRepository<HistorySearch, String> {
    Optional<HistorySearch> findByTitle(String title);
    List<HistorySearch> findByUserId(String userId);
}
