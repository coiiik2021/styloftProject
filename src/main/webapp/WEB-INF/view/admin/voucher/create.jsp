<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<jsp:include page="../layout/headerImport.jsp" />

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="../layout/sidebar.jsp" />

  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Create voucher</h1>
        <hr />

        <!-- Import header -->
        <!-- <%--<c:import url=""/>--%> -->



        <c:if test="${not empty voucherExists}">
          <div style="color: red;">${voucherExists}</div>
        </c:if>
        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <form:form action="/admin/voucher/create" method="post" enctype="multipart/form-data" modelAttribute="newVoucher" class="row">


                <c:set var="errorCode">
                  <form:errors path="code" cssClass="invalid-feedback"/>
                </c:set>

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

                  <label class="form-label">Code</label>
                  <form:input type="text" class="form-control ${not empty errorCode ? 'is-invalid' : ''}"  path="code"/>

                  ${errorCode}
                </div>



                <div class="mb-3 col-12">
                  <label class="form-label">Discount (%)</label>
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
