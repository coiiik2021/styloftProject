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
                <h1 class="mt-4">Create Product</h1>

                <!-- Import header -->
                <!-- <%--<c:import url=""/>--%> -->




                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <form:form action="/admin/product/update" method="post" enctype="multipart/form-data" modelAttribute="product" class="row">



                                <div class="mb-3 col-12" style="display: none">

                                    <form:input type="text" class="form-control"  path="id"/>
                                    <!-- <%--                    ${errorEmail}--%> -->
                                </div>

                                <div class="mb-3 col-12">

                                    <label class="form-label">Name</label>
                                    <form:input type="text" class="form-control"  path="name"/>
                                    <!-- <%--                    ${errorEmail}--%> -->
                                </div>



                                <div class="mb-3 col-12">
                                    <label class="form-label">Description</label>
                                    <form:textarea cols="50" rows="10" type="text" class="form-control "  path="description"/>
                                    <!-- <%--                    ${errorFullName}--%> -->
                                </div>


                                <div class="mb-3 col-12 col-md-6">
                                    <label for="imgProduct" class="form-label">Image</label>
                                    <input class="form-control" type="file" id="imgProduct" name="imageProduct" accept=".png, .jpg, . jpeg">
                                </div>



                                <div class = "col-12 mb-3">
                                    <img style="max-height: 250px; display: none;" alt="avatar-preview" id="avatarPreview"/>
                                </div>
                                <div class="mb-3 col-12 col-md-6">
                                    <lable class ="form-label">Category </lable>
                                    <form:select path="category.name" class="form-select mt-2">
                                        <c:forEach var="cate" items="${categories}">
                                            <option value="${cate.name}" ${product.category.name eq cate.name ? 'selected' : ''}>${cate.name}</option>
                                        </c:forEach>
                                    </form:select>

                                </div>
<%--                                <div class="mb-3 col-12">--%>



<%--                                    <label class="form-label">Name</label>--%>
<%--                                    <form:input type="text" class="form-control"  path="categories"/>--%>
<%--                                    <!-- &lt;%&ndash;                    ${errorEmail}&ndash;%&gt; -->--%>
<%--                                </div>--%>
                                <div class = "mb-3 col-12" >
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <a href="/admin/product" class="btn btn-dark">Cancel</a>
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
