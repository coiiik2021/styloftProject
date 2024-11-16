package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.HistorySearch;
import org.sale.project.entity.Voucher;
import org.sale.project.service.HistorySearchService;
import org.sale.project.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistorySearchController {

    HistorySearchService historySearchService;

    @GetMapping("/search")
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
