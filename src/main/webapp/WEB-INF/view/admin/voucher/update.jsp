<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<jsp:include page="../layout/headerImport.jsp" />
z
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="../layout/sidebar.jsp" />

  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Update Voucher</h1>
        <hr />

        <!-- Import header -->
        <!-- <%--<c:import url=""/>--%> -->




        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <form:form action="/admin/voucher/update" method="post" enctype="multipart/form-data" modelAttribute="voucher" class="row">


                <div class="mb-3 col-12" style="display: none">

                  <label class="form-label">id</label>
                  <form:input type="text" class="form-control" path="id"/>
                  <form:input type="text" class="form-control" path="code"/>

                  <!-- <%--                    ${errorEmail}--%> -->
                </div>

                <c:set var="errorDiscountValue">
                  <form:errors path="discountValue" cssClass="invalid-feedback"/>
                </c:set>
                <c:set var="errorStartDate">
                  <form:errors path="startDate" cssClass="invalid-feedback"/>
                </c:set>
                <c:set var="errorEndDate">
                  <form:errors path="endDate" cssClass="invalid-feedback"/>
                </c:set>


                <div class="mb-3 col-12">

                  <label class="form-label">Name</label>
                  <form:input type="text" class="form-control" disabled="true"  path="code"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>



                <div class="mb-3 col-12">
                  <label class="form-label">Discount</label>
                  <form:input type="text" class="form-control ${not empty errorDiscountValue ? 'is-invalid' : ''}"  path="discountValue"/>
                  <!-- <%--                    ${errorFullName}--%> -->
                    ${errorDiscountValue}
                </div>

                <div class="mb-3 col-12">
                  <label class="form-label">Ngày bắt đầu</label>
                  <form:input path="startDate" type="date" class="form-control ${not empty errorStartDate ? 'is-invalid' : ''}" />
                    ${errorStartDate}
                </div>
                <div class="mb-3 col-12">
                  <label class="form-label">Ngày kết thúc </label>
                  <form:input path="endDate" type="date" class="form-control ${not empty errorEndDate ? 'is-invalid' : ''}" />
                    ${errorEndDate}
                </div>






                <%--                                <div class = "col-12 mb-3">--%>
                <%--                                    <img style="max-height: 250px; display: none;" alt="avatar-preview" id="avatarPreview"/>--%>
                <%--                                </div>--%>
                <%--                                <div class="mb-3 col-12">--%>

                <%--                                    <label class="form-label">Name</label>--%>
                <%--                                    <form:input type="text" class="form-control"  path="categories"/>--%>
                <%--                                    <!-- &lt;%&ndash;                    ${errorEmail}&ndash;%&gt; -->--%>
                <%--                                </div>--%>
                <div class = "mb-3 col-12 col-md-6">
                  <button type="submit" class="btn btn-primary">Submit</button>
                  <a href="/admin/voucher" class ="btn btn-dark">Cancel</a>

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
