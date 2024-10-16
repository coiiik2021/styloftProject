<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
            <form>
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Loại:</label><br />
                    <div class="form-check" >
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="aoThun"
                        />
                        <label class="form-check-label" for="aoThun">Áo thun</label>
                    </div>
                    <div class="form-check" >
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="aoPolo"
                        />
                        <label class="form-check-label" for="aoPolo">Áo Polo</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="hoodie&aoni"
                        />
                        <label class="form-check-label" for="hoodie&aoni"
                        >Hoodie và áo nỉ</label
                        >
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="aokhoac"
                        />
                        <label class="form-check-label" for="aokhoac">Áo khoác</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="quandai"
                        />
                        <label class="form-check-label" for="quandai">Quần dài</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="quanngan"
                        />
                        <label class="form-check-label" for="quanngan">Quần ngắn</label>
                    </div>
                </div>
                <hr />

                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Giới tính:</label><br />
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="male"
                        />
                        <label class="form-check-label" for="male">Nam</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="female"
                        />
                        <label class="form-check-label" for="female">Nữ</label>
                    </div>
                </div>
                <hr />
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Màu sắc:</label><br />
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="XanhLa"
                        />
                        <label class="form-check-label" for="XanhLa">Xanh Lá</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="Do"
                        />
                        <label class="form-check-label" for="Do">Đỏ</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="Tim"
                        />
                        <label class="form-check-label" for="Tim">Tím</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="Den"
                        />
                        <label class="form-check-label" for="Den">Đen</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="Trang"
                        />
                        <label class="form-check-label" for="Trang">Trắng</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="Xam"
                        />
                        <label class="form-check-label" for="Xam">Xám</label>
                    </div>
                </div>

                <hr />
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Kích thước:</label><br />
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="XS"
                        />
                        <label class="form-check-label" for="XS">XS</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="S"
                        />
                        <label class="form-check-label" for="S">S</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="M"
                        />
                        <label class="form-check-label" for="M">M</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="L"
                        />
                        <label class="form-check-label" for="L">L</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="XL"
                        />
                        <label class="form-check-label" for="XL">XL</label>
                    </div>
                    <div class="form-check">
                        <input
                                class="form-check-input"
                                type="checkbox"
                                value=""
                                id="XXL"
                        />
                        <label class="form-check-label" for="XXL">XXL</label>
                    </div>
                </div>
                <hr />
                <!-- Chọn giá tiền trong khoảng -->
                <div class="mb-3" style="border: pink solid 5px;">
                    <label class="form-label">Giá tiền:</label><br />
                    <div class="price-input">
                        <input type="number" placeholder="₫ Từ" />
                        <span class="separator">-</span>
                        <input type="number" placeholder="₫ Đến" />
                    </div>
                    <button class="apply-btn">Áp dụng</button>
                </div>

                <hr />
                <div class="mb-3" >
                    <button class="reset-btn">Xóa tất cả</button>
                </div>
            </form>
        </div>

        <!-- Product Listing -->
        <div class="col-md-9">
            <div class="row">
                <!-- Product 1 -->
                <c:forEach items="${products}" var="product">
                    <div class="col-md-4">
                        <a href="/product/detail/${product.id}">
                            <div class="product-card">
                                <img
                                        src="/images/product/${product.image}"
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
