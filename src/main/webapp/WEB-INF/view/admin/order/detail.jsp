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

                <div class="container mt-5">
                    <div class="row d-flex">

                        <div class="col-4 bg-light rounded p-4 shadow-sm">
                            <h3 class="text mb-4">Name: <br><strong>${order.user.name}</strong></h3>
                            <h4 class="text mb-4">Total: <strong>${order.total} VNĐ</strong></h4>
                            <h4 class="text mb-4">Date: <strong>${order.date}</strong></h4>
                            <h4 class="text mb-4">
                                Status:
                                <span class="text-success"><strong>${order.status}</strong></span>

                            </h4>
                            <hr class="my-4"/>
                        </div>

                        <div class="col-8 fs-5 justify-content-center">
                            <h3 class="text-dark mb-3 text-center" style="margin-left: 6px">List Product</h3>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th scope="col">Product Item Name</th>
                                    <th class="text-center" scope="col">Quantity</th>
                                    <th class="text-center" scope="col">Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="detail" items="${details}">
                                    <tr>
                                        <td>${detail.productVariant.product.name}</td>
                                        <td style="text-align: center;">${detail.quantity}</td>
                                        <td style="text-align: center;">${detail.price} VNĐ</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <div class="mb-3 col-12 d-flex justify-content-end">
                        <a href="/admin/order" class="btn btn-warning fs-5">Cancel</a>
                    </div>

                </div>

            </div>
        </main>
    </div>
</div>
</body>
</html>
