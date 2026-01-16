<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh;">
    <div class="container seat-page-container" style="padding-top: 0.5rem; padding-bottom: 4rem;">
        <div class="reserve-container-glass seat-selection">
            <h3 class="text-center mb-4" style="font-size: 2rem; letter-spacing: 2px;">〈 座席を選択 〉</h3>
            
            <div class="screen-container mb-5">
                <div class="screen">SCREEN</div>
            </div>

            <div class="seat-wrapper">
                <table class="mx-auto">
                    <c:forEach var="r" begin="1" end="${theater.trow}">
                        <c:set var="rowChar" value="${fn:substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ', r-1, r)}" />
                        <tr>
                            <td class="row-label me-3">${rowChar}</td>
                            <c:forEach var="c" begin="1" end="${theater.tcol}">
                                <c:set var="seatId" value="${rowChar}-${c}" />
                                <%-- 예약 여부 확인 --%>
                                <c:set var="isReserved" value="false" />
                                <c:forEach var="res" items="${reservedSeats}">
                                    <c:if test="${res eq seatId}"><c:set var="isReserved" value="true" /></c:if>
                                </c:forEach>

                                <td>
                                    <div class="seat ${isReserved ? 'reserved' : ''}" 
                                         data-seat="${seatId}" 
                                         onclick="${isReserved ? '' : 'selectSeat(this)'}">
                                        ${c}
                                    </div>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="selection-footer mt-5 d-flex justify-content-between align-items-center">
                <div class="selected-info">
                    <span class="me-3">選択座席 : <b id="display-seats" class="text-orange">-</b></span>
                    <span>合計金額 : <b id="display-price" class="text-orange">0</b> 円</span>
                </div>
                <button class="btn-card-reserve" onclick="goToPayment()">次へ</button>
            </div>
        </div>
    </div>
</div>

<script>
    let selectedSeats = [];
    const PRICE_PER_SEAT = ${schedule.price > 0 ? schedule.price : 15000}; 

    function selectSeat(obj) {
        const seatId = obj.getAttribute('data-seat');

        if (obj.classList.contains('selected')) {
            // 이미 선택된 경우: 주황색 제거 + 배열에서 삭제
            obj.classList.remove('selected');
            selectedSeats = selectedSeats.filter(id => id !== seatId);
        } else {
            // 새로 선택하는 경우: 주황색 추가 + 배열에 삽입
            obj.classList.add('selected');
            selectedSeats.push(seatId);
        }

        updateSelectionInfo();
    }
    
    function updateSelectionInfo() {
        const displaySeats = document.getElementById('display-seats');
        const displayPrice = document.getElementById('display-price');

        if (selectedSeats.length > 0) {
            displaySeats.innerText = selectedSeats.sort().join(', ');
            const total = selectedSeats.length * PRICE_PER_SEAT;
            displayPrice.innerText = total.toLocaleString(); 
        } else {
            displaySeats.innerText = '-';
            displayPrice.innerText = '0';
        }
    }

    function goToPayment() {
    	if (selectedSeats.length === 0) {
            showModal('座席をお選びください。', null);
            return;
        }
        
        const sno = "${schedule.sno}";
        const seatStr = selectedSeats.join(',');
        
        location.href = "payment.do?sno=" + sno + "&seats=" + seatStr;
    }
</script>

<%@ include file="footer.jsp" %>