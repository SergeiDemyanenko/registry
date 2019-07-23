set targetDir=..\..\target\db
set targetFile=db.fdb
if exist %targetDir%\%targetFile% (del /F /Q %targetDir%\%targetFile%)
if not exist %targetDir% (mkdir %targetDir%)
isql -input ddl.sql
copy %targetFile% %targetDir%\%targetFile% /Y
del /F /Q %targetFile%