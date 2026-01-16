<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh; padding-top: 4rem;">
    <div class="container" style="max-width: 800px;">
        
        <h3 class="fw-bold text-white mb-4 ps-3 border-start border-4 border-warning">ADMIN : 映画登録</h3>

        <div class="reserve-container-glass p-5">
            <form action="adminMovieInsert.do" method="post" enctype="multipart/form-data">
                
                <div class="mb-4">
                    <label class="text-secondary small mb-1">映画タイトル</label>
                    <input type="text" name="title" class="form-control bg-dark text-white border-secondary" required placeholder="例: GLADIATOR II">
                </div>

                <div class="mb-4">
                    <label class="text-secondary small mb-1">キャッチコピー</label>
                    <input type="text" name="catchphrase" class="form-control bg-dark text-white border-secondary" placeholder="映画の核心となる一文">
                </div>

                <div class="mb-4">
                    <label class="text-secondary small mb-1">あらすじ</label>
                    <textarea name="content" class="form-control bg-dark text-white border-secondary" rows="5" required></textarea>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="text-secondary small mb-1">公開日</label>
                        <input type="date" name="opendate" class="form-control bg-dark text-white border-secondary" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="text-secondary small mb-1">上映時間 (分)</label>
                        <input type="number" name="runtime" class="form-control bg-dark text-white border-secondary" required placeholder="120">
                    </div>
                </div>

                <div class="mb-5">
                    <label class="text-secondary small mb-1">ポスター画像</label>
                    <input type="file" name="poster" class="form-control bg-dark text-white border-secondary" required>
                    <div class="form-text text-secondary">* 推奨サイズ: 2000x3000 (jpg, png)</div>
                </div>

                <div class="text-end">
                    <button type="button" class="btn btn-outline-light me-2" onclick="history.back()">キャンセル</button>
                    <button type="submit" class="btn btn-reserve px-5">登録する</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>