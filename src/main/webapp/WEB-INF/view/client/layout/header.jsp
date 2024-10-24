<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <link rel="stylesheet" href="/css/client/header.css">

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

    <!-- Customized Bootstrap Stylesheet -->

    <!-- Template Stylesheet -->
<%--    <link href="/client/css/style.css" rel="stylesheet">--%>

</head>
<body>
<header class="container-fluid px-4">
    <nav class="navbar navbar-light navbar-expand-xl">
        <a href="/" class="navbar-brand">
            <%--                <h1 class="text-primary display-6 fw-semibold">Anh Dũng Shop</h1>--%>
            <img class="rounded mx-auto d-block img-fluid" style="width: 100px; height: 80%" src="/images/assets/img/logo.png" alt="">
        </a>
        <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="fa fa-bars text-primary"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-between mx--5" id="navbarCollapse">
            <div class="navbar-nav">
                <a href="/" class="nav-item nav-link active" style="margin-left: 6px">Home</a>
                <a href="/product" class="nav-item nav-link mx-3">Product</a>
            </div>

           <form style="display: flex; justify-content: center;" id="formSearch" onsubmit="return searchByName(event)">
    <div class="inputGroup">
        <input type="text" required="" autocomplete="off" name="search" id="nameSearch">
        <label for="nameSearch">Search</label>
    </div>
    <button type="submit" class="button-search">
        <span>
            <svg viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg">
                <path d="M9.145 18.29c-5.042 0-9.145-4.102-9.145-9.145s4.103-9.145 9.145-9.145 9.145 4.103 9.145 9.145-4.102 9.145-9.145 9.145zm0-15.167c-3.321 0-6.022 2.702-6.022 6.022s2.702 6.022 6.022 6.022 6.023-2.702 6.023-6.022-2.702-6.022-6.023-6.022zm9.263 12.443c-.817 1.176-1.852 2.188-3.046 2.981l5.452 5.453 3.014-3.013-5.42-5.421z"></path>
            </svg>
        </span>
    </button>
</form>

<script>
    function searchByName(event) {
        event.preventDefault();

        const form = document.getElementById('formSearch');
        const name = form.querySelector('input[name="search"]').value.trim();

        if (name) {
            window.location.href = '/product?name=' + encodeURIComponent(name);
        }
    }
</script>




            <div class="d-flex m-3 me-0">
                <c:if test="${not empty pageContext.request.userPrincipal}">
                <form action="/cart" method="get">
                    <div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </div>
                    <button class="position-relative me-4 my-auto btn-custom">
                        <i class="fa fa-shopping-bag" style="font-size: 1.5em;"></i>

                        <span class="position-absolute d-flex align-items-center justify-content-center"
                              style="top: -5px; left: 30px; height: 20px; min-width: 20px;border: white solid 10px; border-radius: 10px;color: #ff6900; background-color:#e65a06 ">
                                ${sessionScope.sum}
                        </span>

                    </button>
                </form>

                <div class="dropdown my-auto">
                    <a href="#" class="dropdown" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user btn-custom" style="font-size: 1.5em!important;"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end p-4" aria-labelledby="dropdownMenuLink">
                        <li class="d-flex align-items-center flex-column" style="min-width: 300px;">
<!-- <%--                            <img style="width: 150px; height: 150px; border-radius: 50%;" src="/image/avatar/${sessionScope.avatar}" />--%> -->
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
    <a href="/login" class="position-relative me-4 my-auto btn btn-custom fw-semibold">Đăng nhập</a>
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

<!-- <script>
    function searchByName(){
        const form = document.getElementById('formSearch');


        const name = form.querySelector('input[name="nameSearch"]').value;

        window.location.href = '/product?name=' + name;



    }
</script> -->