<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Product Page</title>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />

    <!-- Custom CSS -->
    <link rel="stylesheet" href="/css/product/products.css" />
    <link href="/client/css/style.css" rel="stylesheet">

    <style>
        body {
            padding-top: 80px; /* Khoảng cách để tránh nội dung bị đè bởi header */
        }
        .carousel-inner {
            margin-top: 100px;
        }

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
<jsp:include page="../layout/header.jsp" />

<div class="container-fluid">
    <div id="carouselExampleIndicators" class="carousel slide">
        <div class="carousel-indicators">
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="0"
                    class="active"
                    aria-current="true"
                    aria-label="Slide 1"
            ></button>
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="1"
                    aria-label="Slide 2"
            ></button>
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="2"
                    aria-label="Slide 3"
            ></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img
                        src="/images/Banner/banner1.png"
                        class="d-block w-100 h-100 img-fluid"
                        alt="..."
                />
            </div>
            <div class="carousel-item">
                <img
                        src="/images/Banner/banner2.png"
                        class="d-block w-100 h-100 img-fluid"
                        alt="..."
                />
            </div>
            <div class="carousel-item">
                <img
                        src="/images/Banner/Banner3.png"
                        class="d-block w-100 h-100 img-fluid"
                        alt="..."
                />
            </div>
        </div>
        <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide="prev"
        >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide="next"
        >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <div class="row" >
        <!-- Filter Section -->
        <div class="col-md-3 filter-section" style="border: pink solid 5px; margin-top: 5px; border-radius: 30px;">
            <h5>Bộ lọc tìm kiếm</h5>
            <form id="filterForm">
                <!-- Category filter -->
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Loại:</label><br />
                    <c:forEach items="${categories}" var="cate">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="categories" value="${cate.name}" id="${cate.name}" />
                            <label class="form-check-label" for="${cate.name}">${cate.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <hr />

                <!-- Color filter -->
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Màu sắc:</label><br />
                    <c:forEach items="${colors}" var="color">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="colors" value="${color.name}" id="${color.name}" />
                            <label class="form-check-label" for="${color.name}">${color.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <hr />

                <!-- Size filter -->
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Kích thước:</label><br />
                    <c:forEach items="${sizes}" var="size">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="sizes" value="${size.name}" id="${size.name}" />
                            <label class="form-check-label" for="${size.name}">${size.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <hr />


                <div class="mb-3">
                    <label class="form-label">Giá tiền:</label><br />
                    <div class="price-input">
                        <input type="number" name="minPrice" placeholder="Giá tối thiểu" />
                        <span class="separator">-</span>
                        <input type="number" name="maxPrice" placeholder="Giá tối đa" />
                    </div>
                </div>

                <!-- Apply button -->
                <div class="mb-3">
                    <button type="button" class="apply-btn" onclick="applyFilters()">Áp dụng</button>
                </div>
            </form>
        </div>

        <script>
            function applyFilters() {
                const form = document.getElementById('filterForm');

                let categories = [];
                let colors = [];
                let sizes = [];

                form.querySelectorAll('input[name="categories"]:checked').forEach((checkbox) => {
                    categories.push(checkbox.value);
                });

                form.querySelectorAll('input[name="colors"]:checked').forEach((checkbox) => {
                    colors.push(checkbox.value);
                });

                form.querySelectorAll('input[name="sizes"]:checked').forEach((checkbox) => {
                    sizes.push(checkbox.value);
                });

                const minPrice = form.querySelector('input[name="minPrice"]').value;
                const maxPrice = form.querySelector('input[name="maxPrice"]').value;

                let queryParams = new URLSearchParams();

                if (categories.length > 0) {
                    queryParams.append('categories', categories.join(','));
                }

                if (colors.length > 0) {
                    queryParams.append('colors', colors.join(','));
                }

                if (sizes.length > 0) {
                    queryParams.append('sizes', sizes.join(','));
                }

                if (minPrice) {
                    queryParams.append('minPrice', minPrice);
                }
                if (maxPrice) {
                    queryParams.append('maxPrice', maxPrice);
                }


                window.location.href = '/product/filter?' + queryParams.toString();
            }
        </script>

        <!-- Product Listing -->
        <div class="col-md-9">
            <div class="row">
                <!-- Product 1 -->
                <c:forEach items="${products}" var="product">
                    <div class="col-md-4">
                        <a href="/product/detail/${product.id}">
                            <div class="product-card">
                                <img
                                        src="/images/product/${product.name}/${product.productItem.get(0).image}"
                                        alt="${product.name}"
                                        class="product-img"
                                />
                                <h5 class="mt-3">${product.name}</h5>
                                <p>${product.productItem.get(0).price} VND</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>


            </div>
        </div>
    </div>
</div>
<jsp:include page="../layout/footer.jsp" />


<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
