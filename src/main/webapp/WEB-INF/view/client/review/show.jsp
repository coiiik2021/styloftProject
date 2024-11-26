<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Star Rating</title>
  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Stylesheet -->
  <link rel="stylesheet" href="style.css" />
  <style>
    * {
      padding: 0;
      margin: 0;
      box-sizing: border-box;
      font-family: "Poppins", sans-serif;
    }
    .bg {
      position: absolute;
      height: 50vh;
      width: 100vw;
      background: linear-gradient(#fe3b5a, #fd7914);
    }
    .wrapper {
      font-size: 1.2ee;
      width: 25em;
      position: absolute;
      transform: translate(-50%, -50%);
      left: 50%;
      top: 50%;
      background-color: #ffffff;
      border-radius: 0.5em;
      padding: 4em 2em;
      box-shadow: 0 1.25em 2.5em rgba(18, 0, 76, 0.15);
    }
    .container {
      width: 100%;
      display: flex;
      justify-content: space-around;
    }
    .fa-star {
      font-size: 1.5em;
      color: #ffd700;
    }
    .number {
      display: block;
      text-align: center;
    }
    #submit {
      display: block;
      position: relative;
      background: linear-gradient(#fe3b5a, #fd7914);
      border: none;
      padding: 0.8em 2em;
      color: #ffffff;
      font-size: 1.2em;
      border-radius: 2em;
      margin: 1em auto 0 auto;
      cursor: pointer;
    }
    #submit:disabled {
      cursor: not-allowed;
    }
    #message {
      text-align: center;
      margin-bottom: 2em;
    }
    #submit-section {
      position: absolute;
      height: 100%;
      width: 100%;
      background-color: #ffffff;
      top: 0;
      left: 0;
      place-items: center;
      border-radius: 0.5em;
    }
    .hide {
      display: none;
    }
    .show {
      display: grid;
    }
    body {
      background-color: #f8f9fa;
      padding-top: 60px;
    }
    /* Ẩn input radio */
    input[type="radio"] {
      display: none;
    }

    /* Thiết kế cho nhãn liên kết với input */
    .star-container {
      display: inline-block;
      cursor: pointer;
      font-size: 2em; /* Kích thước ngôi sao */
      color: #ccc; /* Màu mặc định của ngôi sao */
      transition: color 0.3s ease;
    }

    /* Khi radio được chọn, thay đổi màu ngôi sao */
    input[type="radio"]:checked + label {
      color: #ffd700; /* Màu vàng cho ngôi sao được chọn */
    }

    /* Hiệu ứng hover trên nhãn */
    .star-container:hover label {
      color: #ffab00; /* Màu khi di chuột */
    }

    /* Thay đổi màu của tất cả các ngôi sao bên trái khi hover */
    .star-container:hover ~ .star-container label {
      color: #ccc;
    }
    .comment-area textarea{
      resize: none;
      border: 1px solid #ad9f9f;
    }
    /* Header với chiều cao cố định */
    header {
      position: fixed;
      width: 100%;
      top: 0;
      left: 0;
      height: 60px; /* Chiều cao cố định cho header */
      z-index: 1000; /* Đảm bảo header nằm trên các phần tử khác */
      background-color: #fff;
    }

    /* Wrapper với vị trí relative và khoảng cách từ trên */
    .wrapper {
      font-size: 1.2em;
      width: 25em;
      position: relative; /* Sử dụng relative để nó không bị che mất */
      margin-top: 80px; /* Khoảng cách giữa header và wrapper (hoặc có thể sử dụng padding-top) */
      left: 50%;
      transform: translateX(-50%);
      background-color: #ffffff;
      border-radius: 0.5em;
      padding: 50px;
      box-shadow: 0 1.25em 2.5em rgba(18, 0, 76, 0.15);
    }






    /* Media query để thay đổi bố cục cho màn hình nhỏ */
    @media (max-width: 768px) {
      .wrapper {
        padding-top: 10px;   /* Giảm padding-top khi màn hình nhỏ */
      }

      .wrapper img {
        max-width: 100%;     /* Hình ảnh thay đổi kích thước linh hoạt */
      }
    }
  </style>
