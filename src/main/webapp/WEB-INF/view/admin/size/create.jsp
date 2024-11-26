<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<jsp:include page="../layout/headerImport.jsp" />
<link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />

    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Create Size</h1>

                <!-- Import header -->
                <!-- <%--<c:import url=""/>--%> -->




                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <form:form action="/admin/size/create" method="post" enctype="multipart/form-data" modelAttribute="newSize" class="row">

                                <c:set var="errorName">
                                    <form:errors path="name" cssClass="invalid-feedback"/>
                                </c:set>



                                <div class="mb-3 col-12">

                                    <label class="form-label">Name</label>
                                    <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}"  path="name"/>

                                    ${errorName}
                                </div>



                                <div class="mb-3 col-12">
                                    <label class="form-label">Description</label>
                                    <form:input  type="text" class="form-control "  path="description"/>
                                    <!-- <%--                    ${errorFullName}--%> -->
                                </div>











<%--                                <div class = "col-12 mb-3">--%>
<%--                                    <img style="max-height: 250px; display: none;" alt="avatar-preview" id="avatarPreview"/>--%>
<%--                                </div>--%>
<%--                                <div class="mb-3 col-12">--%>

<%--                                    <label class="form-label">Name</label>--%>
<%--                                    <form:input type="text" class="form-control"  path="categories"/>--%>
<%--                                    <!-- &lt;%&ndash;                    ${errorEmail}&ndash;%&gt; -->--%>
<%--                                </div>--%>
                                <div class = "mb-3 col-12" >
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <a href="/admin/size" class="btn btn-dark">Cancel</a>
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