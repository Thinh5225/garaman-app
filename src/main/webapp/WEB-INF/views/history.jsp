<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!doctype html><html lang="vi"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Lịch sử sửa xe | GaraMan</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css">
</head><body>
<jsp:include page="/WEB-INF/views/includes/header.jspf" />
<main class="container">
  <c:url var="statsUrl" value="/statistics"><c:param name="startDate" value="${startDate}"/><c:param name="endDate" value="${endDate}"/></c:url>
  <a class="back" href="${statsUrl}">← Quay lại danh sách khách hàng</a>
  <p class="eyebrow">LỊCH SỬ SỬA CHỮA</p><h1><c:out value="${customer.name}"/></h1>
  <p class="lead">Biển số <strong><c:out value="${customer.licensePlate}"/></strong> · <c:out value="${startDate}"/> đến <c:out value="${endDate}"/></p>
  <section class="card table-card">
    <div class="section-title"><div><h2>Các lần sửa xe</h2><p>Chọn một lần sửa để mở hóa đơn tương ứng.</p></div><span class="count"><c:out value="${invoices.size()}"/> hóa đơn</span></div>
    <c:choose><c:when test="${not empty invoices}">
      <div class="table-scroll"><table><thead><tr><th>#</th><th>Mã hóa đơn</th><th>Ngày thanh toán</th><th class="right">Tổng tiền</th><th></th></tr></thead><tbody>
      <c:forEach items="${invoices}" var="invoice" varStatus="loop">
        <c:url var="invoiceUrl" value="/invoice"><c:param name="invoiceId" value="${invoice.id}"/></c:url>
        <tr><td><c:out value="${loop.count}"/></td><td>HD-<c:out value="${invoice.id}"/></td>
          <td><c:out value="${invoice.paymentDate}"/></td><td class="right"><fmt:formatNumber value="${invoice.totalAmount}" maxFractionDigits="0"/> đ</td>
          <td class="right"><a href="${invoiceUrl}">Xem hóa đơn →</a></td></tr>
      </c:forEach></tbody></table></div>
    </c:when><c:otherwise><p class="empty">Khách hàng không có lần sửa xe nào trong khoảng thời gian đã chọn.</p></c:otherwise></c:choose>
  </section>
</main></body></html>
