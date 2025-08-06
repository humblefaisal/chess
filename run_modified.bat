@echo off
javac --module-path "javafx/lib" --add-modules javafx.controls -d out chess/*.java gui/*.java
jar --create --file ChessAppMod.jar --main-class=gui.ChessApp -C out .
java --module-path javafx/lib --add-modules javafx.controls,javafx.fxml -jar ChessAppMod.jar
pause
