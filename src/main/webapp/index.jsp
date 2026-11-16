<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Đăng nhập GaraMan</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=2">
</head>
<body>
<main class="login-page">
  <section class="login-box">
    <h1>GaraMan</h1>
    <p>Đăng nhập để sử dụng hệ thống</p>
    <% if (request.getAttribute("loginError") != null) { %>
      <p class="alert"><%= request.getAttribute("loginError") %></p>
    <% } %>
    <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
      <label for="username">Tên đăng nhập</label>
      <input id="username" name="username" type="text" autocomplete="username" required autofocus>
      <label for="password">Mật khẩu</label>
      <input id="password" name="password" type="password" autocomplete="current-password" required>
      <button type="submit">Đăng nhập</button>
    </form>
  </section>
</main>
</body>
</html>
