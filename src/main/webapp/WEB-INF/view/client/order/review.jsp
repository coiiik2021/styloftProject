<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dungnguyen
  Date: 10/11/24
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
        /*body {*/
        /*  margin: 0;*/
        /*  width: 100vw;*/
        /*  height: 100vh;*/
        /*  display: flex;*/
        /*  align-items: center;*/
        /*  justify-content: center;*/
        /*  background-color: #11114e;*/
        /*  background: radial-gradient( circle at 50% 100%, #1d659f, #11114e);*/
        /*}*/

        body * {
            box-sizing: border-box;
        }

        .rating-stars {
            display: block;
            width: 50vmin;
            padding: 1.75vmin 10vmin 2vmin 3vmin;
            background: linear-gradient(90deg, #ffffff90 40vmin, #fff0 40vmin 100%);
            border-radius: 5vmin;
            position: relative;
        }

        .rating-counter {
            font-size: 5.5vmin;
            font-family: Arial, Helvetica, serif;
            color: #9aacc6;
            width: 10vmin;
            text-align: center;
            background: #0006;
            position: absolute;
            top: 0;
            right: 0;
            height: 100%;
            border-radius: 0 5vmin 5vmin 0;
            line-height: 10vmin;
        }

        .rating-counter:before {
            content: "0";
            transition: all 0.25s ease 0s;
        }



        input { display: none; }

        label {
            width: 5vmin;
            height: 5vmin;
            background: #000b;
            display: inline-flex;
            cursor: pointer;
            margin: 0.5vmin 0.65vmin;
            transition: all 1s ease 0s;
            clip-path: polygon(50% 0%, 66% 32%, 100% 38%, 78% 64%, 83% 100%, 50% 83%, 17% 100%, 22% 64%, 0 38%, 34% 32%);
        }

        label[for=rs0] {
            display: none;
        }

        label:before {
            width: 90%;
            height: 90%;
            content: "";
            background: orange;
            z-index: -1;
            display: block;
            margin-left: 5%;
            margin-top: 5%;
            clip-path: polygon(50% 0%, 66% 32%, 100% 38%, 78% 64%, 83% 100%, 50% 83%, 17% 100%, 22% 64%, 0 38%, 34% 32%);
            background: linear-gradient(90deg, yellow, orange 30% 50%, #184580 50%, 70%, #173a75 100%);
            background-size: 205% 100%;
            background-position: 0 0;
        }

        label:hover:before {
            transition: all 0.25s ease 0s;
        }

        input:checked + label ~ label:before {
            background-position: 100% 0;
            transition: all 0.25s ease 0s;
        }

        input:checked + label ~ label:hover:before {
            background-position: 0% 0
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

        label + input:checked ~ .rating-counter:before {
            color: #ffab00 !important;
            transition: all 0.25s ease 0s;
        }





        label:hover ~ .rating-counter:before {
            color: #9aacc6 !important;
            transition: all 0.5s ease 0s;
            animation: pulse 1s ease 0s infinite;
        }

        @keyframes pulse {
            50% { font-size: 6.25vmin; }
        }

        label[for=rs1]:hover ~ .rating-counter:before {
            content: "1" !important;
        }

        label[for=rs2]:hover ~ .rating-counter:before {
            content: "2" !important;
        }

        label[for=rs3]:hover ~ .rating-counter:before {
            content: "3" !important;
        }

        label[for=rs4]:hover ~ .rating-counter:before {
            content: "4" !important;
        }

        label[for=rs5]:hover ~ .rating-counter:before {
            content: "5" !important;
        }


        input:checked:hover ~ .rating-counter:before {
            animation: none !important;
            color: #ffab00 !important ;
        }








        /*** INITIAL ANIMATION - Not Necessary ***/
        /*** I want to make a loop here ***/
        /*
        label + input + label:before {
            animation: start 0.5s ease 0s 1;
        }

        .rating-stars:hover label + input + label:before {
            animation: none;
        }

        label[for=rs2]:before {
            animation-delay: 0.1s;
        }

        label[for=rs3]:before {
            animation-delay: 0.2s;
        }

        label[for=rs4]:before {
            animation-delay: 0.3s;
        }

        label[for=rs5]:before {
            animation-delay: 0.4s;
        }

        @keyframes start {
            10%, 90% { background-position: 0% 0; }
        }

        */
    </style>

</head>
<body>

<section class="h-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-10 col-xl-8">
                <div class="card" style="border-radius: 10px;">


<%--                        <c:forEach var="orderDetail" items="${details}">--%>
                            <div class="card shadow-0 border mb-4">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <img src="/images/product/${detail.productVariant.product.name}/${detail.productVariant.image}"
                                                 class="img-fluid" alt="${detail.productVariant.product.name}">
                                        </div>


                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="text-muted mb-0">${detail.productVariant.product.name}</p>
                                        </div>
                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="text-muted mb-0 small">${detail.productVariant.product.name}</p>
                                        </div>
                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="text-muted mb-0 small">${detail.productVariant.size.name}</p>
                                        </div>

                                        <div class="col-md-10">
                                            <form:form action="/order/detail/review" method="post" modelAttribute="newReview">
                                                <form:input type="text" class="form-control" path="description"/>

                                                <div style="display: none">
                                                    <label>
                                                        <input value="${detail.id}" name="idOrderDetail" type="text">
                                                    </label>
                                                </div>
                                            <div class="rating-stars">
                                                <input type="radio" name="star" id="rs0" value="0" checked><label for="rs0"></label>
                                                <input type="radio" name="star" id="rs1" value="1" ${newReview.star == 1 ? 'checked' : ''} ><label for="rs1"></label>
                                                <input type="radio" name="star" id="rs2" value="2" ${newReview.star == 2 ? 'checked' : ''} ><label for="rs2"></label>
                                                <input type="radio" name="star" id="rs3" value="3" ${newReview.star == 3 ? 'checked' : ''} ><label for="rs3"></label>
                                                <input type="radio" name="star" id="rs4" value="4" ${newReview.star == 4 ? 'checked' : ''} ><label for="rs4"></label>
                                                <input type="radio" name="star" id="rs5" value="5" ${newReview.star == 5 ? 'checked' : ''} ><label for="rs5"></label>
                                                <span class="rating-counter"></span>

                                            </div>

                                                <button type="submit" class="btn btn-danger" > Send </button>
                                            </form:form>

                                    </div>
                                    <hr class="mb-4" style="background-color: #e0e0e0; opacity: 1;">
                                </div>
                            </div>

<%--                        </c:forEach>--%>







                    </div>
                </div>
            </div>
        </div>
</section>





</body>
</html>
