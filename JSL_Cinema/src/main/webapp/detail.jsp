<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<div class="detail-hero"
	style="--poster-url: url('${pageContext.request.contextPath}/poster/${movie.poster}');">
	<div class="container">
		<div class="row align-items-center">
			<div class="col-md-4 text-center">
				<img src="${pageContext.request.contextPath}/poster/${movie.poster}"
					class="detail-poster" alt="${movie.title}">
			</div>

			<div class="col-md-8">
				<div class="movie-info-wrap">
					<div class="info-top">
						<p class="catchphrase">「 ${movie.catchphrase} 」</p>
						<h1 class="detail-title">${movie.title}</h1>

						<div class="meta-data mb-4">
						    <span class="meta-item">
						        <i class="bi bi-clock me-1"></i> 上映時間 : ${movie.runtime}分
						    </span> 
						    <span class="meta-item ms-3">
						        <i class="bi bi-calendar-event me-1"></i> 公開日 : ${movie.opendate.substring(0, 10)}
						    </span>
						    
						    <span class="meta-item ms-3 text-warning">
						        <i class="bi bi-star-fill me-1"></i> 平点 : 
						        <span class="fw-bold">${movie.score}</span>
						    </span>
						</div>

						<h3 class="synopsis-title">INTRODUCTION</h3>
						<p class="synopsis-text mt-3">${movie.content}</p>
					</div>
					<div class="info-bottom text-end d-flex justify-content-end align-items-center gap-2">
					    
					    <c:if test="${not empty sessionScope.userid}">
					        <button id="btn-like" class="btn-like-circle" onclick="toggleLike()">
					            <i class="bi ${isLiked ? 'bi-heart-fill text-danger' : 'bi-heart'}"></i>
					        </button>
					    </c:if>
					
					    <a href="reserve.do?mno=${movie.mno}" class="btn-card-reserve">
					        オンライン予約 <i class="bi bi-arrow-right-circle ms-2"></i>
					    </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="reserve-bar">
	<div
		class="container d-flex justify-content-between align-items-center" style = "padding-top : 22px;">
		<div>
			<span class="small fw-light">JSL CINEMA <span
				class="text-secondary mx-2">|</span> 公式オンラインチケット予約
			</span>
		</div>
		<h5 class="m-0 fw-normal italic">${movie.title}</h5>
	</div>
</div>

<div class="container mt-5 mb-5">
    <div class="reserve-container-glass p-4">
        <h4 class="text-white mb-4"><i class="bi bi-star-fill text-warning me-2"></i> Review & Rating</h4>
        
        <form id="reviewForm">
            <input type="hidden" name="mno" value="${movie.mno}">
            <input type="hidden" name="rating" id="ratingInput" value="5"> <div class="d-flex align-items-center mb-3">
                <span class="text-secondary me-3">評価 :</span>
                <div class="star-rating">
                    <i class="bi bi-star-fill" data-score="1"></i>
                    <i class="bi bi-star-fill" data-score="2"></i>
                    <i class="bi bi-star-fill" data-score="3"></i>
                    <i class="bi bi-star-fill" data-score="4"></i>
                    <i class="bi bi-star-fill" data-score="5"></i>
                </div>
                <span id="star-text" class="ms-3 text-warning fw-bold">5.0 / 5.0</span>
            </div>
            
            <div class="input-group">
                <textarea class="form-control bg-transparent text-white" name="content" rows="3" 
                          placeholder="映画の感想を聞かせてください。" ${empty sessionScope.userid ? 'disabled' : ''}></textarea>
                <button type="button" class="btn btn-card-reserve" onclick="addReview()">登録</button>
            </div>
        </form>
    </div>
</div>

<div class="container mb-5">
    <h5 class="text-white mb-3">User Reviews <span class="text-secondary small">(${reviewList.size()})</span></h5>
    <div id="review-list-area">
        <c:forEach var="review" items="${reviewList}">
            <div class="review-item border-bottom border-secondary py-3">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="text-warning mb-1">
                            <c:forEach begin="1" end="${review.rating}">★</c:forEach>
                            <c:forEach begin="1" end="${5 - review.rating}">☆</c:forEach>
                        </div>
                        <p class="text-white mb-1">${review.content}</p>
                    </div>
                    <div class="text-end">
                        <span class="text-orange small">${review.userid}</span><br>
                        <span class="text-secondary x-small">${review.regdate}</span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="footer.jsp"%>

<script>
// === 1. 별점 UI 로직 ===
const stars = document.querySelectorAll('.star-rating i');
const ratingInput = document.getElementById('ratingInput');
const starText = document.getElementById('star-text');

stars.forEach(star => {
    // 클릭했을 때 점수 확정
    star.addEventListener('click', function() {
        const score = this.getAttribute('data-score');
        ratingInput.value = score;
        starText.innerText = score + ".0 / 5.0";
        
        // 별 색깔 채우기
        stars.forEach(s => {
            if(s.getAttribute('data-score') <= score) {
                s.classList.add('active');
                s.classList.remove('bi-star');
                s.classList.add('bi-star-fill');
            } else {
                s.classList.remove('active');
                s.classList.remove('bi-star-fill');
                s.classList.add('bi-star');
            }
        });
    });
});

// 초기화: 5점 만점으로 시작
stars.forEach(s => s.classList.add('active'));


// === 2. 좋아요(Like) AJAX ===
function toggleLike() {
    const mno = "${movie.mno}";
    
    // AJAX 요청 (비동기)
    fetch('toggleLike.do?mno=' + mno)
        .then(response => response.text()) // 서버에서 "true" 또는 "false" 문자열 리턴 가정
        .then(data => {
            const icon = document.querySelector('#btn-like i');
            if (data.trim() === 'true') {
                // 좋아요 성공 (빨간 하트)
                icon.classList.remove('bi-heart');
                icon.classList.add('bi-heart-fill');
                icon.classList.add('text-danger');
            } else {
                // 좋아요 취소 (빈 하트)
                icon.classList.remove('bi-heart-fill');
                icon.classList.remove('text-danger');
                icon.classList.add('bi-heart');
            }
        })
        .catch(error => console.error('Error:', error));
}


// === 3. 리뷰 등록 AJAX ===
function addReview() {
    const userid = "${sessionScope.userid}";
    if(!userid) {
        showModal("ログイン後にご利用いただけます。", null); // 우리가 만든 모달 활용
        return;
    }

    const form = document.getElementById('reviewForm');
    const formData = new FormData(form);

    // form 데이터를 쿼리 스트링으로 변환
    const params = new URLSearchParams(formData);

    fetch('insertReview.do', {
        method: 'POST',
        body: params
    })
    .then(response => response.text())
    .then(result => {
        if(result.trim() === 'success') {
            // 성공 시 페이지 새로고침 (또는 리스트만 갱신 가능하지만 일단 간단히)
            location.reload(); 
        } else {
            showModal("レビュー登録に失敗しました。", null);
        }
    });
}
</script>