<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="--poster-url: url('${pageContext.request.contextPath}/poster/${movie.poster}');">
    <div class="container">
        <div class="row justify-content-center">
            
            <div class="col-lg-10">
                <div class="reserve-container-glass">
                    
                    <div class="reserve-header d-flex align-items-center mb-4">
                        <img src="${pageContext.request.contextPath}/poster/${movie.poster}" class="mini-poster me-3" alt="${movie.title}">
                        <div>
                            <h2 class="m-0 fw-bold">${movie.title}</h2>
                            <p class="text-orange mb-0 small">「 ${movie.catchphrase} 」</p>
                        </div>
                    </div>

                    <hr class="border-secondary opacity-25">

                    <div class="date-selector-wrapper mb-5">
                        <h5 class="section-title"><i class="bi bi-calendar3 me-2"></i> 上映日を選択 </h5>
						<div class="date-list d-flex">
						<%-- fn:split을 사용하여 쉼표로 구분된 문자열을 배열로 변환합니다 --%>
							<c:set var="weeks" value="${fn:split('日,月,火,水,木,金,土', ',')}" />
							
							<c:forEach var="date" items="${dateList}">
								<c:set var="fullDate" value="${date.toString()}" />
								<%-- Java DayOfWeek: 월(1)~일(7) -> %7 연산 시 일(0), 월(1)...토(6) 순서가 됩니다 --%>
								<c:set var="dayOfWeekIdx" value="${date.dayOfWeek.value % 7}" />
								
								<a href="reserve.do?mno=${movie.mno}&date=${fullDate}" 
									class="date-item ${fullDate eq selectedDate ? 'active' : ''}">
									<span class="month">${date.monthValue}月</span>
									<span class="day">${date.dayOfMonth}</span>
									<span class="dow">${weeks[dayOfWeekIdx]}</span>
								</a>
							</c:forEach>
						</div>
                    </div>

                    <div class="time-selector-wrapper">
                        <h5 class="section-title"><i class="bi bi-clock me-2"></i> 上映時間を選択 </h5>
                        
                        <div class="schedule-grid mt-3">
    <c:choose>
        <c:when test="${not empty scheduleList}">
            <c:forEach var="sch" items="${scheduleList}">
                <%-- 클릭 시 좌석 선택 페이지로 이동 --%>
                <a href="seat.do?sno=${sch.sno}" class="schedule-card">
                    <%-- 시간 표시 (초 단위 자르기) --%>
                    <div class="sch-time">${sch.sdate.substring(11,16)}</div>
                    <div class="sch-info">
                        <span class="theater">${sch.tname}</span>
                        
                        <%-- [핵심 로직] 잔여 좌석 계산: 전체 - 예약수 --%>
                        <c:set var="remaining" value="${sch.total_seats - sch.booked_cnt}" />
                        
                        <c:choose>
                            <%-- 잔여석이 0 이하면 매진 처리 --%>
                            <c:when test="${remaining <= 0}">
                                <span class="seats text-danger fw-bold">SOLD OUT</span>
                            </c:when>
                            <c:otherwise>
                                <%-- 잔여석이 10석 미만이면 빨간색, 아니면 주황색 --%>
                                <span class="seats ${remaining < 10 ? 'text-danger' : 'text-orange'}">
                                    ${remaining} / ${sch.total_seats} 席
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </a>
            </c:forEach>
        </c:when>
        
        <c:otherwise>
            <div class="py-5 text-center text-secondary">
                <i class="bi bi-exclamation-circle fs-1 d-block mb-3"></i>
                その日は上映スケジュールがありません。
            </div>
        </c:otherwise>
    </c:choose>
</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>