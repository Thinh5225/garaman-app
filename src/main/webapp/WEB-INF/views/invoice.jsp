<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!doctype html><html lang="vi"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Hóa đơn HD-<c:out value="${invoice.id}"/> | GaraMan</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css">
</head><body>
<jsp:include page="/WEB-INF/views/includes/header.jspf" />
<main class="container">
  <a class="back" href="javascript:history.back()">← Quay lại lịch sử sửa xe</a>
  <p class="eyebrow">CHI TIẾT HÓA ĐƠN</p><h1>Hóa đơn HD-<c:out value="${invoice.id}"/></h1>
  <section class="card invoice-summary">
    <div><small>Khách hàng</small><strong><c:out value="${invoice.customerName}"/></strong></div>
    <div><small>Biển số xe</small><strong><c:out value="${invoice.licensePlate}"/></strong></div>
    <div><small>Ngày thanh toán</small><strong><c:out value="${invoice.paymentDate}"/></strong></div>
  </section>
  <section class="card table-card">
    <div class="section-title"><div><h2>Dịch vụ và phụ tùng</h2><p>Đơn giá lấy từ giá tại thời điểm lập hóa đơn.</p></div></div>
    <c:choose><c:when test="${not empty lines}">
      <div class="table-scroll"><table><thead><tr><th>#</th><th>Nội dung</th><th>Loại</th><th class="right">Số lượng</th><th class="right">Đơn giá</th><th class="right">Thành tiền</th></tr></thead><tbody>
      <c:forEach items="${lines}" var="line" varStatus="loop">
        <tr><td><c:out value="${loop.count}"/></td><td><strong><c:out value="${line.name}"/></strong></td><td><span class="pill"><c:out value="${line.type}"/></span></td>
          <td class="right"><c:out value="${line.quantity}"/></td><td class="right"><fmt:formatNumber value="${line.unitPrice}" maxFractionDigits="0"/> đ</td>
          <td class="right"><fmt:formatNumber value="${line.lineTotal}" maxFractionDigits="0"/> đ</td></tr>
      </c:forEach></tbody></table></div>
    </c:when><c:otherwise><p class="empty">Hóa đơn chưa có dòng dịch vụ hoặc phụ tùng.</p></c:otherwise></c:choose>
    <div class="invoice-total"><span>Tổng tiền hóa đơn</span><strong><fmt:formatNumber value="${invoice.totalAmount}" maxFractionDigits="0"/> đ</strong></div>
  </section>
</main></body></html>
