<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!doctype html><html lang="vi"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Tìm kiếm | GaraMan</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css">
</head><body>
<jsp:include page="/WEB-INF/views/includes/header.jspf" />
<main class="container">
  <p class="eyebrow">MODULE 1</p><h1>Tìm kiếm dịch vụ và phụ tùng</h1>
  <p class="lead">Kết quả được tìm trong cả hai danh mục theo tên có chứa từ khóa.</p>
  <section class="card">
    <form action="${pageContext.request.contextPath}/search" method="get" class="inline-form">
      <label class="sr-only" for="keyword">Tên dịch vụ hoặc phụ tùng</label>
      <input id="keyword" name="keyword" type="search" value="<c:out value='${keyword}'/>" placeholder="Ví dụ: dầu động cơ" required>
      <button type="submit">Tìm kiếm</button>
    </form>
  </section>
  <c:if test="${searched}">
    <section class="card table-card">
      <div class="section-title"><div><h2>Kết quả tìm kiếm</h2><p>Từ khóa: <strong><c:out value="${keyword}"/></strong></p></div>
        <span class="count"><c:out value="${items.size()}"/> mục</span></div>
      <c:choose><c:when test="${not empty items}">
        <div class="table-scroll"><table><thead><tr><th>#</th><th>Tên</th><th>Loại</th><th class="right">Giá</th><th></th></tr></thead><tbody>
        <c:forEach items="${items}" var="item" varStatus="loop">
          <c:url var="detailUrl" value="/item"><c:param name="type" value="${item.type eq 'Dịch vụ' ? 'service' : 'part'}"/><c:param name="id" value="${item.id}"/></c:url>
          <tr><td><c:out value="${loop.count}"/></td><td><strong><c:out value="${item.name}"/></strong></td>
            <td><span class="pill"><c:out value="${item.type}"/></span></td>
            <td class="right"><fmt:formatNumber value="${item.price}" maxFractionDigits="0"/> đ</td>
            <td class="right"><a href="${detailUrl}">Xem chi tiết →</a></td></tr>
        </c:forEach>
        </tbody></table></div>
      </c:when><c:otherwise><p class="empty">Không tìm thấy dịch vụ hoặc phụ tùng phù hợp.</p></c:otherwise></c:choose>
    </section>
  </c:if>
</main></body></html>
