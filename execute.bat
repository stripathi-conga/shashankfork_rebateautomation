gradle clean build -P%1 --scan --debug > gradle.txt 2> errorLog.txt | type gradle.txt

@echo off
rem ----------------------------------------------------------------------
rem
rem     Usage: ExecutionFolder.bat [folderNumber] [fileNumber]
rem
rem             [folderNumber]  define number of folder will be 
rem                             create (default=10)
rem 
rem             [fileNumber]    define number of files will be create 
rem                             inside each folder (default=5)
rem
rem             Execute the steps for gradle
rem             
rem             
rem ----------------------------------------------------------------------

setlocal EnableDelayedExpansion
set folderNo=%1

IF NOT DEFINED folderNo set folderNo=10
IF NOT DEFINED fileNo set fileNo=5

echo.
echo   Creation of %folderNo% folder(s) and %fileNo% file(s) started...
echo.
echo   Plese Wait...
echo.
echo.
echo.

for /l %%i in (1,1,%folderNo%) do call :myTask %fileNo%

echo.
echo **********************************************
echo.
echo   Task Finished Successfuly.
echo.
echo **********************************************
echo.
pause


:myTask
set fileNo=%1
set folderName=%random%
md %folderName%
for /l %%i in (1,1,%fileNo%) do dir /q /s > %~dp0%folderName%\!random!.txt
GOTO :eof