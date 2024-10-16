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

                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-8 col-12 mx-auto bg-light rounded p-4 shadow-sm">
                            <h3 class="text-primary">Name: <strong>${order.user.name}</strong></h3>
                            <h4 class="text-success">Total: <strong>${order.total} Đ</strong></h4>
                            <h4 class="text-secondary">Date: <strong>${order.date}</strong></h4>
                            <h4 class="text-danger">Status: <strong>${order.status}</strong></h4>

                            <hr class="my-4" />
                        </div>

                        <div class="col-md-12 col-12 mx-auto">
                            <div class="d-flex justify-content-between mb-3">
                                <h3 class="text-dark">List Product</h3>
                            </div>
                            <table class="table table-bordered table-hover">
                                <thead class="table-primary">
                                <tr style="text-align: center">
                                    <th scope="col">Product Item Name</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="detail" items="${details}">
                                    <tr>
                                        <td>${detail.productItem.product.name}</td>
                                        <td style="text-align: center;">${detail.quantity}</td>
                                        <td style="text-align: center;">${detail.price} Đ</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="mb-3 col-12">
                            <a href="/admin/order" class="btn btn-dark">Cancel</a>
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
