JSL-Cinema
JSL人材開発院 Cloud連携Web開発者課程26期 個人プロジェクト
PPT : https://1drv.ms/p/c/563a77a803c69188/IQDQsq1F5ojjT6pv5JmGNXiXAb2cCep_PlfO-0SeCHgZ0-o?e=yaERv9

Project: JSL Cinema
"ユーザーには最高の感動を、管理者には効率的な運営を。" MVCパターン基盤の映画予約およびメンバーシップ管理システム

1. プロジェクト概要 (Overview)
プロジェクト名: JSL Cinema

開発期間: 2026.01.12 ~ 2026.01.16 (5日間の午前集中開発 4時間/日、計20時間：企画含め1週間)

開発人数: 1人 (Full Stack)

開発目的:

Model 2 MVCパターンを適用し、保守性に優れたWebアプリケーションを構築。

複雑なビジネスロジック（座席予約、ポイント決済、上映スケジュール管理）をDBと連動させて実装。

最新トレンドである Glassmorphism（すりガラス風）UI を適用し、差別化されたユーザー体験を提供。

2. 技術スタック (Tech Stack)
🖥️ Backend
Language: Java 11

Web Server: Apache Tomcat 9.0

Database: Oracle DB 11g/18c (OJDBC8)

Framework/Lib: JSP & Servlet, JSTL, COS (File Upload), BCrypt (Security)

🎨 Frontend
Language: HTML5, CSS3, JavaScript (ES6)

Library: Bootstrap 5.3 (Grid System), jQuery (AJAX), Bootstrap Icons

Design Pattern: Glassmorphism UI (Custom CSS)

🛠️ Tools & Environment
IDE: Eclipse IDE 2023-09

VCS: Git & GitHub

DB Tool: SQL Developer

3. システムアーキテクチャ (System Architecture)
[MVC Design Pattern]

Controller (*.do):

MovieController 一つですべてのリクエストを受け取り、Command Patternで分岐処理。

URL管理が容易になり、保守性を最大化。

Model (DAO/DTO):

DBアクセスロジック(DAO)とデータオブジェクト(DTO)を徹底的に分離。

Connection Pool(JNDI)またはDBManagerシングルトンパターンを使用し、リソース効率を確保。

View (JSP):

JSTL/ELを使用し、ViewからJavaコードを排除することで、デザインとロジックの分離を実現。

4. 主な機能 (Key Features)
👤 ユーザー (User)
映画予約システム (Reservation):

映画選択 → 日時選択 → 座席選択 → 決済(ポイント)へと続くワンストッププロセス。

DBトランザクション: 座席予約とポイント消費が同時に行われるよう処理。

マイチケット (My Ticket UX改善):

過去の観覧履歴をデフォルトで非表示(d-none)にし、トグルボタンで閲覧可能にすることで可読性を向上。

観覧完了/予約完了の状態に応じた直感的なバッジ(Badge)UIを提供。

メンバーシップ & ポイント:

会員ランク(Member~MVP)に応じたポイント付与率および割引特典の適用。

⚙️ 管理者 (Admin)
映画登録 (File Upload):

multipart/form-dataリクエスト処理およびサーバーパスへのファイルアップロード実装。

開発環境(Eclipse)とデプロイ環境(Tomcat)のパスの違いを考慮した柔軟なアップロードロジック。

上映スケジュール管理 (Scheduling):

映画と上映館情報を照会し、日時別の上映スケジュール(datetime-local)を登録。

5. トラブルシューティング (Troubleshooting & Challenges)
Issue 1: Eclipseデプロイパスとファイルアップロード消失
問題: 管理者モードでポスター画像をアップロードしたが、サーバー再起動時に画像が消える現象が発生。

原因: Eclipseはsrcフォルダではなく、.metadata内部の仮想デプロイフォルダでサーバーを駆動するため。

解決: 開発段階ではアップロードパスを プロジェクトの実際のローカルパス(Absolute Path) にハードコーディングし、ソースフォルダにファイルが保存されるように措置、データの永続性を確保。

Issue 2: JSTL 日付/文字列処理のエラー
問題: JSP EL表記法内でJavaメソッド(.substring())呼び出し時、Eclipseのバリデーションエラー（赤線）が発生。

解決: JSTL標準関数ライブラリ(xmlns:fn)を導入し、${fn:substring()}構文に代替することで標準準拠およびエラー解決。

Issue 3: 外部ライブラリ(JBCrypt) ビルドパスの競合
問題: プロジェクト位置移動後、ライブラリを参照できずビルド失敗。

解決: .classpath設定ファイルを分析して誤ったパスを削除し、プロジェクト内部のWEB-INF/libパスに再設定して依存性問題を解決。


6. プロジェクト後記 (Conclusion)
単純なCRUDを超え、実際の映画館で使用される予約ロジックを実装することで、DB設計の重要性を体感しました。特にユーザー体験(UX)を考慮したUI改善と、管理者機能を通じたデータ制御まで、WebサービスのA to Zを経験した意義深いプロジェクトでした。
