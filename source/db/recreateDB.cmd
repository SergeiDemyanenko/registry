set targetDir=..\..\target\db
set targetFile=db.fdb
if not exist %targetDir% (mkdir %targetDir%)
if exist %targetDir%\%targetFile% (del /F /Q %targetDir%\%targetFile%)
isql -input ddl.sql