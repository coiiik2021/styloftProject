
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
        <h1 class="mt-4">Create Variant</h1>
        <hr />

        <!-- Import header -->
        <!-- <%--<c:import url=""/>--%> -->




        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <form:form action="/admin/item/create" method="post" enctype="multipart/form-data" modelAttribute="newItem" class="row">
                  <p style="color: red">${errorProductVariantExists}</p>

                  <div class="mb-3 col-12 ">
                      <lable class ="form-label">Product </lable>
                      <form:select path="product.name" class="form-select mt-2" >
                          <c:forEach var="product" items="${products}">
                              <option value="${product.name}" ${product.name == newItem.product.name || product.name == productSelected.name ? 'selected' : ''}>${product.name}</option>
                          </c:forEach>

                      </form:select>
                  </div>

                  <div class="mb-3 col-12 ">
                      <lable class ="form-label">Color </lable>
                      <form:select path="color.name" class="form-select mt-2" >
                          <c:forEach var="color" items="${colors}">
                              <option value="${color.name}" ${color.name == newItem.color.name ? 'selected' : ''}>${color.name}</option>
                          </c:forEach>

                      </form:select>
                  </div>

                  <div class="mb-3 col-12 ">
                      <lable class ="form-label">Size </lable>
                      <form:select path="size.name" class="form-select mt-2" >
                          <c:forEach var="size" items="${sizes}">
                              <option value="${size.name}" ${size.name == newItem.size.name ? 'selected' : ''}>${size.name}</option>
                          </c:forEach>

                      </form:select>
                  </div>

                <div class="mb-3 col-12 col-mb-6">

                  <label class="form-label">Quantity</label>
                  <form:input type="text" class="form-control"  path="quantity"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>
                  <div class="mb-3 col-12 col-md-6">
                      <label for="imageItem" class="form-label">Image</label>
                      <input class="form-control" type="file" id="imageItem" name="imageItem" accept=".png, .jpg, . jpeg">
                  </div>
                <div class="mb-3 col-12 col-mb-6">

                  <label class="form-label">Price</label>
                  <form:input type="text" class="form-control"  path="price"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>




                <div class = "mb-3 col-12 col-md-6">
                  <button type="submit" class="btn btn-primary">Submit</button>
                  <a href="/admin${productSelected == null ? '/item' : '/product/' }${productSelected.id}" class ="btn btn-dark">Cancel</a>

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
