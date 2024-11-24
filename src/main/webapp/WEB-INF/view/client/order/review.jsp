<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reivew Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body * {
            box-sizing: border-box;
        }


        input { display: none; }


        label[for=rs0] {
            display: none;
        }

        #rs1:checked ~ .rating-counter:before {
            content: "1";
        }

        #rs2:checked ~ .rating-counter:before {
            content: "2";
        }

        #rs3:checked ~ .rating-counter:before {
            content: "3";
        }

        #rs4:checked ~ .rating-counter:before {
            content: "4";
        }

        #rs5:checked ~ .rating-counter:before {
            content: "5";
        }

        input:checked:hover ~ .rating-counter:before {
            animation: none !important;
            color: #ffab00 !important ;
        }

        .comment-area textarea{
            resize: none;
            border: 1px solid #ad9f9f;
        }
        .rating {
            display: flex;
            margin-top: -10px;
            flex-direction: row-reverse;
            margin-left: -4px;
            float: left;
        }

        .rating>input {
            display: none
        }

        .rating>label {
            position: relative;
            width: 19px;
            font-size: 25px;
            color: #ffab00;
            cursor: pointer;
        }

        .rating>label::before {
            content: "\2605";
            position: absolute;
            opacity: 0
        }

        .rating>label:hover:before,
        .rating>label:hover~label:before {
            opacity: 1 !important
        }
        .rating>input:checked~label:before {
            opacity: 1
        }

        .rating:hover>input:checked~label:before {
            opacity: 0.4
        }
        h5 {
            text-align: center;
        }
        .btn {
            background-color: #ED4417!important;
            color: #fff !important;
            font-weight: bold !important;
            border: navajowhite !important;
        }
    </style>
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
<section class="h-100" style="background-color: #fff;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-10 col-xl-8">
                <div class="card" style="border-radius: 10px;">
                    <div class="card-header px-4 py-5" style="background-color: #ef750b">
                        <h5 class="text-muted mb-0" style="color: #ffffff">Đánh giá</h5>
                    </div>
                    <div class="card shadow-0 border">
                        <div class="card-body">
                            <div class="row">
                                <div class="col text-center d-flex justify-content-center align-items-center">
                                    <img src="/images/product/${detail.productVariant.product.name}/${detail.productVariant.image}"
                                         class="img-fluid" alt="${detail.productVariant.product.name}">
                                </div>
                                <div class="col text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0">${detail.productVariant.product.category.name}</p>
                                </div>
                                <div class="col text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0 small">${detail.productVariant.product.name}</p>
                                </div>
                                <div class="col text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0 small">${detail.productVariant.size.name}</p>
                                </div>
                                <div class="col text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0 small">${detail.productVariant.color.name}</p>
                                </div>

                                <div class="col-md-10" style="width: 100%; padding: 0;">
                                    <div class="d-flex flex-row m-2">
                                        <div class="ps-2 pe-2 fs-5">
                                            <img class="rounded-circle" src="/images/avatar/${not empty user.image ?  user.image : 'default.png'}" alt="${user.image}" style="width: 60px; height: auto;">
                                        </div>
                                        <div class="flex-column mb-2 ">
                                            <h6 class="fs-5">Bình luận</h6>
                                            <div class="rating" >
                                                <input type="radio" name="rating" id="rs0" value="0" checked><label for="rs0"></label>
                                                <input type="radio" name="rating" id="rs1" value="1" ${newReview.star == 1 ? 'checked' : ''} ><label for="rs1">☆</label>
                                                <input type="radio" name="rating" id="rs2" value="2" ${newReview.star == 2 ? 'checked' : ''} ><label for="rs2">☆</label>
                                                <input type="radio" name="rating" id="rs3" value="3" ${newReview.star == 3 ? 'checked' : ''} ><label for="rs3">☆</label>
                                                <input type="radio" name="rating" id="rs4" value="4" ${newReview.star == 4 ? 'checked' : ''} ><label for="rs4">☆</label>
                                                <input type="radio" name="rating" id="rs5" value="5" ${newReview.star == 5 ? 'checked' : ''} ><label for="rs5">☆</label>
                                            </div>
                                        </div>
                                    </div>
                                    <form:form action="/order/detail/review" method="post" modelAttribute="newReview">
                                        <form:input type="text" class="form-floating" path="description"/>
                                        <textarea
                                                class="form-control w-100"
                                                placeholder="Để lại đánh giá tại đây"
                                                id="floatingTextarea2"
                                                style="height: 100px;"></textarea>
                                        <div style="display: none">
                                            <label>
                                                <input value="${detail.id}" name="idOrderDetail" type="text">
                                            </label>
                                        </div>
                                        <hr style="background-color: #e0e0e0; opacity: 1;">
                                        <button type="submit" class="btn btn-danger" style="float: right;">Gửi</button>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../layout/footer.jsp" />

</body>
</html>
