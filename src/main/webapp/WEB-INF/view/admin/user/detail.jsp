
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="../layout/headerImport.jsp" />

  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
    </div>

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="container mt-5">
                <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                        <div class="card p-4">
                            <h3 class="mb-4"> ${user.name}</h3>
                            <div class="mb-3">
                                <label for="email" class="form-label"><strong>Email:</strong></label>
                                <p id="email">${user.account.email}</p>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label"><strong>Address:</strong></label>
                                <p id="address">${user.address}</p>
                            </div>
                            <div class="mb-3">
                                <label for="phone" class="form-label"><strong>Phone:</strong></label>
                                <p id="phone">${user.phoneNumber}</p>
                            </div>
                            <div class="mb-3">
                                <label for="sex" class="form-label"><strong>Sex:</strong></label>
                                <p id="sex">${user.sex == 1 ? 'Nam' : 'Nữ'}</p>
                            </div>
                             <div class="mb-3">
                                <label for="birthDay" class="form-label"><strong>Birth Day:</strong></label>
                                <p id="birthDay">${user.birthDay}</p>
                            </div>

                            
                            
                            <div class="d-flex justify-content-end">
                                <a href="/admin/account" class="btn btn-primary">Back to Users</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </main>
</div>



      <jsp:include page="../layout/footer.jsp" />
  </body>
</html>
