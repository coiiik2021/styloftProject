
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="../layout/headerImport.jsp" />
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
                            <div class="d-flex justify-content-between" style="margin-bottom: 20px;">
                                <h3>Table product Item</h3>
                                <form action="/admin/item/search" method="get" class="d-flex">
                                    <input class="form-control me-2 w-auto" type="search" name="query" placeholder="Search by name product" value="${nameSearch}" aria-label="Search">
                                    <button class="btn btn-outline-success" type="submit">Search</button>
                                </form>
                                <a href="/admin/item/create" class="btn btn-primary">Create Product Item</a>

                            </div>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr style="text-align: center">
                                    <th scope="col">Product</th>
                                    <th scope="col">color</th>
                                    <th scope="col">Size</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Image</th>
                                    <th scope="col">Action</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="productItem" items="${productItems}">
                                    <tr style="text-align: center; vertical-align: middle; ">
                                        <th scope="row">${productItem.product.name}</th>
                                        <td>${productItem.color.name}</td>
                                        <td>${productItem.size.name}</td>
                                        <td>${productItem.quantity}</td>

                                        <td>${productItem.price}</td>
                                        <td style="text-align: center">
                                            <img src="/images/product/${productItem.product.name}/${productItem.image}" alt="${productItem.product.name}" style="width: 100px; height: auto;">
                                        </td>

                                        <td style="text-align: center">
                                            <a href="/admin/item/update/${productItem.id}" class="btn btn-warning">Update</a>
                                            <a href="/admin/item/delete/${productItem.id}" class="btn btn-danger">Delete</a>
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
                                           href="/admin/item?page=${currentPage - 1}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                        <li class="page-item">
                                            <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                                               href="/admin/item?page=${loop.index}">${loop.index}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item">
                                        <a class="${currentPage eq totalPages ? 'disabled' : ''} page-link"
                                           href="/admin/item?page=${currentPage + 1}" aria-label="Next">
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
