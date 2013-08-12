@echo off

:: Startup script for Tinustris.
:: This script assumes that a valid javaw.exe, version 1.7 or later, is on the PATH.

set PWD="%CD%"

set LIB_DIR=%PWD%\..\lib

set CONFIG_DIR=%PWD%\..\etc

set NATIVES_DIR=%PWD%\..\natives

set JAVA_OPTS=-Djava.library.path=%NATIVES_DIR%

set JAVA=javaw

start %JAVA% %JAVA_OPTS% -cp %CONFIG_DIR%;%LIB_DIR%\* nl.mvdr.tinustris.gui.Tinustris