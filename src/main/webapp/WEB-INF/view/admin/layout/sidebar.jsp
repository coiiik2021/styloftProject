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
                            <div class="sb-nav-link-icon"><i class="bi bi-box-seam-fill"></i></i></div>
                            Product
                        </a>

                        <a class="nav-link" href="/admin/category">
                            <div class="sb-nav-link-icon"><i class="bi bi-inboxes"></i></i></div>
                            Categories
                        </a>
                        <a class="nav-link" href="/admin/item">
                            <div class="sb-nav-link-icon"><i class="bi bi-handbag"></i></div>
                            Product Variant
                        </a>

                        <a class="nav-link" href="/admin/size">
                            <div class="sb-nav-link-icon"><i class="bi bi-arrows-angle-expand"></i></i></div>
                            Sizes
                        </a>

                        <a class="nav-link" href="/admin/color">
                            <div class="sb-nav-link-icon"><i class="bi bi-palette"></i></div>
                            Colors
                        </a>
                        <a class="nav-link" href="/admin/voucher">
                            <div class="sb-nav-link-icon"><i class="bi bi-ticket-detailed"></i></i></div>
                            Voucher
                        </a>
                        <a class="nav-link" href="/admin/order">
                            <div class="sb-nav-link-icon"><i class="bi bi-basket-fill"></i></i></div>
                            Order <strong style="color: red">${sessionScope.totalAnnounce > 0 ? sessionScope.totalAnnounce : ''}</strong>
                        </a>

                        <a class="nav-link" href="/">
                            <div class="sb-nav-link-icon"><i class="bi bi-house-door-fill"></i></i></div>
                            Home
                        </a>

                    </div>
                </div>

            </nav>
        </div>