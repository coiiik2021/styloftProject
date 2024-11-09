<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
          rel="stylesheet"
  />
  <link
          href="https://cdn.jsdelivr.net/npm/remixicon/fonts/remixicon.css"
          rel="stylesheet"
  />
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      max-width: 960px; /* Giới hạn chiều rộng tối đa của form */
    }
    .form-control {
      border: 1px solid #ced4da;
      outline: none;
    }
    .input-group-text {
      background-color: #f8f9fa;
    }
    .form-section {
      background-color: #f8f9fa;
      padding: 20px;
      border-radius: 5px;
    }
    .form-label {
      font-weight: bold;
    }
    .d-grid button {
      background-color: #f05f25;
      font-weight: bold;
    }
    .order-summary {
      border: 1px solid #ced4da;
      padding: 15px;
      border-radius: 5px;
    }
    .order-summary img {
      width: 100px;
      height: 100px;
      object-fit: cover;
    }
    .payment-method {
      cursor: pointer;
      border: 1px solid #ced4da;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 10px;
    }
    .payment-method input[type="radio"] {
      cursor: pointer;
    }
  </style>
  <title>Checkout Page</title>
</head>
<body>
<div class="container py-4">
  <div class="d-flex flex-column flex-sm-row align-items-center bg-white py-4 px-3">
    <p class="text-decoration-none fs-2 fw-bold text-dark">
      Thông tin vận chuyển
    </p>
  </div>

  <div class="row mt-4">
    <div class="col-lg-6 form-section" style="margin-right: 50px">
      <h5 class="fw-bold">Thông tin chi tiết</h5>
      <p class="text-muted">Hoàn thành thông tin liên hệ của bạn</p>
      <form:form action="/pay/complete" method="post" modelAttribute="user">

        <c:set var="errorName">
          <form:errors path="name" cssClass="invalid-feedback"/>
        </c:set>
        <c:set var="errorAddrress">
          <form:errors path="address" cssClass="invalid-feedback"/>
        </c:set>
        <c:set var="errorPhoneNumber">
          <form:errors path="phoneNumber" cssClass="invalid-feedback"/>
        </c:set>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<%--        <div class="mb-3">--%>
<%--          <label for="email" class="form-label">Email</label>--%>
<%--          <div class="input-group" >--%>
<%--            <span class="input-group-text"><i class="ri-mail-send-line"></i></span>--%>
<%--            <input type="email" class="form-control py-3" id="email"  name="email" value="${email}" />--%>
<%--          </div>--%>
<%--        </div>--%>

        <div class="mb-3">
          <label for="Username" class="form-label">Họ và tên</label>
          <div class="input-group">
            <span class="input-group-text"><i class="ri-id-card-line"></i></span>
            <form:input path="name" type="text" class="form-control py-3 ${not empty errorName ? 'is-invalid' : ''}" id="Username" placeholder="Nhập họ và tên"   name="name" />
            ${errorName}
          </div>
        </div>

        <div class="mb-3">
          <label for="UserPhone" class="form-label">Số điện thoại</label>
          <div class="input-group">
            <span class="input-group-text"><i class="ri-phone-line"></i></span>
            <form:input path="phoneNumber" type="text" class="form-control py-3 ${not empty errorPhoneNumber ? 'is-invalid' : ''}" id="UserPhone" placeholder="Nhập số điện thoại"   name="phoneNumber" />
            ${errorPhoneNumber}
          </div>
        </div>

        <div class="mb-3">
          <label for="billing-address" class="form-label">Địa chỉ giao hàng</label>
          <div class="input-group">
            <span class="input-group-text"><i class="ri-home-8-line"></i></span>
            <form:input path="address" type="text" class="form-control py-3 ${not empty errorAddrress ? 'is-invalid' : ''}" id="billing-address" placeholder="Nhập địa chỉ"  name="address" />
            ${errorAddrress}
          </div>
        </div>

        <div class="mb-3">
          <label for="billing-note" class="form-label">Ghi chú</label>
          <div class="input-group">
            <span class="input-group-text"><i class="ri-sticky-note-line"></i></span>
            <input type="text" class="form-control py-3" id="billing-note" placeholder="Ví dụ: Giao hàng giờ hành chính" name="note" />
          </div>
        </div>

        <div class="py-2 border-top border-bottom">
          <div class="d-flex justify-content-between">
            <span>Tạm tính</span>
            <span class="fw-bold"><fmt:formatNumber value="${totalPrice}" type="number"/> VND</span>
          </div>
          <div class="d-flex justify-content-between">
            <span>Phí giao hàng</span>
            <span class="fw-bold">30,000 VND</span>
          </div>
        </div>

        <div class="d-flex justify-content-between mt-3">
          <span>Tổng</span>
          <span class="fs-4 fw-bold"><fmt:formatNumber value="${totalPrice + 30000}" type="number"/> VND</span>
        </div>

        <div class="d-grid mt-4">
          <button class="btn btn-primary py-3">Đặt hàng</button>
        </div>
    </div>

    <div class="col-lg-5 p-4">
      <h5 class="fw-bold">Tóm tắt đơn hàng</h5>
      <p class="text-muted">Hãy kiểm tra kỹ thông tin của đơn hàng</p>

      <!-- Cart Items -->
      <div class="border p-3 mb-4">
        <c:forEach items="${items}" var="item">
          <div class="d-flex mb-3">
            <img src="/images/product/${item.productVariant.product.name}/${item.productVariant.image}" class="img-fluid rounded me-3" alt="Product" style="width: 100px; height: 100px" />
            <div>
              <h6 class="fw-bold mb-1" style="width: 80%">${item.productVariant.product.name}</h6>
              <div class="mt-2">
                <input style="display: none" name="totalPrice" value="${item.quantity * item.productVariant.price + 30000}">
                <input type="text" class="form-control form-control-sm d-inline" style="width: 40px" value="${item.quantity}" disabled />
                <span class="fw-bold ms-3 fs-5"><fmt:formatNumber type="number" value="${item.quantity * item.productVariant.price}"/>₫</span>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>

      <h5 class="fw-bold">Phương thức thanh toán</h5>
      <div class="container mt-5">
        <form>
          <!-- Phương thức thanh toán -->
          <div class="payment-method position-relative">
            <input class="form-check-input position-absolute top-50 end-0 translate-middle-y me-3"
                   type="radio" name="paymentMethod" id="codRadio" value="cod" checked />
            <label class="d-flex align-items-center" for="codRadio">
              <i class="ri-truck-line fs-2"></i>
              <div class="ms-3">
                <span class="d-block fw-bold">COD</span>
                <p class="text-muted small mb-0">Thanh toán khi nhận hàng</p>
              </div>
            </label>
          </div>

          <div class="payment-method position-relative">
            <input class="form-check-input position-absolute top-50 end-0 translate-middle-y me-3"
                   type="radio" name="paymentMethod" id="onlineRadio" value="online" />
            <label class="d-flex align-items-center" for="onlineRadio">
              <i class="ri-wallet-3-line fs-2"></i>
              <div class="ms-3">
                <span class="d-block fw-bold">Online Payment</span>
                <p class="text-muted small mb-0">Thanh toán Online</p>
              </div>
            </label>
          </div>

        </form>


      </div>
      </form:form>

    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

