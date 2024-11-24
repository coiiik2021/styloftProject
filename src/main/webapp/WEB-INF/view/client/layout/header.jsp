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
    <link
            href="https://cdn.jsdelivr.net/npm/remixicon/fonts/remixicon.css"
            rel="stylesheet"
    />
    <!-- Libraries Stylesheet -->

    <!-- Customized Bootstrap Stylesheet -->

    <!-- Template Stylesheet -->
    <%--    <link href="/client/css/style.css" rel="stylesheet">--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">


</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary bg-primary py-1 px-4">
        <!-- Logo -->
        <a href="/" class="navbar-brand d-flex align-items-center">
            <img class="rounded img-fluid" style="width: 90px; height: 70px" src="/images/assets/img/logo.png" alt="Logo">
        </a>

        <!-- Toggler -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
                aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar content -->
        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
            <!-- Navigation Links -->
            <ul class="navbar-nav align-items-center">
                <li class="nav-item">
                    <a href="/" class="nav-link active px-3 py-2" style="font-size: 14px">NEW ARRIVALS</a>
                </li>
                <li class="nav-item">
                    <a href="/product" class="nav-link px-3 py-2" style="font-size: 14px">CLOTHING</a>
                </li>
                <li class="nav-item">
                    <a href="/blog" class="nav-link px-3 py-2" style="font-size: 14px">BLOG</a>
                </li>
                <li class="nav-item">
                    <a href="/about" class="nav-link px-3 py-2" style="font-size: 14px">ABOUT</a>
                </li>
            </ul>

            <!-- Search Form -->
            <form class="d-flex position-relative me-3" role="search" action="/product" method="get">
                <input class="form-control me-2" type="search" id="itemInput" placeholder="Search" aria-label="Search" name="name" autocomplete="off" value="${sessionScope.nameSearch}">
                <div id="itemList" class="dropdown-menu w-80" style="display: none; position: absolute; background: white; border: 1px solid #ddd; max-height: 200px; overflow-y: auto; width: calc(100% - 50px); z-index: 1000; top: 100%; left: 0;"></div>
                <button class="btn btn-outline bg-orange" type="submit"><i class="ri-search-line"></i></button>
            </form>

            <!-- User and Cart -->
            <div class="d-flex align-items-center ms-auto mt-2">
                <c:if test="${not empty pageContext.request.userPrincipal && !sessionScope.isAdmin}">
                    <form action="/cart" method="get" class="me-3">
                        <button class="position-relative me-4 my-auto btn-custom">
                            <i class="fa fa-shopping-bag" style="font-size: 1.5em;"></i>
                            <span class="position-absolute d-flex align-items-center justify-content-center"
                                  style="top: -5px; left: 30px; height: 20px; min-width: 20px;border: white solid 10px; border-radius: 10px;color: #ff6900; background-color:#e65a06 ">
                                    ${sessionScope.sum}
                            </span>
                        </button>
                    </form>
                    <div class="dropdown">
                        <a href="#" class="dropdown" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-user btn-custom" style="font-size: 1.5em!important;"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuLink">
                            <li class="dropdown-item text-center"><c:out value="${sessionScope.email}" /></li>
                            <li><a class="dropdown-item" href="/account">Quản lý tài khoản</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="/logout" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <button class="dropdown-item">Đăng xuất</button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </c:if>
                <c:if test="${empty pageContext.request.userPrincipal}">
                    <a href="/login" class="btn btn-light fw-semibold me-3">Đăng nhập</a>
                </c:if>
                <c:if test="${sessionScope.isAdmin}">
                    <a href="/admin" class="btn btn-light fw-semibold">Quản lý</a>
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


<script>
    let allItems = []; // Mảng chứa toàn bộ danh sách từ server

    const itemInput = document.getElementById('itemInput');
    const itemList = document.getElementById('itemList');

    // Lấy danh sách từ server khi input được focus lần đầu tiên
    itemInput.addEventListener('focus', function () {
        if (allItems.length === 0) { // Chỉ tải một lần duy nhất
            fetch('/search')
                .then(response => response.json())
                .then(data => {
                    allItems = data; // Lưu toàn bộ danh sách vào mảng
                    showItemList(''); // Hiển thị toàn bộ danh sách khi người dùng lần đầu nhấp vào input
                })
                .catch(error => {
                    console.error('Error fetching items:', error);
                });
        } else {
            showItemList(''); // Hiển thị toàn bộ danh sách khi đã tải dữ liệu
        }
    });

    // Lọc và hiển thị danh sách khi người dùng nhập vào input
    itemInput.addEventListener('input', function () {
        const query = this.value.toLowerCase(); // Lấy giá trị người dùng nhập vào và chuyển thành chữ thường
        showItemList(query); // Cập nhật danh sách dựa trên từ khóa
    });

    // Hàm hiển thị danh sách
    function showItemList(query) {
        const filteredItems = allItems.filter(item => item.toLowerCase().includes(query)); // Lọc danh sách dựa trên từ khóa

        // Hiển thị danh sách đã lọc
        itemList.innerHTML = ''; // Xóa nội dung cũ

        if (filteredItems.length > 0) {
            itemList.style.display = 'block'; // Hiển thị danh sách nếu có kết quả
            filteredItems.forEach(item => {
                const itemElement = document.createElement('div');
                itemElement.textContent = item;
                itemElement.style.padding = '8px';
                itemElement.style.cursor = 'pointer';

                // Thêm sự kiện click để chọn item
                itemElement.addEventListener('click', function () {
                    itemInput.value = item;
                    itemList.innerHTML = ''; // Ẩn danh sách sau khi chọn
                    itemList.style.display = 'none'; // Ẩn hoàn toàn danh sách
                });

                itemList.appendChild(itemElement);
            });
        } else {
            itemList.style.display = 'none'; // Ẩn danh sách nếu không có kết quả
        }
    }

    // Ẩn danh sách khi click ra ngoài
    document.addEventListener('click', function (event) {
        if (!itemInput.contains(event.target) && !itemList.contains(event.target)) {
            itemList.innerHTML = ''; // Xóa danh sách khi click ra ngoài
            itemList.style.display = 'none'; // Ẩn hoàn toàn danh sách
        }
    });
</script>