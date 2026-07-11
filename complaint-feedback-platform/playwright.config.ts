import { defineConfig, devices } from "@playwright/test";

const chromiumPath = "C:/Users/33072/AppData/Local/ms-playwright/chromium-1217/chrome-win64/chrome.exe";

export default defineConfig({
  testDir: "./tests",
  timeout: 60_000,
  use: {
    baseURL: "http://127.0.0.1:4173",
    launchOptions: {
      executablePath: chromiumPath
    },
    trace: "on-first-retry"
  },
  webServer: {
    command: "npm run build && npm run start",
    url: "http://127.0.0.1:3000",
    reuseExistingServer: false,
    timeout: 120_000
  },
  projects: [
    { name: "desktop", use: { ...devices["Desktop Chrome"], baseURL: "http://127.0.0.1:3000" } },
    { name: "mobile", use: { ...devices["Pixel 5"], baseURL: "http://127.0.0.1:3000" } }
  ]
});
