<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="todayDate" value="${now}" pattern="yyyy-MM-dd" />

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh; align-items: flex-start; padding-top: 4rem;">
    <div class="container">
        
        <div class="d-flex justify-content-between align-items-center mb-4 ps-2 border-start border-4 border-warning">
            <h2 class="text-white fw-bold mb-0">
                &nbsp;マイチケット
            </h2>
            <button class="btn btn-outline-secondary btn-sm rounded-pill px-3" onclick="toggleHistory(this)">
                <i class="bi bi-clock-history me-1"></i> 予約履歴をすべて見る
            </button>
        </div>

        <div class="row g-4" id="ticketContainer">
            <c:choose>
                <c:when test="${not empty ticketList}">
                    <c:forEach var="ticket" items="${ticketList}">
                        
                        <c:set var="ticketDateOnly" value="${ticket.sdate.substring(0, 10)}"  />
                        <c:set var="isPast" value="${ticketDateOnly < todayDate}"  />
                        
                        <div class="col-md-6 col-lg-4 ticket-item ${isPast ? 'past-ticket d-none' : ''}">
                            <div class="reserve-container-glass p-0 overflow-hidden h-100 position-relative ${isPast ? 'opacity-75 grayscale' : ''}">
                                <div class="row g-0 h-100">
                                    <div class="col-4">
                                        <img src="${pageContext.request.contextPath}/poster/${ticket.poster}" 
                                             class="w-100 h-100 object-fit-cover" 
                                             style="min-height: 200px;" alt="poster">
                                    </div>
                                    <div class="col-8 p-3 d-flex flex-column justify-content-between">
                                        <div>
                                            <h5 class="fw-bold text-white mb-1 text-truncate">${ticket.title}</h5>
                                            <p class="text-orange small mb-2" style="font-family: monospace; letter-spacing: 1px;">
											    Reservation No.<fmt:formatNumber value="${ticket.rno}" pattern="202600" />
											</p>
                                            
                                            <div class="text-secondary small">
                                                <p class="mb-1"><i class="bi bi-geo-alt me-1"></i> ${ticket.tname}</p>
                                                <p class="mb-1 ${isPast ? 'text-danger text-decoration-line-through' : ''}">
                                                    <i class="bi bi-calendar-event me-1"></i> ${ticket.sdate.substring(0,16)}
                                                </p>
                                                <p class="mb-1 text-white"><i class="bi bi-person-workspace me-1"></i> 座席: <b>${ticket.seat_info}</b></p>
                                            </div>
                                        </div>
                                        <div class="text-end border-top border-secondary pt-2 mt-2">
                                            <c:choose>
                                                <c:when test="${isPast}">
                                                    <span class="badge bg-secondary text-white">観覧完了</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-warning text-dark">決済完了</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <div id="emptyMessage" class="col-12 text-center py-5 d-none">
                        <i class="bi bi-calendar-x fs-1 text-secondary mb-3"></i>
                        <p class="text-secondary">現在、上映予定のチケットはありません。</p>
                        <p class="small text-secondary">右上のボタンで過去の履歴を確認できます。</p>
                    </div>
                    
                </c:when>
                <c:otherwise>
                    <div class="col-12 text-center py-5">
                        <div class="reserve-container-glass d-inline-block px-5">
                            <i class="bi bi-ticket-perforated fs-1 text-secondary d-block mb-3"></i>
                            <p class="text-white mb-0">予約履歴がございません。</p>
                            <a href="list.do" class="btn btn-outline-light mt-3 btn-sm">映画を予約する</a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
    </div>
</div>

<script>
    // [추가] 초기 로딩 시: 만약 보여줄 티켓이 하나도 없다면(다 과거티켓이면) 안내 메시지 표시
    document.addEventListener("DOMContentLoaded", function() {
        checkVisibleTickets();
    });

    function toggleHistory(btn) {
        const pastTickets = document.querySelectorAll('.past-ticket');
        const isHidden = pastTickets[0] && pastTickets[0].classList.contains('d-none');

        pastTickets.forEach(ticket => {
            if (isHidden) {
                ticket.classList.remove('d-none'); // 보이기
            } else {
                ticket.classList.add('d-none'); // 숨기기
            }
        });

        // 버튼 텍스트/스타일 변경
        if (isHidden) {
            btn.innerHTML = '<i class="bi bi-eye-slash me-1"></i> 最近のチケットのみ';
            btn.classList.add('btn-light');
            btn.classList.remove('btn-outline-secondary');
        } else {
            btn.innerHTML = '<i class="bi bi-clock-history me-1"></i> 予約履歴をすべて見る';
            btn.classList.remove('btn-light');
            btn.classList.add('btn-outline-secondary');
        }
        
        checkVisibleTickets();
    }
    
    // 화면에 보이는 티켓이 있는지 체크하는 함수
    function checkVisibleTickets() {
        const allTickets = document.querySelectorAll('.ticket-item');
        const hiddenTickets = document.querySelectorAll('.ticket-item.d-none');
        const emptyMsg = document.getElementById('emptyMessage');
        
        if(emptyMsg) {
            // 전체 티켓 수와 숨겨진 티켓 수가 같으면 -> 화면에 아무것도 없음
            if (allTickets.length > 0 && allTickets.length === hiddenTickets.length) {
                emptyMsg.classList.remove('d-none');
            } else {
                emptyMsg.classList.add('d-none');
            }
        }
    }
</script>

<style>
    /* 과거 티켓 흑백 처리 */
    .grayscale {
        filter: grayscale(100%);
        transition: 0.3s;
    }
    .grayscale:hover {
        filter: grayscale(0%); /* 호버하면 컬러로 */
        opacity: 1 !important;
    }
</style>

<%@ include file="footer.jsp" %>