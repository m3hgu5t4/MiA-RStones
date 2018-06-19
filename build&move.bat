SET JAVA_HOME="C:\Program Files (x86)\Java\jdk1.8.0_05"
SET mia_relic_location="%USERPROFILE%\Desktop\cod stuf\mineinabyss\spaghetti\plugins\MineInAbyss\relics"
gradle build
move build\libs\*.* %mia_relic_location%
pause
