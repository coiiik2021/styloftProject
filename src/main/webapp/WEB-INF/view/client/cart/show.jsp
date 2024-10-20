<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: dungnguyen
  Date: 15/10/24
  Time: 01:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>TuanKiet</title>
  <script src="https://kit.fontawesome.com/7a738de67b.js" crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<!--Body-->
<jsp:include page="../layout/header.jsp" />


<main>
  <div class="container mt-5">
    <h2 class="text-center" style="color: #000;">Giỏ hàng của bạn</h2>

    <p class="text-center" style="color: #000;">Có ${items.size()} sản phẩm trong giỏ hàng</p>

    <div class="card">
      <div class="card-body">

        <table class="table table-bordered align-middle text-center">
          <thead>
          <tr style="color: #000;">
            <th>Thông tin sản phẩm</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Thành tiền</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="#{items}" var="item">
          <tr>
            <td>
              <div class="d-flex align-items-center">
                <img src="/images/product/${item.productItem.product.name}/${item.productItem.image}" class="img-fluid" style="width: 80px; height: 80px;" alt="Hình ảnh sản phẩm">
                <div class="ms-3 text-start" style="color: #000;">
                  <p class="mb-1">${item.productItem.product.name}</p>
                  <small style="color: #000;">Màu: ${item.productItem.color.name} / Kích cỡ: ${item.productItem.size.name}</small>
                </div>
              </div>
            </td>
            <td>
              <div class="input-group mx-auto" style="width: 140px; align-content: center">
                <a class="btn btn-outline-secondary" type="button" href="/cart/updateDown/${item.id}">-</a>
                <input type="number" class="form-control text-center" value="${item.quantity}" min="1" readonly style="width: 60px;">
                <a class="btn btn-outline-secondary" type="button" href="/cart/updateUp/${item.id}">+</a>
              </div>
            </td>

            <td style="color: #000;"><fmt:formatNumber type="number" value="${item.productItem.price}"/> đ</td>
            <td style="color: #000;">
              <fmt:formatNumber type="number" value="${item.productItem.price * item.quantity}"/> đ
            </td>
            <td>

                <a href = "/cart/delete-item-in-cart/${item.id}" class="btn btn-danger btn-sm">
                  <i class="fas fa-trash"></i>
                </a>
            </td>
          </tr>
          </c:forEach>

          </tbody>
        </table>

        <c:if test="${sessionScope.sum > 0}">
        <div class="d-flex justify-content-between align-items-center mt-3">
          <h4 class="mb-0" style="color: #000;">Tổng tiền: <span style="color: #F15F25;"> <fmt:formatNumber type="number" value="${totalPrice}"/>  VND</span></h4>
          <a href="/pay" class="btn" style="background-color: #F15F25; color: white;">Thanh toán</a>
        </div>
        </c:if>
      </div>
    </div>
  </div>
</main>
<jsp:include page="../layout/footer.jsp" />

</body>
</html>
