<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Lựa Chọn Vest Nữ Trung Niên</title>
  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
  />
  <link
          href="https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css"
          rel="stylesheet"
  />
  <style>
    /* CSS code here */
    .article-date {
      font-size: 20px;
      font-weight: bold;
    }
    .article-title {
      color: #ef750b;
      font-size: 1.5rem;
      font-weight: bold;
    }
    .centered-image {
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 1rem 0;
    }
    .main-image {
      height: auto;
      max-width: 100%;
      width: 100%;
      object-fit: cover;
    }
    /* Rest of the styles */
  </style>
</head>
<link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
<body>
<!--Body-->
<jsp:include page="../layout/header.jsp" />
<main>
  <body>
  <div class="container mt-4" style="padding-left: 60px; padding-right: 60px">
    <div class="col">
      <!-- Main Content -->
      <div>
        <!-- Article Header -->
        <div class="d-flex align-items-center gap-3 mb-3">
          <div
                  class="article-date"
                  style="border-right: #666 solid 1px; padding-right: 8px"
          >
            01/11
            <br />
            <i class="ri-time-line"></i>
            <small>2024</small>
          </div>
          <h1 class="article-title" style="font-size: 40px;">
            Hướng Dẫn Chi Tiết Cách Chọn Áo Vest Nữ Trung Niên
          </h1>
        </div>

        <!-- Dynamic Content Example -->
        <%
          String date = "01/11";
          String year = "2024";
        %>
        <div class="d-flex align-items-center gap-3 mb-3">
          <div
                  class="article-date"
                  style="border-right: #666 solid 1px; padding-right: 8px"
          >
            <%= date %>
            <br />
            <i class="ri-time-line"></i>
            <small><%= year %></small>
          </div>
          <h1 class="article-title" style="font-size: 40px;">
            Hướng Dẫn Chi Tiết Cách Chọn Áo Vest Nữ Trung Niên
          </h1>
        </div>

        <!-- Static content remains unchanged -->
        <p>
          Áo vest nữ trung niên là trang phục đặc biệt cần có trong tủ đồ của
          mỗi quý cô. Là item mang lại sự thanh lịch và sang trọng...
        </p>

        <!-- Image Example -->
        <div class="centered-image">
          <img
                  src="https://file.hstatic.net/1000178779/article/lua_chon_ao_vest_nu_trung_nien_f652e019b9de4422a9971060182f7012.jpg"
                  alt="Vest Nữ Trung Niên"
                  class="main-image"
          />
        </div>
        <p class="image-caption">Bộ sưu tập Vest nhà Styloft</p>

        <!-- Example of looping with JSP -->
        <h2 class="h4 mt-4 mb-4">Sản Phẩm Khác</h2>
        <%
          String[] products = {
                  "Áo vest cổ sen - O65B24Q008",
                  "Áo vest 2 lớp xẻ lưng phối kẻ - V65B23T010",
                  "Áo vest 2 lớp dài eo - L65B21T006"
          };
          String[] prices = { "559,300đ", "990,000đ", "299,000đ" };
          String[] oldPrices = { "799,000đ", "", "799,000đ" };
          String[] discounts = { "", "-30%", "-63%" };
          String[] images = {
                  "https://product.hstatic.net/1000178779/product/o65b24q008_40782221_1_401c07aff68e421f97113b0afb13ec0d_master.jpg",
                  "https://product.hstatic.net/1000178779/product/v65b23t010_40774821_16_ba6bd89b76f74a98bed07b93baa849af_master.jpg",
                  "https://product.hstatic.net/1000178779/product/o61b24q003-f1511-o62r24q012-s3300-_1__dd6a8539af854ca0be847316fa6523e4_1024x1024.jpg"
          };
        %>
        <div class="container">
          <% for (int i = 0; i < products.length; i++) { %>
          <div class="card">
            <img src="<%= images[i] %>" alt="<%= products[i] %>" />
            <h4><%= products[i] %></h4>
            <p>
              <span class="price"><%= prices[i] %></span>
              <% if (!oldPrices[i].isEmpty()) { %>
              <span class="old-price"><%= oldPrices[i] %></span>
              <% } %>
              <% if (!discounts[i].isEmpty()) { %>
              <span class="discount"><%= discounts[i] %></span>
              <% } %>
            </p>
            <button class="buy-now">Mua ngay</button>
          </div>
          <% } %>
        </div>
      </div>
    </div>
  </div>
  </body>
</main>
<jsp:include page="../layout/footer.jsp" />
</html>
