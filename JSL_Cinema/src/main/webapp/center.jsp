<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<style>
    .accordion-item {
        background-color: transparent;
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 8px;
        margin-bottom: 10px;
    }
    .accordion-button {
        background-color: rgba(255, 255, 255, 0.05);
        color: white;
        font-weight: bold;
        box-shadow: none !important; /* 클릭 시 파란 테두리 제거 */
    }
    .accordion-button:not(.collapsed) {
        background-color: rgba(255, 132, 58, 0.2); /* 열렸을 때 배경색 (오렌지 투명) */
        color: var(--jsl-orange);
        border-bottom: 1px solid rgba(255, 132, 58, 0.3);
    }
    .accordion-button::after {
        filter: invert(1); /* 화살표 아이콘 흰색으로 변경 */
    }
    .accordion-button:not(.collapsed)::after {
        filter: invert(0.5) sepia(1) saturate(5) hue-rotate(330deg); /* 열렸을 때 오렌지색 */
    }
    .accordion-body {
        background-color: rgba(0, 0, 0, 0.3);
        color: #ddd;
        line-height: 1.6;
        padding: 20px;
        border-radius: 0 0 8px 8px;
    }
    .q-mark { color: var(--jsl-orange); margin-right: 10px; font-weight: 800; }
</style>

<div class="container main-content" style="max-width: 800px; min-height: 70vh;">
    
    <div class="text-center mb-5">
        <h2 class="fw-bold text-white mb-2">カスタマーセンター</h2>
        <p class="text-secondary">よくあるご質問 (FAQ)</p>
    </div>

    <div class="accordion" id="faqAccordion">
        
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#faq1">
                    <span class="q-mark">Q.</span> 予約のキャンセルはいつまで可能ですか？
                </button>
            </h2>
            <div id="faq1" class="accordion-collapse collapse show" data-bs-parent="#faqAccordion">
                <div class="accordion-body">
                    上映開始の <strong class="text-white">20分前</strong> まで、「マイチケット」ページよりキャンセルが可能です。<br>
                    それ以降のキャンセルおよび払い戻しは致しかねますのでご了承ください。
                </div>
            </div>
        </div>

        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq2">
                    <span class="q-mark">Q.</span> 支払い方法にはどのようなものがありますか？
                </button>
            </h2>
            <div id="faq2" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                <div class="accordion-body">
                    現在は <strong class="text-white">保有ポイントのみ</strong> での決済に対応しております。<br>
                    クレジットカード決済機能は順次導入予定です。
                </div>
            </div>
        </div>

        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq3">
                    <span class="q-mark">Q.</span> 座席の変更はできますか？
                </button>
            </h2>
            <div id="faq3" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                <div class="accordion-body">
                    予約完了後の座席変更はできません。<br>
                    一度予約をキャンセルしていただき、再度ご希望の座席をご予約ください。
                </div>
            </div>
        </div>

        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq4">
                    <span class="q-mark">Q.</span> 学生割引やシニア割引はありますか？
                </button>
            </h2>
            <div id="faq4" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                <div class="accordion-body">
                    はい、ございます。ご入場の際に学生証または年齢確認ができる身分証をご提示ください。<br>
                    割引適用後の差額は現場にてポイントで返還いたします。
                </div>
            </div>
        </div>

    </div>

    <div class="text-center mt-5">
    <p class="text-secondary mb-3">解決しない場合はこちらからお問い合わせください。</p>
    
    <a href="javascript:void(0)" class="btn btn-outline-light px-4 py-2" 
       onclick="showModal('現在、電話相談のみ受け付けております。\n オフィス番号 : 042-241-4424 ', null)">
        <i class="bi bi-envelope me-2"></i> お問い合わせ
    </a>
</div>

</div>

<%@ include file="footer.jsp" %>