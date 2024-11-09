package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.HistorySearch;
import org.sale.project.service.HistorySearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistorySearchController {

    HistorySearchService historySearchService;

    @GetMapping
    public List<String> getAllSearchByUser(HttpServletRequest request) {

        HttpSession session = request.getSession();

        String userid = (String) session.getAttribute("id");
        List<HistorySearch> ls = historySearchService.findAllByUserId(userid);
        List<String> list = new ArrayList<>();
        for (HistorySearch hs : ls) {
            list.add(hs.getTitle());
        }

        return list;


    }
}
