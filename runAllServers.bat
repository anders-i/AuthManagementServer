start cmd.exe /K "call mvn clean package jetty:run"
CD ..\
CD ProductManagementServer
call mvn clean package jetty:run