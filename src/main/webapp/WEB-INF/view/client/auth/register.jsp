<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Login to access your account and exclusive features.">
    <meta name="keywords" content="login, authentication, user login, access">
    <meta name="author" content="Your Company">
    <title>Login</title>
    <link rel="stylesheet" href="/css/register.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>
<section class="container">
    <h2>Register</h2>
    <form:form action="/register" method="POST" class="form-group" modelAttribute="newUser">
        <div class="input-group">
            <label for="email">Email</label>
            <form:input path="email" type="email" id="email"  placeholder="Enter your email" aria-label="Email"/>
        </div>
        <div class="input-group">
            <label for="password">Password</label>
            <form:input path="password" type="password" id="password" placeholder="Enter your password" aria-label="Password"/>
        </div>
        <button type="submit" class="btn">Register</button>
    </form:form>
    <div class="description">
        Or log in with your Google account
    </div>
    <button class="btn btn-google">
        <img src="google.png" alt="Google Logo" class="google-logo">
        Login with Google
    </button>
    <div class="link">
        Don't have an account? <a href="/login">Login</a>
    </div>
</section>
</body>
</html>
