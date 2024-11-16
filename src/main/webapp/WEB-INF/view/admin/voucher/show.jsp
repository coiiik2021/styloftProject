
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html"
                                                         pageEncoding="UTF-8" %>

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
                            <div class="d-flex justify-content-between">
                                <h3>Table color</h3>
                                <a href="/admin/voucher/create" class="btn btn-primary">Create voucher</a>
                            </div>
                            <table class="table table-bordered table-hover mt-3">
                                <thead>
                                <tr style="text-align: center; vertical-align: middle;">
                                    <th scope="col">Code</th>
                                    <th scope="col">Discount</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Quantity</th>

                                    <th scope="col">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="voucher" items="${vouchers}">
                                    <tr style="text-align: center; vertical-align: middle;">
                                        <th scope="row">${voucher.code}</th>
                                        <td><fmt:formatNumber value="${voucher.discountValue}" type="number"/>%</td>
                                        <td><fmt:formatNumber value="${voucher.quantity}" type="number"/></td>

                                        <td style="color:${voucher.active ? 'green' : 'red'} ;">  ${voucher.active ? 'Còn' : 'Hết'}</td>


                                        <td style="text-align: center">
                                            <a href="/admin/voucher/update/${voucher.id}" class="btn btn-warning">Update</a>
                                            <a href="/admin/voucher/active/${voucher.id}" class="btn btn-danger">Active</a>
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
                                           href="/admin/color?page=${currentPage - 1}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                        <li class="page-item">
                                            <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                                               href="/admin/color?page=${loop.index}">${loop.index}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item">
                                        <a class="${currentPage eq totalPages ? 'disabled' : ''} page-link"
                                           href="/admin/color?page=${currentPage + 1}" aria-label="Next">
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
    <jsp:include page="../layout/footer.jsp" />
</div>
</body>
</html>
