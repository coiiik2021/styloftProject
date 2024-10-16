
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%@page contentType="text/html"
pageEncoding="UTF-8" %>

<jsp:include page="../layout/headerImport.jsp" />
  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />

                      <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">User</h1>
                            <!-- <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item active">Dashboard</li>
                            </ol> -->
                            <div class="container mt-5">
                                <div class="row">
                                    <div class="col-md-12 col-12 mx-auto">

                                        <table class="table table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Name</th>
                                                    <th scope="col">Email</th>
                                                    <th scope="col">Phone</th>
                                                    <th scope="col">Address</th>
                                                    <th scope="col">Sex</th>
                                                    <th scope="col">BirthDay</th>
                                                    <th scope="col" style="text-align: center">Action</th>


                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="user" items="${users}">

                                                    <tr>
                                                        <th scope="row">${user.name}</th>
                                                        <td>${user.email}</td>
                                                        <td>${user.phoneNumber}</td>
                                                        <td>${user.address}</td>
                                                        <td>${user.sex == 1 ? 'Nam' : 'Nữ'}</td>
                                                        <td>${user.birthDay}</td>




                                                        <td style="text-align: center">
                                                            <a href="/admin/user/${user.id}"
                                                                class="btn btn-success">View</a>


                                                            <a href="/admin/user/delete/${user.id}"
                                                                class="btn btn-danger">delete</a>

                                                            <!-- <form action = "/admin/user/delete/${user.id}" method = "post" onsubmit="return submitDelete(); ">
                                           <button type="button">  delete</button>
                                        </form> -->

                                                        </td>

                                                    </tr>

                                                </c:forEach>


                                        </table>

                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-center">
                                                <li class="page-item">
                                                    <a class="${1 eq currentPage ? 'disabled': ''} page-link"
                                                        href="/admin/user?page=${currentPage - 1}"
                                                        aria-label="Previous">
                                                        <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                </li>
                                                <c:forEach begin="1" end="${totalPages}" varStatus="loop">

                                                    <li class="page-item">
                                                        <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                                                            href="/admin/user?page=${loop.index}">${loop.index}</a>

                                                    </li>
                                                </c:forEach>



                                                <li class="page-item">
                                                    <a class="${currentPage eq totalPages ? 'disabled' : ''}  page-link"
                                                        href="/admin/user?page=${currentPage + 1}" aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>

                                    </div>

                                </div>

                            </div>

                        </div>
                    </main>
                </div>
    
      <jsp:include page="../layout/footer.jsp" />
   
  </body>
</html>
