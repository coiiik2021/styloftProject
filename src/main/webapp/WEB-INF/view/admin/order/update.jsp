<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="../layout/headerImport.jsp" />
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="../layout/sidebar.jsp" />

  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Create Product</h1>

        <!-- Import header -->
        <!-- <%--<c:import url=""/>--%> -->




        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <form:form action="/admin/order/update" method="post" enctype="multipart/form-data" modelAttribute="order" class="row">



                <div class="mb-3 col-12" style="display: none">

                  <form:input type="text" class="form-control"  path="id"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>

                <div class="mb-3 col-12">

                  <label class="form-label">Name</label>
                  <form:input type="text" class="form-control" disabled="true"  path="user.name"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>



                <div class="mb-3 col-12">
                  <label class="form-label">Total Price</label>
                  <!-- <%--                    ${errorFullName}--%> -->
                  <form:input type="text" class="form-control" disabled="true"  path="total"/>

                </div>
                <div class="mb-3 col-12">
                  <label class="form-label">date</label>
                  <!-- <%--                    ${errorFullName}--%> -->
                  <form:input type="text" class="form-control" disabled="true"  path="date"/>
                </div>
                <div class="mb-3 col-12">
                  <label class="form-label">Status</label>
                  <!-- <%--                    ${errorFullName}--%> -->
                  <form:select path="status" class="form-select mt-2">
                    <option value="PROCESSING" ${order.status.toString() eq "PROCESSING" ? 'selected' : ''}>Xử lý</option>
                    <option value="PAYMENT_FAILED" ${order.status.toString() eq "PAYMENT_FAILED" ? 'selected' : ''}>Thanh toán thất bại</option>

                      <option value="SHIPPING" ${order.status.toString() eq "SHIPPING" ? 'selected' : ''}>Giao Hàng</option>
                      <option value="COMPLETED" ${order.status.toString() eq "COMPLETED" ? 'selected' : ''}>Hoàn Thành</option>
                      <option value="RETURNED" ${order.status.toString() eq "RETURNED" ? 'selected' : ''}>Hoàn Trả Hàng</option>
                    <option value="CANCEL" ${order.status.toString() eq "CANCEL" ? 'selected' : ''}>Huỷ</option>


                  </form:select>

                </div>







                <%--                                <div class="mb-3 col-12">--%>



                <%--                                    <label class="form-label">Name</label>--%>
                <%--                                    <form:input type="text" class="form-control"  path="categories"/>--%>
                <%--                                    <!-- &lt;%&ndash;                    ${errorEmail}&ndash;%&gt; -->--%>
                <%--                                </div>--%>
                <div class = "mb-3 col-12" >
                  <button type="submit" class="btn btn-primary">Submit</button>
                  <a href="/admin/order" class="btn btn-dark">Cancel</a>
                </div>
              </form:form>

            </div>

          </div>

        </div>

      </div>
    </main>
  </div>


  <jsp:include page="../layout/footer.jsp" />
</div>
</body>
</html>
