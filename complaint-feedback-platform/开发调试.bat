@echo off
chcp 65001 >nul
cd /d "%~dp0"
title 民生诉求反馈平台 - 开发调试

echo.
echo ========================================
echo   民生诉求反馈平台 - 开发调试模式
echo ========================================
echo.

if not exist "node_modules" (
  echo 正在安装依赖，请稍候...
  call npm install --registry=https://registry.npmmirror.com
  if errorlevel 1 goto fail
)

echo 前端地址: http://127.0.0.1:5173
echo 后端地址: http://127.0.0.1:3000
echo.
start "" "http://127.0.0.1:5173"
call npm run dev
goto end

:fail
echo.
echo 启动失败，请查看上方错误信息。
pause

:end
