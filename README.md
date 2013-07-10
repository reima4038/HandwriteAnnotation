for research
handwrite annotation<br>
<br>

<h2>開発memo：動作にあたっての注意</h2>
利用には環境変数Pathにlibディレクトリを追記する必要あり。<br>
JNIでwin32APIを叩くため、dllを導入している。<br>
これをインポートするために、jarファイルをビルドパスに追加するだけでなく、環境変数への記述が必要となる。<br>