<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dungnguyen
  Date: 15/10/24
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

  <title>Chi tiết sản phẩm</title>
</head>
<body>
<jsp:include page="../layout/header.jsp" />
<link rel="stylesheet" href="/css/product/detail.css" type="text/css">

<div class="container" >
  <div class="row">
    <div class="col-md-6">
      <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <c:forEach var="item" items="${item.product.productItem}">
                <div class="carousel-item ${item.image eq item.product.productItem.get(0).image ? 'active' : ''}">
                    <img class="d-block w-100" src="/images/product/${item.product.name}/${item.image}" alt="${item.product.name}">
                </div>
            </c:forEach>

<%--          <div class="carousel-item">--%>
<%--            <img class="d-block w-100" src="Image/hoodie.png" alt="Hoodie Image 2">--%>
<%--          </div>--%>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
<%--          <span class="carousel-control-prev-icon" aria-hidden="true"></span>--%>
        <i class="bi bi-caret-left"></i>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
            <i class="bi bi-caret-right"></i>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>
    <div class="col-md-6 d-flex flex-column justify-content-center">
        <div class="infor">
          <h2 class="fw-bold">${item.product.name}</h2>
          <div class="price mb-2"> <!-- New price section -->
            <strong style="font-size: 1.5rem; color: red;">Giá: ${item.price} VND</strong>
          </div>
          <p><strong>Mô tả:</strong> ${item.product.description}.</p>
          <div class="rating text-warning">
            <span class="fa fa-star checked"></span>
            <span class="fa fa-star checked"></span>
            <span class="fa fa-star checked"></span>
            <span class="fa fa-star checked"></span>
            <span class="fa fa-star"></span>
          </div>
            <div class="my-3">
                <strong>Kích thước:</strong>
                <div>
                    <c:forEach items="${sizes}" var="size">
                        <a href="javascript:void(0);"
                           class="btn btn-outline-danger btn-sm me-1 ${item.size.name eq size.name ? 'disabled' : ''}"
                           onclick="updateSelection('size', '${size.name}')">
                                ${size.name}
                        </a>
                    </c:forEach>
                </div>
            </div>

            <div class="my-3">
                <strong>Màu sắc:</strong>
                <div>
                    <c:forEach items="${colors}" var="color">
                        <a href="javascript:void(0);"
                           class="btn  btn-sm me-1 }"
                           onclick="updateSelection('color',
                                   '${color.name}')" style=" display: inline-block; padding: 10px; width: 30px; height: 30px;
                                    background-color: ${color.description}; ${item.color.name eq color.name ? 'border: black solid 1px; ' : ''}
                                    border-radius: 50%">
<%--                                ${color.name}--%>
                            <div ></div>
                        </a>
                    </c:forEach>
                </div>
            </div>

            <script>
                // Biến để lưu giá trị đã chọn của size và color
                let selectedSize = '${param.size != null ? param.size : ''}';
                let selectedColor = '${param.color != null ? param.color : ''}';

                function updateSelection(type, value) {
                    if (type === 'size') {
                        selectedSize = value;
                    } else if (type === 'color') {
                        selectedColor = value;
                    }

                    sendRequest();
                }

                function sendRequest() {
                    var productId = '${item.product.id}';

                    var url = '/product/detail/' + productId;
                    var params = [];

                    if (selectedSize) {
                        params.push('size=' + selectedSize);
                    }

                    if (selectedColor) {
                        params.push('color=' + selectedColor);
                    }

                    if (params.length > 0) {
                        url += '?' + params.join('&');
                    }

                    window.location.href = url;
                }
            </script>

            <div class="d-flex align-items-center my-3">
            <label class="mb-0 me-2"><strong>Số lượng:</strong></label>
            <input type="number" class="form-control me-2" value="1" min="1" max="${item.quantity}" style="width: 80px;">
            <span>Còn lại ${item.quantity} sản phẩm</span>
          </div>
          <div class="text-center mt-1 "> <!-- Changed margin-top to create space -->
            <a href = "/cart/add-product-item-in-cart/${item.id}" class="btn btn-danger btn-sm ${item.quantity eq 0 ? 'disabled' : ''}" id="checkout-btn" style="background: rgb(247, 240, 229); color: dimgrey; border: 1px solid black;">
              <strong>Thêm vào giỏ hàng</strong>
            </a>
          </div>
        </div>
    </div>
  </div>
</div>
    <jsp:include page="../layout/footer.jsp" />
</body>

</html>
