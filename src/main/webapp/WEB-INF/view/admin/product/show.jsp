<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="../layout/headerImport.jsp" />
<link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="../layout/sidebar.jsp" />

  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <div class="container mt-5">
          <div class="row">
            <div class="col-md-12 col-12 mx-auto">
              <div class="d-flex justify-content-between">
                <h3>Table product</h3>

                <!-- Form tìm kiếm -->
                <form action="/admin/product/search" method="get" class="d-flex">
                  <input class="form-control me-2" type="search" name="query" placeholder="Search by name" value="${nameSearch}" aria-label="Search">
                  <button class="btn btn-outline-success" type="submit">Search</button>
                </form>

                <a href="/admin/product/create" class="btn btn-primary">Create a product</a>
              </div>

              <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr style="text-align: center">
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Category</th>
                  <th scope="col">Status</th>

                  <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                  <tr>
                    <th scope="row">${product.name}</th>
                    <td class="text-center">${product.description}</td>
                    <td class="text-center">${product.category.name}</td>
                    <td class="text-center" style=" color: ${product.status ? 'green' : 'red'}">${product.status ? 'Hiện' : 'Ẩn'}</td>


                    <td style="text-align: center">
                      <a href="/admin/product/${product.id}" class="btn btn-success">View</a>
                      <a href="/admin/product/update/${product.id}" class="btn btn-warning">Update</a>
                      <a href="/admin/product/delete/${product.id}" class="btn btn-danger">Delete</a>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
              <c:if test="${totalPages > 1}">

              <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                  <li class="page-item">
                    <a class="${1 eq currentPage ? 'disabled' : ''} page-link"
                       href="/admin/product?page=${currentPage - 1}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>
                  <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                    <li class="page-item">
                      <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                         href="/admin/product?page=${loop.index}">${loop.index}</a>
                    </li>
                  </c:forEach>
                  <li class="page-item">
                    <a class="${currentPage eq totalPages ? 'disabled' : ''} page-link"
                       href="/admin/product?page=${currentPage + 1}" aria-label="Next">
                      <span aria-hidden="true">&raquo;</span>
                    </a>
                  </li>
                </ul>
              </nav>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>
</body>
</html>
