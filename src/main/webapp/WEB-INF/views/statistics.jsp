<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!doctype html><html lang="vi"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Thống kê doanh thu | GaraMan</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css">
</head><body>
<jsp:include page="/WEB-INF/views/includes/header.jspf" />
<main class="container">
  <p class="eyebrow">MODULE 2</p><h1>Thống kê khách hàng theo doanh thu</h1>
  <p class="lead">Doanh thu được tính từ tổng tiền hóa đơn có ngày thanh toán nằm trong khoảng đã chọn.</p>
  <section class="card">
    <form action="${pageContext.request.contextPath}/statistics" method="get" class="date-form">
      <div><label for="startDate">Ngày bắt đầu</label><input id="startDate" name="startDate" type="date" value="<c:out value='${startDate}'/>" required></div>
      <div><label for="endDate">Ngày kết thúc</label><input id="endDate" name="endDate" type="date" value="<c:out value='${endDate}'/>" required></div>
      <button type="submit">Xem thống kê</button>
    </form>
  </section>
  <c:if test="${not empty error}"><p class="alert"><c:out value="${error}"/></p></c:if>
  <c:if test="${searched}">
    <section class="card table-card">
      <div class="section-title"><div><h2>Danh sách khách hàng</h2><p>Khoảng ngày <c:out value="${startDate}"/> đến <c:out value="${endDate}"/></p></div>
        <span class="count"><c:out value="${rows.size()}"/> khách hàng</span></div>
      <c:choose><c:when test="${not empty rows}">
        <div class="table-scroll"><table><thead><tr><th>#</th><th>Khách hàng</th><th>Biển số</th><th>Số hóa đơn</th><th class="right">Doanh thu</th><th></th></tr></thead><tbody>
        <c:forEach items="${rows}" var="row" varStatus="loop">
          <c:url var="historyUrl" value="/history"><c:param name="customerId" value="${row.customer.id}"/><c:param name="startDate" value="${startDate}"/><c:param name="endDate" value="${endDate}"/></c:url>
          <tr><td><c:out value="${loop.count}"/></td>
            <td><strong><c:out value="${row.customer.name}"/></strong><small class="subline"><c:out value="${row.customer.phone}"/></small></td>
            <td><c:out value="${row.customer.licensePlate}"/></td><td><c:out value="${row.invoiceCount}"/></td>
            <td class="right"><strong><fmt:formatNumber value="${row.revenue}" maxFractionDigits="0"/> đ</strong></td>
            <td class="right"><a href="${historyUrl}">Lịch sử sửa xe →</a></td></tr>
        </c:forEach></tbody></table></div>
      </c:when><c:otherwise><p class="empty">Không có dữ liệu trong khoảng thời gian này.</p></c:otherwise></c:choose>
    </section>
  </c:if>
</main></body></html>
