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
    <nav class="navbar navbar-expand-md bg-body-tertiary bg-primary py-1">

        <a href="/" class="navbar-brand">
            <%--                <h1 class="text-primary display-6 fw-semibold">Anh Dũng Shop</h1>--%>
            <img class="rounded mx-auto d-block img-fluid" style="width: 90px; height: 70px" src="/images/assets/img/logo.png" alt="a">
        </a>

        <div class="collapse navbar-collapse justify-content-between mx--5" id="navbarCollapse">
            <nav class="navbar navbar-expand-md bg-body-tertiary py-0">
                <ul class="navbar-nav">
                    <li class="nav-item gx-12">
                        <a href="/" class="nav-link active px-3 py-2" style="font-size: 14px">NEW ARRIVALS</a>
                    </li>
                    <li class="nav-item">
                        <a href="/product" class="nav-link px-3 py-2" style="font-size: 14px">CLOTHING</a>
                    </li>
                    <li class="nav-item">
                        <a href="/" class="nav-link px-3 py-2" style="font-size: 14px">BLOG</a>
                    </li>
                    <li class="nav-item">
                        <a href="/" class="nav-link px-3 py-2" style="font-size: 14px">ABOUT</a>
                    </li>
                    <li>
                        <div class="container-fluid">
                            <form class="d-flex position-relative" role="search" action="/product" method="get">
                                <input class="form-control me-2" type="search" id="itemInput" placeholder="Search" aria-label="Search" name="name" autocomplete="off" value="${sessionScope.nameSearch}">

                                <!-- Div để hiển thị danh sách kết quả tìm kiếm -->
                                <div id="itemList" style="position: absolute; background: white; border: 1px solid #ddd; max-height: 200px; overflow-y: auto; width: calc(100% - 40px); z-index: 1000; top: 100%; left: 0;"></div>

                                <button class="btn btn-outline bg-orange" type="submit"><i class="ri-search-line"></i></button>
                            </form>
                        </div>

                        <script>
                            let allItems = []; // Mảng chứa toàn bộ danh sách từ server

                            // Lấy danh sách từ server khi input được focus lần đầu tiên
                            document.getElementById('itemInput').addEventListener('focus', function() {
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
                            document.getElementById('itemInput').addEventListener('input', function() {
                                const query = this.value.toLowerCase(); // Lấy giá trị người dùng nhập vào và chuyển thành chữ thường
                                showItemList(query); // Cập nhật danh sách dựa trên từ khóa
                            });

                            // Hàm hiển thị danh sách
                            function showItemList(query) {
                                const filteredItems = allItems.filter(item => item.toLowerCase().includes(query)); // Lọc danh sách dựa trên từ khóa

                                // Hiển thị danh sách đã lọc
                                const itemList = document.getElementById('itemList');
                                itemList.innerHTML = ''; // Xóa nội dung cũ

                                filteredItems.forEach(item => {
                                    const itemElement = document.createElement('div');
                                    itemElement.textContent = item;
                                    itemElement.style.padding = '8px';
                                    itemElement.style.cursor = 'pointer';

                                    // Thêm sự kiện click để chọn item
                                    itemElement.addEventListener('click', function() {
                                        document.getElementById('itemInput').value = item;
                                        itemList.innerHTML = ''; // Ẩn danh sách sau khi chọn
                                    });

                                    itemList.appendChild(itemElement);
                                });
                            }

                            // Ẩn danh sách khi click ra ngoài
                            document.addEventListener('click', function(event) {
                                const itemList = document.getElementById('itemList');
                                const itemInput = document.getElementById('itemInput');

                                if (!itemInput.contains(event.target) && !itemList.contains(event.target)) {
                                    itemList.innerHTML = ''; // Xóa danh sách khi click ra ngoài
                                }
                            });
                        </script>

                    </li>
                </ul>
            </nav>


            <div class="d-flex m-3 me-0">
                <c:if test="${not empty pageContext.request.userPrincipal && !sessionScope.isAdmin}">
                    <form action="/cart" method="get">

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
                                <%--                        <li><a class="dropdown-item" href="/order-history">Lịch sử mua hàng</a></li>--%>
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

                <c:if test="${ empty pageContext.request.userPrincipal}" >
                    <a href="/login" class="position-relative me-4 my-auto btn btn-custom btn-white fw-semibold">Đăng nhập</a>
                </c:if>
                <c:if test="${sessionScope.isAdmin}">
                    <a href="/admin" class="position-relative me-4 my-auto btn btn-custom btn-white fw-semibold" >Quản lý</a>
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