</head>

<body style="padding-top: 0px;">
<jsp:include page="../layout/header.jsp" />
<main>
  <div class="bg"></div>
  <form:form action="/order/detail/review" method="post" modelAttribute="newReview">
  <div style="display: none">
    <label>
      <input value="${detail.id}" name="idOrderDetail" type="text">
    </label>
  </div>
  <div class="wrapper">
    <h3 class="text-center">Review</h3>
    <hr>
    <div class="text-center mb-2">
      <img src="/images/product/${detail.productVariant.product.name}/${detail.productVariant.image}"
           class="rounded img-fluid" alt="${detail.productVariant.product.name}">
    </div>
    <div class="container">
      <!-- Ngôi sao 1 -->
      <input type="radio" name="star" id="rs1" value="1" ${newReview.star == 1 ? 'checked' : ''}>
      <label for="rs1" class="star-container"><i class="fa-regular fa-star"></i></label>

      <!-- Ngôi sao 2 -->
      <input type="radio" name="star" id="rs2" value="2" ${newReview.star == 2 ? 'checked' : ''}>
      <label for="rs2" class="star-container"><i class="fa-regular fa-star"></i></label>

      <!-- Ngôi sao 3 -->
      <input type="radio" name="star" id="rs3" value="3" ${newReview.star == 3 ? 'checked' : ''}>
      <label for="rs3" class="star-container"><i class="fa-regular fa-star"></i></label>

      <!-- Ngôi sao 4 -->
      <input type="radio" name="star" id="rs4" value="4" ${newReview.star == 4 ? 'checked' : ''}>
      <label for="rs4" class="star-container"><i class="fa-regular fa-star"></i></label>

      <!-- Ngôi sao 5 -->
      <input type="radio" name="star" id="rs5" value="5" ${newReview.star == 5 ? 'checked' : ''}>
      <label for="rs5" class="star-container"><i class="fa-regular fa-star"></i></label>
    </div>

    <p id="message">Rate Your Experience</p>
    <form:textarea path="description" placeholder="Leave a comment here" class="form-control w-100" style="height: 100px;"></form:textarea>

    <button type="submit" id="submit">Submit</button>
    </form:form>
    <div id="submit-section" class="hide">
      <p id="submit-message">Thanks for your feedback</p>
    </div>
  </div>
</main>
<!-- Script -->
<script>
  const starContainer = document.querySelectorAll(".star-container");
  const submitButton = document.querySelector("#submit");
  const message = document.querySelector("#message");
  const submitSection = document.querySelector("#submit-section");

  // Thêm sự kiện click vào mỗi container
  starContainer.forEach((element, index) => {
    element.addEventListener("click", () => {
      submitButton.disabled = false;

      // Cập nhật trạng thái sao
      ratingUpdate(index);
    });
  });

  // Hàm cập nhật trạng thái ngôi sao
  const ratingUpdate = (selectedIndex) => {
    // Lặp qua tất cả các ngôi sao
    starContainer.forEach((star, index) => {
      const icon = star.querySelector("i");
      if (index <= selectedIndex) {
        // Kích hoạt các ngôi sao đã chọn
        star.classList.add("active");
        star.classList.remove("inactive");
        icon.className = "fa-star fa-solid";
      } else {
        // Đặt lại các ngôi sao còn lại
        star.classList.remove("active");
        star.classList.add("inactive");
        icon.className = "fa-star fa-regular";
      }
    });

    // Cập nhật thông báo theo số sao đã chọn
    const messages = ["Terrible", "Bad", "Satisfied", "Good", "Excellent"];
    message.innerText = messages[selectedIndex];
  };

  // Thiết lập trạng thái ban đầu
  window.onload = () => {
    submitButton.disabled = true;
    submitSection.classList.add("hide");
  };

</script>
</body>
</html>
