@echo off

call mvn -f C:\Users\Vlas\Desktop\senla-final-project\classifiedapi\pom.xml clean
call mvn -f C:\Users\Vlas\Desktop\senla-final-project\classifiedapi\pom.xml package

call java -jar C:\Users\Vlas\Desktop\senla-final-project\classifiedapi\target\classified-api-docker.war

pause