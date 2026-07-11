@echo off
chcp 65001 >nul
cd /d "%~dp0"
title 民生诉求反馈平台 - 项目检查

echo.
echo 正在执行类型检查、构建检查和接口检查...
call npm run check
if errorlevel 1 (
  echo.
  echo 检查失败，请查看上方错误信息。
  pause
  exit /b 1
)

echo.
echo 检查通过。
pause
