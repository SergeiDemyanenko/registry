@echo off

set targetDrive=Z:\
set localDrive=%temp%\

set targetDir=target
set sourceDir=source

set dbDir=db

set sourceDbDir=%sourceDir%\%dbDir%
set targetDbDir=%targetDir%\%dbDir%

set cmdFile=\recreateDB.cmd

if not exist %targetDrive% (
  @echo on
  echo You have to map network drive %targetDrive%
  echo.
  pause
) else (
  if not exist %targetDrive%%sourceDbDir% (
    @echo on
    echo Drive %targetDrive% has to by map to project folder
    echo.
    pause  
  ) else (
    if exist %localDrive%%sourceDir%\ (
      rd %localDrive%%sourceDir% /S /Q
    )
    xcopy %targetDrive%%sourceDbDir% %localDrive%%sourceDbDir% /Y /E /I

    pushd %localDrive%%sourceDbDir%
    call %localDrive%%sourceDbDir%%cmdFile%
    pushd %~dp0
    
    if exist %targetDrive%%targetDbDir% (
      rd %targetDrive%%targetDbDir% /S /Q
    )
    xcopy %localDrive%%targetDbDir% %targetDrive%%targetDbDir% /Y /E /I

    rd %localDrive%%sourceDir% /S /Q
    rd %localDrive%%targetDir% /S /Q
  )
)