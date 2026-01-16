<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <footer>
        <div class="container">
            <span class="footer-logo">JSL Cinema</span>
            <p class="mb-1">JSLシネマ | 大田広域市中区鶏龍路825 希英ビル7階</p>
            <p class="mb-3">お問い合わせ: 042-241-4424</p>
            <hr class="border-secondary">
            <div class="d-flex justify-content-between small">
                <span>&copy; 2026 JSL 26th Group. All Rights Reserved.</span>
                <div>
                    <a href="terms.do" class="text-decoration-none text-secondary me-3">利用規約</a>
                    <a href="privacy.do" class="text-decoration-none text-secondary fw-bold">プライバシーポリシー</a>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
	<div id="customModal" class="modal-overlay">
		<div class="glass-modal">
		    <div class="modal-title">NOTICE</div>
		    <div id="modalMessage" class="modal-message"></div>
		    <button class="btn-modal-ok" onclick="closeModal()">確認</button>
		</div>
	</div>

<script>
	let targetFocus = null; // 닫은 후 포커스 이동할 요소 저장
	
	function showModal(msg, focusElement) {
	    document.getElementById("modalMessage").innerText = msg; // 메시지 주입
	    document.getElementById("customModal").style.display = "flex"; // 모달 보이기
	    targetFocus = focusElement; // 포커스 타겟 저장
	}
	
	function closeModal() {
	    document.getElementById("customModal").style.display = "none"; // 모달 숨기기
	    if (targetFocus) {
	        targetFocus.focus(); // 저장된 요소로 포커스 이동
	        targetFocus = null;
	    }
	}
</script>

</body>
</html>