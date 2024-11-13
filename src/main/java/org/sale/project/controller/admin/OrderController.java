package org.sale.project.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.Order;
import org.sale.project.entity.User;
import org.sale.project.service.OrderDetailService;
import org.sale.project.service.OrderService;
import org.sale.project.service.email.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    OrderDetailService orderDetailService;
    EmailService emailService;


    @GetMapping
    public String getPageOrder(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Order> pOrder = orderService.findAll(pageable);
        List<Order> orders = pOrder.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pOrder.getTotalPages());
        return "/admin/order/show";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdate(@PathVariable("id") String id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "/admin/order/update";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") Order order, HttpServletRequest request) {



        Order oldOrder = orderService.findById(order.getId());
        User user = oldOrder.getUser();


        oldOrder.setStatus(order.getStatus());
        String content = "<p> Status:  <strong>" + oldOrder.getStatus() + "</strong></p>" ;
        if(order.getStatus().equals("SHIPPING")){
            content += "</br>  <p> Đơn hàng của bạn đang trong quá trình vận chuyển </p>";
        } else{
            content += "</br>  <p> Đơn hàng của bạn đã thành công </p>";


        }

        emailService.sendEmail(
                SendEmailRequest
                        .builder()
                        .subject("Status Order #" + order.getId().substring(0,5))
                        .htmlContent(content)
                        .to(
                                Recipient
                                        .builder()
                                        .name(user.getName())
                                        .email(user.getAccount().getEmail())
                                        .build()
                        )
                        .build()
        );

        orderService.saveOrder(oldOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/detail/{id}")
    public String getPageDetail(@PathVariable("id") String id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("details", orderDetailService.findAllOrderDetailByOrderId(id));
        return "/admin/order/detail";
    }

    @GetMapping("/search")
    public String findAllOrderByStartId(@RequestParam("query") String id, Model model) {

        if(id.isEmpty()){
            return "redirect:/admin/order";
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> pOrder = orderService.findAllByStartId(id,pageable);
        List<Order> orders = pOrder.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", pOrder.getTotalPages());
        model.addAttribute("startID", id);

        return "/admin/order/show";

    }





}
