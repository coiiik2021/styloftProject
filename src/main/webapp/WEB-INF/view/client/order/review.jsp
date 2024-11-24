<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Review Oder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .gradient-custom {
            /* fallback for old browsers */
            background: #cd9cf2;

            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to top left, rgba(205, 156, 242, 1), rgba(246, 243, 255, 1));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: linear-gradient(to top left, rgba(205, 156, 242, 1), rgba(246, 243, 255, 1))
        }
    </style>
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
    </style>

</head>
<body>
<section class="h-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-10 col-xl-8">
                <div class="card" style="border-radius: 10px;">
                    <div class="card-header px-4 py-5">
                        <h5 class="text-muted mb-0">Review</h5>
                    </div>
                    <%--                        <c:forEach var="orderDetail" items="${details}">--%>
                    <div class="card shadow-0 border">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <img src="/images/product/${detail.productVariant.product.name}/${detail.productVariant.image}"
                                         class="img-fluid" alt="${detail.productVariant.product.name}">
                                </div>


                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0">${detail.productVariant.product.category.name}</p>
                                </div>
                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0 small">${detail.productVariant.product.name}</p>
                                </div>
                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0 small">${detail.productVariant.size.name}</p>
                                </div>
                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                    <p class="text-muted mb-0 small">${detail.productVariant.color.name}</p>
                                </div>

                                <div class="col-md-10" style="width: 100%; padding: 0;">
                                    <div class="rating" style="margin-left: 8px;">
                                        <input type="radio" name="rating" id="rs0" value="0" checked><label for="rs0"></label>
                                        <input type="radio" name="rating" id="rs1" value="1" ${newReview.star == 1 ? 'checked' : ''} ><label for="rs1">☆</label>
                                        <input type="radio" name="rating" id="rs2" value="2" ${newReview.star == 2 ? 'checked' : ''} ><label for="rs2">☆</label>
                                        <input type="radio" name="rating" id="rs3" value="3" ${newReview.star == 3 ? 'checked' : ''} ><label for="rs3">☆</label>
                                        <input type="radio" name="rating" id="rs4" value="4" ${newReview.star == 4 ? 'checked' : ''} ><label for="rs4">☆</label>
                                        <input type="radio" name="rating" id="rs5" value="5" ${newReview.star == 5 ? 'checked' : ''} ><label for="rs5">☆</label>
                                    </div>
                                    <form:form action="/order/detail/review" method="post" modelAttribute="newReview">
                                        <form:input type="text" class="form-floating" path="description"/>
                                        <textarea
                                                class="form-control w-100"
                                                placeholder="Leave a comment here"
                                                id="floatingTextarea2"
                                                style="height: 100px;"></textarea>
                                        <div style="display: none">
                                            <label>
                                                <input value="${detail.id}" name="idOrderDetail" type="text">
                                            </label>
                                        </div>

                                        <button type="submit" class="btn btn-danger" style="float: right;margin: 10px 0;">Send</button>
                                    </form:form>

                                </div>
                                <hr class="mb-4" style="background-color: #e0e0e0; opacity: 1;">
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>