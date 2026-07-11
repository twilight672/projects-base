@echo off
chcp 65001 >nul
cd /d "%~dp0"
title 民生诉求反馈平台 - 重新构建

echo.
echo 正在重新构建生产文件...
call npm run build
if errorlevel 1 (
  echo.
  echo 构建失败，请查看上方错误信息。
  pause
  exit /b 1
)

echo.
echo 构建完成。
pause
