<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="header.jsp" %>

    <main class="container main-content">
        
        <div class="d-flex justify-content-between align-items-end mb-3">
            <div>
                <h3 class="fw-bold text-white m-0">BOXOFFICE</h3>
                <p class="text-secondary m-0 small">現在公開中の映画 : NOW PLAYING</p>
            </div>
			<span class="text-secondary small">
    		    <i class="bi bi-mouse me-1"></i> Scroll or Drag <i class="bi bi-arrows-move ms-1"></i>
		    </span>
        </div>

        <div class="movie-scroll-container">
            <c:forEach var="movie" items="${movieList}">
                <div class="movie-card">
                    <div class="poster-wrapper">
                        <a href="detail.do?mno=${movie.mno}">
              				<img src="${pageContext.request.contextPath}/poster/${movie.poster}" class="card-img-top" alt="${movie.title}">
            			</a>
                    </div>
                    
                    <div class="card-body px-1">
                        <a href="detail.do?mno=${movie.mno}" class="text-decoration-none">
              				<h5 class="card-title fw-bold text-truncate mb-1">${movie.title}</h5>
            			</a>
                        
                        <div class="d-flex justify-content-between align-items-center mt-2 mb-3">
				            <small class="text-secondary" style="font-size: 0.85rem;">
				                ${movie.opendate.substring(0, 10)} 公開
				            </small>
				            <small class="${movie.score > 0 ? 'text-warning' : 'text-secondary'} fw-bold bg-dark px-2 py-1 rounded-pill" style="font-size: 0.8rem;">
				                <i class="bi bi-star-fill me-1"></i> ${movie.score}
				            </small>
				        </div>
                        
                        <a href="reserve.do?mno=${movie.mno}" class="btn btn-reserve w-100 shadow-sm">
                            <i class="bi bi-ticket-perforated me-1"></i> チケット予約
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>

    <section class="event-section mt-4 mb-5">
        <div class="container">
            <h4 class="fw-bold text-white mb-4 ps-2 border-start border-4 border-warning">Event & Promotion</h4>
            <div class="row g-4">
                <div class="col-md-6">
                    <div class="p-4 rounded-3 text-white h-100 d-flex align-items-center" 
                         style="background: linear-gradient(135deg, #2c3e50, #3498db); min-height: 120px;">
                        <div>
                            <span class="badge bg-warning text-dark mb-2">NEW</span>
                            <h4>新規会員 : ポップコーン無料クーポン!</h4>
                            <p class="mb-0 small opacity-75">ポップコーン無料クーポン。</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="p-4 rounded-3 text-white h-100 d-flex align-items-center" 
                         style="background: linear-gradient(135deg, #42275a, #734b6d); min-height: 120px;">
                         <div>
                            <span class="badge bg-danger mb-2">HOT</span>
                            <h4>毎週金曜日 : 全作品 50% OFF</h4>
                            <p class="mb-0 small opacity-75">毎週金曜日, 全作品 50% OFF！</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
<script>
	const scrollContainer = document.querySelector('.movie-scroll-container');
	
	// 1. 마우스 휠로 가로 스크롤 하기
	scrollContainer.addEventListener('wheel', (evt) => {
	    evt.preventDefault();
	    scrollContainer.scrollLeft += evt.deltaY; 
	}, { passive: false });
	
	// 2. 마우스 클릭 드래그로 스크롤 하기
	let isDown = false;
	let startX;
	let scrollLeft;
	
	scrollContainer.addEventListener('mousedown', (e) => {
	    isDown = true;
	    scrollContainer.classList.add('active');
	    startX = e.pageX - scrollContainer.offsetLeft;
	    scrollLeft = scrollContainer.scrollLeft;
	});
	
	scrollContainer.addEventListener('mouseleave', () => {
	    isDown = false;
	    scrollContainer.classList.remove('active');
	});
	
	scrollContainer.addEventListener('mouseup', () => {
	    isDown = false;
	    scrollContainer.classList.remove('active');
	});
	
	scrollContainer.addEventListener('mousemove', (e) => {
	    if (!isDown) return;
	    e.preventDefault();
	    const x = e.pageX - scrollContainer.offsetLeft;
	    const walk = (x - startX) * 2; 
	    scrollContainer.scrollLeft = scrollLeft - walk;
	});
</script>

<%@ include file="footer.jsp" %>