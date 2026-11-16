<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="vi"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Lỗi | GaraMan</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css">
</head><body>
<jsp:include page="/WEB-INF/views/includes/header.jspf" />
<main class="container narrow"><section class="card"><p class="eyebrow">KHÔNG THỂ HOÀN TẤT YÊU CẦU</p>
  <h1>Đã xảy ra lỗi</h1><p class="lead">Kiểm tra kết nối MySQL và cấu hình database, sau đó tải lại trang.</p>
  <a class="button-link" href="${pageContext.request.contextPath}/">Về trang chính</a></section></main>
</body></html>
