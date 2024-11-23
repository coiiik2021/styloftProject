package org.sale.project.controller.admin;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.dto.request.Recipient;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.entity.Order;
import org.sale.project.entity.User;
import org.sale.project.enums.StatusOrder;
import org.sale.project.service.OrderDetailService;
import org.sale.project.service.OrderService;
import org.sale.project.service.SmsService;
import org.sale.project.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//    SmsService smsService;





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
    public String getPageUpdate(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        Order order = orderService.findById(id);
        if(order != null){
            HttpSession session = request.getSession();
            session.setAttribute("totalAnnounce", Math.max((int)session.getAttribute("totalAnnounce") - 1, 0));
            order.setAnnounceOrder(false);

            orderService.saveOrder(order);
        }
        model.addAttribute("order", order != null ? order : new Order());


        return "/admin/order/update";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") Order order, HttpServletRequest request) throws MessagingException {

        Order oldOrder = orderService.findById(order.getId());
        User user = oldOrder.getUser();


        oldOrder.setStatus((StatusOrder) order.getStatus());
        String content = "<p> Status:  <strong>" + oldOrder.getStatus() + "</strong></p>" ;
        if(order.getStatus().name().equals("SHIPPING")){
            content += "</br>  <p> Đơn hàng của bạn đang trong quá trình vận chuyển </p>";
        } else{
            content += "</br>  <p> Đơn hàng của bạn đã thành công </p>";


        }

//        emailService.sendEmail(
//                SendEmailRequest
//                        .builder()
//                        .subject("Status Order #" + order.getId().substring(0,5))
//                        .htmlContent(content)
//                        .to(
//                                Recipient
//                                        .builder()
//                                        .name(user.getName())
//                                        .email(user.getAccount().getEmail())
//                                        .build()
//                        )
//                        .build()
//        );
        System.out.println(order.getStatus().name());
        if(order.getStatus().equals(StatusOrder.COMPLETED)) {
            String emailContent = "<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                    "<h2 style='color: #ff6600;'>Đơn hàng của bạn đã hoàn tất</h2>" +
                    "<p>StyloftCloth</p>" +
                    "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong>" + user.getName() + "</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
                    "<p style='color: #333;'>Chúc <strong>bạn</strong> có thời gian mua sắm vui vẻ!!!</p>" +
                    "<p style='color: #333;'>Nếu có thắc mắc gì mong <strong>bạn</strong> phản hồi lại sớm cho bên chúng tôi.</p>" +
                    "<h3 style='color: #28a745;'>Thông báo: Đơn hàng của bạn đã được hoàn thành!</h3>" +
                    "<p style='color: #333;'>Cảm ơn bạn đã tin tưởng và mua sắm tại StyloftCloth. Đơn hàng của bạn với mã <strong>" + oldOrder.getId().substring(0, 5) + "</strong> đã được hoàn thành.</p>" +
                    "<p style='color: #333;'>Chúng tôi hy vọng bạn hài lòng với sản phẩm của mình. Nếu có bất kỳ vấn đề gì, đừng ngần ngại liên hệ với chúng tôi qua email hoặc hotline để được hỗ trợ kịp thời.</p>" +
                    "<a href='http://localhost:8080/orders' style='color: #0066cc;'>Xem chi tiết đơn hàng</a>" +
                    "<br><br><p>Best regards,<br>AnhDungShop</p></div></body></html>";
            emailService.sendHtmlEmail(user.getAccount().getEmail(),"Đơn hàng #" + oldOrder.getId().substring(0, 5)+" đã hoàn thành",emailContent);
        } if (order.getStatus().equals(StatusOrder.RETURNED)) {
            String emailContent = "<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                    "<h2 style='color: #ff6600;'>Đơn hàng của bạn đã được xác nhận hoàn trả</h2>" +
                    "<p>StyloftCloth</p>" +
                    "<p style='color: #333;'>Xin chào <strong>" + user.getName() + "</strong>,</p>" +
                    "<p style='color: #333;'>Đơn hàng của bạn với mã <strong>" + oldOrder.getId().substring(0, 5) + "</strong> đã được chúng tôi xác nhận hoàn trả.</p>" +
                    "<p style='color: #333;'>Chúng tôi rất tiếc vì sản phẩm không đáp ứng được mong đợi của bạn. Bạn có thể gửi lại sản phẩm qua địa chỉ bên dưới:</p>" +
                    "<p style='color: #333;'><strong>StyloftCloth Return Center</strong></p>" +
                    "<p style='color: #333;'>123 Đường Hoàn Trả, Quận Hoàn Kiếm, Hà Nội, Việt Nam</p>" +
                    "<p style='color: #333;'>Lưu ý: Vui lòng ghi rõ mã đơn hàng trên gói hàng để chúng tôi xử lý nhanh chóng.</p>" +
                    "<h3 style='color: #28a745;'>Chúng tôi luôn sẵn sàng hỗ trợ bạn!</h3>" +
                    "<p style='color: #333;'>Nếu bạn có bất kỳ câu hỏi nào hoặc cần hỗ trợ thêm, vui lòng liên hệ với chúng tôi qua email hoặc hotline để được trợ giúp.</p>" +
                    "<a href=/orders' style='color: #0066cc;'>Kiểm tra trạng thái hoàn trả</a>" +
                    "<br><br><p>Best regards,<br>AnhDungShop</p></div></body></html>";

            emailService.sendHtmlEmail(
                    user.getAccount().getEmail(),
                    "Xác nhận hoàn trả đơn hàng #" + oldOrder.getId().substring(0, 5),
                    emailContent
            );

        }


        orderService.saveOrder(oldOrder);
//        smsService.sendSms(user);

        return "redirect:/admin/order";
    }

    @GetMapping("/detail/{id}")
    public String getPageDetail(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        Order order = orderService.findById(id);
        if(order != null) {

            if(order.isAnnounceOrder()){
                HttpSession session = request.getSession();
                session.setAttribute("totalAnnounce", Math.max((int)session.getAttribute("totalAnnounce") - 1, 0));

                order.setAnnounceOrder(false);
                orderService.saveOrder(order);
            }

            model.addAttribute("details", orderDetailService.findAllOrderDetailByOrderId(id));


        }
        model.addAttribute("order", order != null ? order : new Order());

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
