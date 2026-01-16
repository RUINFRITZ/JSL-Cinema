<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- 숫자 포맷팅을 위해 추가 추천 --%>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh;">
    <div class="container seat-page-container" style="padding-top: 0.5rem; padding-bottom: 4rem;">
        <div class="reserve-container-glass" style="max-width: 600px; margin: 0 auto;">
            
            <h3 class="text-center mb-5">決済情報の確認</h3>
            
            <div class="card bg-transparent border border-secondary text-white mb-4">
                <div class="card-body">
                    <h4 class="card-title fw-bold text-orange mb-3">${schedule.title}</h4> 
                    <p class="mb-1"><i class="bi bi-geo-alt me-2"></i> ${theater.tname}</p>
                    <p class="mb-1"><i class="bi bi-clock me-2"></i> ${schedule.sdate}</p>
                    
                    <hr class="border-secondary">
                    
                    <div class="d-flex justify-content-between">
                        <span>選択座席</span>
                        <span class="fw-bold">${seats}</span>
                    </div>
                    
                    <div class="d-flex justify-content-between mt-2">
                        <span>合計金額</span>
                        <span class="fw-bold text-orange fs-4">
                            <fmt:formatNumber value="${totalPrice}" pattern="#,###" /> 円
                        </span>
                    </div>

                    <hr class="border-secondary my-3" style="border-style: dashed !important;">
                    
                    <%-- 잔여 포인트 계산 (현재 포인트 - 결제 금액) --%>
                    <%-- 주의: Controller에서 'point'라는 이름으로 현재 포인트를 넘겨줘야 합니다 --%>
                    <c:set var="currentPoint" value="${point}" /> 
                    <c:set var="remainingPoint" value="${currentPoint - totalPrice}" />

                    <div class="d-flex justify-content-between text-secondary small mb-1">
                        <span>保有ポイント</span>
                        <span><fmt:formatNumber value="${currentPoint}" pattern="#,###" /> P</span>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <span>決済後の残高</span>
                        <%-- 잔액이 마이너스면 빨간색 경고, 아니면 흰색 --%>
                        <span class="fw-bold ${remainingPoint < 0 ? 'text-danger' : 'text-white'}">
                            <fmt:formatNumber value="${remainingPoint}" pattern="#,###" /> P
                        </span>
                    </div>
                     </div>
            </div>

            <form action="insertResv.do" method="post" onsubmit="return checkPoint()">
                <input type="hidden" name="sno" value="${schedule.sno}">
                <input type="hidden" name="seats" value="${seats}">
                <input type="hidden" name="price" value="${totalPrice}"> 
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-card-reserve py-3 fs-5">
                        <i class="bi bi-credit-card me-2"></i> 決済する
                    </button>
                    <a href="javascript:history.back()" class="btn btn-outline-light">後戻り</a>
                </div>
            </form>
            
        </div>
    </div>
</div>

<script>
    function checkPoint() {
        const remaining = ${remainingPoint};
        if (remaining < 0) {
            showModal("ポイントが不足しています。\n", null);
            return false;
        }
        return true;
    }
</script>

<%@ include file="footer.jsp" %>