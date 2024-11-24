
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
                            <div class="d-flex justify-content-between">
                                <h3>Table order</h3>
                                <form action="/admin/order/search" method="get" class="d-flex">
                                    <input class="form-control me-2" type="search" name="query" placeholder="Search by Id" value="${startID}" aria-label="Search">
                                    <button class="btn btn-outline-success" type="submit">Search</button>
                                </form>

                            </div>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr style="text-align: center">
                                    <th scope="col">Id</th>

                                    <th scope="col">Name</th>
                                    <th scope="col">total price</th>
                                    <th scope="col">date </th>
                                    <th scope="col">status </th>

                                    <th scope="col">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="order" items="${orders}">
                                    <style>
                                        .note-new {
                                            position: absolute; /* Đặt note nằm phía trên */
                                            top: -10px; /* Điều chỉnh vị trí trên dòng */
                                            right: 0; /* Căn trái dòng */
                                            background-color: red; /* Màu nền đỏ nổi bật */
                                            color: white; /* Chữ trắng */
                                            font-size: 10px; /* Chữ nhỏ gọn */
                                            font-weight: bold; /* Chữ đậm */
                                            padding: 2px 5px; /* Tạo khoảng cách cho note */
                                            border-radius: 3px; /* Bo tròn góc của note */
                                        }
                                    </style>

                                    <tr>
                                        <th scope="row" style="position: relative;">
                                            #${order.id.substring(0,5)}
                                                ${order.announceOrder ? '<span class="note-new">NEW</span>' : ''}
                                        </th>

                                        <th scope="row">${order.user.name}</th>
                                        <td><fmt:formatNumber value="${order.total}" type="number"/> Đ</td>
                                        <td>${order.date}</td>
                                        <td style="color: ${order.status.toString() == 'RETURN' || order.status.toString() == 'CANCEL'
                                                                || order.status.toString() == 'PAYMENT_FAILED'
                                                            ? 'red' : 'green'
                                                            }"
                                        >${order.status}</td>

                                        <td style="text-align: center">
                                            <a href="/admin/order/detail/${order.id}" class="btn btn-danger">Detail</a>
                                            <a href="/admin/order/update/${order.id}" class="btn btn-warning">Update</a>

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
                                           href="/admin/order?page=${currentPage - 1}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                        <li class="page-item">
                                            <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                                               href="/admin/order?page=${loop.index}">${loop.index}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item">
                                        <a class="${currentPage eq totalPages ? 'disabled' : ''} page-link"
                                           href="/admin/order?page=${currentPage + 1}" aria-label="Next">
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
