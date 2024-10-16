<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <title>Fruitables - Vegetable Website Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
            rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
          rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet">
    <style>
        .card:hover {
            transform: scale(1.05);
        }

        .card {
            transition: transform 0.5s;
        }

        body, html {
            background-color: #FDE9CE;
        }

        .btn {
            background-color: #ff6900;
            color: #fff;
            font-weight: bold;
            border: navajowhite;
        }

        .btn:hover {
            background-color: rgba(255, 105, 0, 0.6);
            color: #fff;
        }

        .text-primary {
            color: #ff6900 !important;
        }

        /* Tạo header cố định và trong suốt */


        /* Tạo khoảng cách giữa nội dung và header */
        body {
            padding-top: 80px; /* Khoảng cách để tránh nội dung bị đè bởi header */
        }

        .slide {
            margin-top: 100px;
        }
    </style>
</head>
<body>
<style>
    .card:hover {
        transform: scale(1.05);
    }

    .card {
        transition: transform 0.5s;
    }

    body, html {
        background-color: #FDE9CE;
    }

    .btn {
        background-color: #ff6900;
        color: #fff;
        font-weight: bold;
        border: navajowhite;
    }

    .btn:hover {
        background-color: rgba(255, 105, 0, 0.6);
        color: #fff;
    }

    .text-primary {
        color: #ff6900 !important;
    }

    /* Tạo header cố định và trong suốt */


    /* Tạo khoảng cách giữa nội dung và header */
    body {
        padding-top: 80px; /* Khoảng cách để tránh nội dung bị đè bởi header */
    }

    .slide {
        margin-top: 100px;
    }
    .header{
       position: fixed;
        top: 0;
        z-index: 999;
    }

    header {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        z-index: 9999; /* Đảm bảo header nổi lên trên */
        background-color: transparent; /* Màu trong suốt */
        transition: background-color 0.3s ease; /* Hiệu ứng chuyển màu khi cuộn */
    }

    /* Thêm hiệu ứng đổi màu khi cuộn */
    .scrolled {
        background-color: rgba(253, 233, 206, 0.9); /* Màu nền khi cuộn */
    }
    header {
        position: fixed;
        width: 100%;
        z-index: 1000; /* Đảm bảo header nằm trên cùng */
        background-color: rgba(253, 233, 206, 0.8); /* Màu nền trong suốt */
    }

    .navbar-nav a {
        color: #ff6900; /* Màu chữ cho các liên kết */
    }

    .btn-custom {
        background-color: transparent; /* Bỏ màu nền */
        border: none; /* Bỏ viền */
        color: #b65bca;

    }

    .btn-custom:hover {
        /*background-color: rgba(255, 105, 0, 0.3); !* Tạo hiệu ứng hover *!*/

        background-color: transparent; /* Bỏ màu nền */

        color: #a16868; /* Màu chữ khi hover */
    }





</style>
<header class="container-fluid">
        <nav class="navbar navbar-light navbar-expand-xl">
            <a href="/" class="navbar-brand">
                <h1 class="text-primary display-6">Anh Dũng Shop</h1>
            </a>
            <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="fa fa-bars text-primary"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-between mx--5" id="navbarCollapse">
                <div class="navbar-nav">
                    <a href="/" class="nav-item nav-link active">Home</a>
                    <a href="/product" class="nav-item nav-link">Product</a>
                </div>
                <div class="d-flex m-3 me-0">
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                        <form action="/cart" method="get">
                            <div>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </div>
                            <button class="position-relative me-4 my-auto btn btn-custom">
                                <i class="fa fa-shopping-bag fa-2x"></i>

                                <span class="position-absolute rounded-circle d-flex align-items-center justify-content-center"
                                      style="top: -5px; left: 30px; height: 20px; min-width: 20px; color: #c438e4;"> <!-- Màu xanh nhạt -->
     ${sessionScope.sum}
</span>

                            </button>
                        </form>

                        <div class="dropdown my-auto">
                            <a href="#" class="dropdown" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user fa-2x btn-custom"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end p-4" aria-labelledby="dropdownMenuLink">
                                <li class="d-flex align-items-center flex-column" style="min-width: 300px;">
                                    <img style="width: 150px; height: 150px; border-radius: 50%;" src="/image/avatar/${sessionScope.avatar}" />
                                    <div class="text-center my-3">
                                        <c:out value="${sessionScope.email}" />
                                    </div>
                                </li>
                                <li><a class="dropdown-item" href="/account">Quản lý tài khoản</a></li>
                                <li><a class="dropdown-item" href="/order-history">Lịch sử mua hàng</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <form action="/logout" method="post">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <button class="dropdown-item">Đăng xuất</button>
                                    </form>
                                </li>
                            </ul>
                        </div>
                    </c:if>

                    <c:if test="${ empty pageContext.request.userPrincipal}">
                        <a href="/login" class="position-relative me-4 my-auto btn btn-custom">Đăng nhập</a>
                    </c:if>
                </div>
            </div>
        </nav>
</header>

<script>
    window.addEventListener('scroll', function() {
        var header = document.querySelector('header');
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });
</script>