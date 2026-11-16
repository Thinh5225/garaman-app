<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!doctype html><html lang="vi"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Chi tiết | GaraMan</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css">
</head><body>
<jsp:include page="/WEB-INF/views/includes/header.jspf" />
<main class="container narrow">
  <a class="back" href="${pageContext.request.contextPath}/search">← Tìm kiếm lại</a>
  <p class="eyebrow">CHI TIẾT DANH MỤC</p><h1><c:out value="${item.name}"/></h1>
  <section class="card detail-card">
    <p><span class="pill"><c:out value="${item.type}"/></span></p>
    <dl class="details"><dt>Mã</dt><dd><c:out value="${item.id}"/></dd>
      <dt>Giá hiện tại</dt><dd><strong><fmt:formatNumber value="${item.price}" maxFractionDigits="0"/> đ</strong></dd>
      <c:if test="${item.type eq 'Dịch vụ'}"><dt>Mô tả</dt><dd><c:out value="${item.description}"/></dd></c:if>
      <c:if test="${item.type eq 'Phụ tùng'}"><dt>Số lượng tồn</dt><dd><c:out value="${item.stockQuantity}"/></dd></c:if>
    </dl>
  </section>
</main></body></html>
