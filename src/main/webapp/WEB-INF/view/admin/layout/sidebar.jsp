<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav">
                <div class="sb-sidenav-menu-heading">Features</div>
                <a class="nav-link" href="/admin">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Dashboard
                </a>

                <a class="nav-link" href="/admin/account">
                    <div class="sb-nav-link-icon"><i class="bi bi-person-circle"></i></div>
                    Account
                </a>

                <a class="nav-link" href="/admin/product">
                    <div class="sb-nav-link-icon"><i class="bi bi-box-seam-fill"></i></div>
                    Product
                </a>

                <a class="nav-link" href="/admin/category">
                    <div class="sb-nav-link-icon"><i class="bi bi-inboxes"></i></div>
                    Categories
                </a>
                <a class="nav-link" href="/admin/item">
                    <div class="sb-nav-link-icon"><i class="bi bi-handbag"></i></div>
                    Product Variant
                </a>

                <a class="nav-link" href="/admin/size">
                    <div class="sb-nav-link-icon"><i class="bi bi-arrows-angle-expand"></i></div>
                    Sizes
                </a>

                <a class="nav-link" href="/admin/color">
                    <div class="sb-nav-link-icon"><i class="bi bi-palette"></i></div>
                    Colors
                </a>
                <a class="nav-link" href="/admin/voucher">
                    <div class="sb-nav-link-icon"><i class="bi bi-ticket-detailed"></i></div>
                    Voucher
                </a>
                <a class="nav-link position-relative" href="/admin/order" style="position: relative;">
                    <div class="sb-nav-link-icon"><i class="bi bi-basket-fill"></i></div>
                    Order
                    <span style="
                            position: absolute;
                            top: -10px; /* Đặt phía trên chữ */
                            left: 40%;  /* Căn giữa chữ  */
                                        color: red; /* Màu đỏ cho số lượng */
                                        font-size: 15px; /* Nhỏ hơn */
                                        font-weight: bold;
                                        display: inline-block;
                                        line-height: 3;
                                        transform: translateX(-80%);
                                        ">
                                        ${sessionScope.totalAnnounce > 0 ? sessionScope.totalAnnounce : ''}
                                        </span>
                </a>

                <a class="nav-link" href="/">
                    <div class="sb-nav-link-icon"><i class="bi bi-house-door-fill"></i></div>
                    Home
                </a>

            </div>
        </div>

    </nav>
</div>