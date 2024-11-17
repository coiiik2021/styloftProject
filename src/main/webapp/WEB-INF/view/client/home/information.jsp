<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thông tin tài khoản</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .form-container {
      background-color: white;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      display: none; /* Ẩn form mặc định */
    }
    .section-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin-bottom: 15px;
    }
    .btn-save {
      background-color: #20c997;
      color: white;
    }
    .btn-save:hover {
      background-color: #17a589;
    }
    .sidebar {
      margin-right: 20px;
    }
  </style>
</head>
<body>
<jsp:include page="../layout/header.jsp" />


<div class="container" style="margin-top: 100px ">
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-3">
      <ul class="list-group sidebar">
        <li class="list-group-item active" onclick="showSection('account-info')">Thông tin tài khoản</li>
        <li class="list-group-item" onclick="showSection('order-history')">Lịch sử đơn hàng</li>
        <li class="list-group-item" onclick="showSection('account-address')">Số địa chỉ</li>
<%--        <li class="list-group-item">Đánh giá và phản hồi</li>--%>
<%--        <li class="list-group-item">Chính sách và câu hỏi thường gặp</li>--%>
<%--        <li class="list-group-item">Đăng xuất</li>--%>
      </ul>
    </div>

    <!-- Form Thông tin tài khoản -->
    <div class="col-md-9 form-container" id="account-info" style="display: block;">
      <div>
        <h2 class="mb-4">Thông tin tài khoản</h2>
        <form:form action="/account/update-information"  enctype="multipart/form-data" method="post" modelAttribute="user">
          <div class="row mb-3">
            <c:set var="errorName">
              <form:errors path="name" cssClass="invalid-feedback" />
            </c:set>
            <c:set var="erroPhone">
              <form:errors path="phoneNumber" cssClass="invalid-feedback" />
            </c:set>
            <c:set var="errorBirthDay">
              <form:errors path="birthDay" cssClass="invalid-feedback" />
            </c:set>

            <div class="col-md-6">

              <img src="/images/avatar/${not empty user.image ?  user.image : 'default.png'}" alt="${user.image}" style="width: 100px; height: auto;">

            </div>

            <div class="mb-3 col-12 col-md-6">
              <label for="imageAvatar" class="form-label">Image</label>
              <input class="form-control" type="file" id="imageAvatar" name="imageAvatar" accept=".png, .jpg, . jpeg">
            </div>

            <div class="col-md-6">
              <label for="fullName" class="form-label">Họ và tên</label>

              <form:input path="name" type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}" id="fullName" placeholder="Nguyễn Anh Dũng" />
                ${errorName}

            </div>
          </div>
          <div class="row mb-3">

            <div class="col-md-6">
              <label for="phone" class="form-label">Số điện thoại</label>

              <form:input path="phoneNumber" type="text" class="form-control ${not empty erroPhone ? 'is-invalid' : ''}" id="phone" placeholder="0878888424" />
                ${erroPhone}

            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label>Giới tính</label><br>

                <!-- Radio Nam -->
                <form:radiobutton path="sex" id="male" value="1"
                                  checked="${user.sex == 1}" />
                <label for="male">Nam</label>

                <!-- Radio Nữ -->
                <form:radiobutton path="sex" id="female" value="0"
                                  checked="${user.sex == 0}" />
                <label for="female">Nữ</label>

                <!-- Radio Khác giới -->
                <form:radiobutton path="sex" id="other" value="-1"
                                  checked="${user.sex == -1}" />
                <label for="other">Khác giới</label>
              </div>

            </div>
          </div>
          <div class="row mb-4">
            <div class="col-md-6">
              <label for="dob" class="form-label">Ngày sinh</label>
              <form:input path="birthDay" type="date" class="form-control ${not empty errorBirthDay ? 'is-invalid' : ''}" id="dob" />
                ${errorBirthDay}

            </div>
          </div>
          <h4 class="mb-3">Thông tin đăng nhập</h4>
          <div class="row mb-4">
            <div class="col-md-6">
              <label for="email" class="form-label">Email</label>
              <input path="email" disabled type="email" class="form-control" id="email"  value="${email}">
            </div>
          </div>
          <button type="submit" class="btn btn-save">Lưu thông tin</button>
        </form:form>
      </div>
    </div>

    <!-- Form Lịch sử đơn hàng -->
    <div class="col-md-9 form-container" id="order-history">
      <div>
        <h2 class="mb-4">Lịch sử đơn hàng</h2>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">Id</th>

            <th scope="col">Ngày đặt</th>
            <th scope="col">Tình trạng</th>
            <th scope="col">Tổng tiền</th>
            <th scope="col">Chi Tiết</th>


          </tr>
          </thead>
          <tbody>
          <c:forEach items="${orders}" var="order">
          <tr>

            <td>  <a href="/order/${order.id}" > #${order.id.substring(0, 5)} </a></td>

            <td>${order.date}</td>
            <td style="color: ${order.status.toString() == 'RETURN' || order.status.toString() == 'CANCEL' ? 'red' : 'green'}" >${order.status}</td>
            <td><fmt:formatNumber type="number" value="${order.total}" /> VND</td>
            <td> <a href="/order/${order.id}" class = "btn btn-primary">Xem</a> </td>



          </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="col-md-9 form-container" id="account-address">
      <div>
        <h1>Chọn danh sách tỉnh</h1>
        <form action="">
          <select name="" id="province">
          </select>
          <select name="" id="district">
            <option  value="">chọn quận</option>
          </select>
          <select name="" id="ward">
            <option   value="">chọn phường</option>
          </select>
        </form>


        <h2 id="result"></h2>
      </div>
    </div>

  </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.26.1/axios.min.js" integrity="sha512-bPh3uwgU5qEMipS/VOmRqynnMXGGSRv+72H/N260MQeXZIK4PG48401Bsby9Nq5P5fz7hy5UGNmC/W1Z51h2GQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script>
  const host = "https://provinces.open-api.vn/api/";

  // Hàm gọi API lấy dữ liệu tỉnh/thành phố
  var callAPI = (api) => {
    return axios.get(api)
            .then((response) => {
              if (Array.isArray(response.data)) {
                renderData(response.data, "province");
              } else {
                console.error("Province data is not an array:", response.data);
              }
            })
            .catch((error) => {
              console.error("Error fetching provinces:", error);
            });
  };

  // Hàm gọi API lấy dữ liệu quận/huyện
  var callApiDistrict = (api) => {
    return axios.get(api)
            .then((response) => {
              if (Array.isArray(response.data.districts)) {
                renderData(response.data.districts, "district");
              } else {
                console.error("Districts data is not an array:", response.data);
              }
            })
            .catch((error) => {
              console.error("Error fetching districts:", error);
            });
  };

  // Hàm gọi API lấy dữ liệu phường/xã
  var callApiWard = (api) => {
    return axios.get(api)
            .then((response) => {
              if (Array.isArray(response.data.wards)) {
                renderData(response.data.wards, "ward");
              } else {
                console.error("Wards data is not an array:", response.data);
              }
            })
            .catch((error) => {
              console.error("Error fetching wards:", error);
            });
  };

  // Hàm hiển thị dữ liệu vào <select>
  var renderData = (array, select) => {
    if (!Array.isArray(array)) {
      console.error("Expected an array, got:", array);
      return;
    }

    let row = '<option disable value="">chọn</option>';
    array.forEach(function (element) {
      row += '<option value="' + element.code + '">' + element.name + '</option>';
    });
    document.querySelector("#" + select).innerHTML = row;
  };

  // Hàm in kết quả lựa chọn
  var printResult = () => {
    if ($("#district").val() != "" && $("#province").val() != "" &&
            $("#ward").val() != "") {
      let result = $("#province option:selected").text() +
              " | " + $("#district option:selected").text() + " | " +
              $("#ward option:selected").text();
      $("#result").text(result);
    }
  };

  // Gọi API để lấy danh sách tỉnh/thành phố ban đầu
  callAPI(host + "?depth=1");

  // Xử lý sự kiện khi thay đổi tỉnh/thành phố
  $("#province").change(function () {
    const provinceCode = $("#province").val();
    if (provinceCode) {
      callApiDistrict(host + "p/" + provinceCode + "?depth=2");
    } else {
      document.querySelector("#district").innerHTML = '<option disable value="">chọn</option>';
      document.querySelector("#ward").innerHTML = '<option disable value="">chọn</option>';
    }
    printResult();
  });

  // Xử lý sự kiện khi thay đổi quận/huyện
  $("#district").change(function () {
    const districtCode = $("#district").val();
    if (districtCode) {
      callApiWard(host + "d/" + districtCode + "?depth=2");
    } else {
      document.querySelector("#ward").innerHTML = '<option disable value="">chọn</option>';
    }
    printResult();
  });

  // Xử lý sự kiện khi thay đổi phường/xã
  $("#ward").change(function () {
    printResult();
  });
</script>


<script>



  function showSection(sectionId) {
    // Ẩn tất cả form
    document.querySelectorAll('.form-container').forEach(function (section) {
      section.style.display = 'none';
    });
    // Hiển thị form được chọn
    document.getElementById(sectionId).style.display = 'block';

    // Đặt class 'active' cho phần được chọn trong sidebar
    document.querySelectorAll('.list-group-item').forEach(function (item) {
      item.classList.remove('active');
    });
    event.target.classList.add('active');
  }
</script>

<jsp:include page="../layout/footer.jsp" />

</body>
</html>
