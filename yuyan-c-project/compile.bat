@echo off
chcp 65001 > nul
echo 正在编译C程序...

REM 编译start.c
echo 编译启动程序...
gcc -o start.exe start.c -lpsapi
if %errorlevel% neq 0 (
    echo 编译start.c失败
    pause
    exit /b 1
)

REM 编译stop.c
echo 编译停止程序...
gcc -o stop.exe stop.c -lpsapi
if %errorlevel% neq 0 (
    echo 编译stop.c失败
    pause
    exit /b 1
)

echo 编译完成！
pause