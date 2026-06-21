# アプリ 動作確認手順（VS Code運用）

## 前提

Android Studioでプロジェクト作成済み。

実機で動作確認する。Android Emulatorは使用しない。

---

## 1. スマホを開発者モードにする

設定
↓
デバイス情報
↓
ビルド番号を7回タップ開発者し、開発者向けオプションを有効化

---

## 2. USBデバッグを有効化する化（初回のみ）

設定
↓
開発者向けオプション
↓
USBデバッグ ON

---

## 3. スマホをPCへ接続する

USBケーブルで接続。接続モードは「ファイル転送」にする。

---

## 4. 実機が認識されているか確認

VS Codeのターミナルで実行。

```powershell
adb devices
```

成功例

```text
List of devices attached
358158352278315 device
```

device と表示されれば接続成功。

---

## 5. VS Codeからアプリを実行

VS Codeで

Ctrl + Shift + B

を押す。

VS Codeは

`.vscode/tasks.json`

に定義されたタスクを実行する。これにより、以下の順番で実行される。

---

### ① Install Debug

実行コマンド

```powershell
.\gradlew.bat :app:installDebug
```

Gradleが起動する。Gradleは
* build.gradle.kts
* settings.gradle.kts
* libs.versions.toml

などを読み込み、必要なライブラリや設定を解決する。

その後、Kotlinソースをコンパイルし、APKを生成し、接続中のスマホへインストールする。

Android Studioの `RunBuild　→　Build & Installlll`　に相当する。

---

### ② Launch App

実行コマンド

```powershell
adb shell am start -n com.renat.phrasecollection/.MainActivity
```

ADB経由でAndroidへ「MainActivityを起動せよ」という命令を送る。

Androidがアプリを自動で起動させるため。

---

## 6. 動作確認

処理が成功すると、スマホ上でが当アプリが起動する。

---
