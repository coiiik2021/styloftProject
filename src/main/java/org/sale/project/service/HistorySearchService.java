package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.HistorySearch;
import org.sale.project.repository.HistorySearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistorySearchService {
    HistorySearchRepository historySearchRepository;

    public void saveHistorySearch(HistorySearch historySearch) {
        Optional<HistorySearch> optionalHistorySearchRepository = historySearchRepository.findByTitle(historySearch.getTitle());

        optionalHistorySearchRepository.ifPresent(search -> historySearch.setId(search.getId()));
        historySearchRepository.save(historySearch);
    }

    public List<HistorySearch> findAllByUserId(String userId) {
           return historySearchRepository.findByUserId(userId);
    }
}
