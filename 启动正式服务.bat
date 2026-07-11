@echo off
chcp 65001 >nul
cd /d "%~dp0"
title 民生诉求反馈平台 - 正式服务

echo.
echo ========================================
echo   民生诉求反馈平台 - 正式服务启动
echo ========================================
echo.

if not exist "node_modules" (
  echo [1/3] 正在安装依赖，请稍候...
  call npm install --registry=https://registry.npmmirror.com
  if errorlevel 1 goto fail
) else (
  echo [1/3] 已检测到 node_modules，跳过依赖安装。
)

if not exist "dist\server\index.js" (
  echo [2/3] 未检测到生产构建，正在构建...
  call npm run build
  if errorlevel 1 goto fail
) else (
  echo [2/3] 已检测到 dist，跳过构建。
)

echo [3/3] 正在启动服务...
echo.
echo 本机访问地址: http://127.0.0.1:3000
echo 手机同一 Wi-Fi 访问时，请使用本机局域网 IP + 3000 端口。
echo.
start "" "http://127.0.0.1:3000"
call npm run start
goto end

:fail
echo.
echo 启动失败，请查看上方错误信息。
pause

:end
