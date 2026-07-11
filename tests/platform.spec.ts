import { expect, test } from "@playwright/test";

test("公众端可实名提交、查询并上传附件", async ({ page }) => {
  await page.goto("/");
  await page.getByRole("button", { name: "实名" }).click();
  await page.getByLabel("所属县区").selectOption("东城区");
  await page.getByLabel("诉求类型").selectOption("城市管理");
  await page.getByLabel("标题").fill("道路井盖松动");
  await page.getByLabel("详细内容").fill("主路井盖松动，车辆经过有明显异响。");
  await page.getByLabel("姓名").fill("李先生");
  await page.getByLabel("联系电话").fill("13900000000");
  await page.getByLabel("附件材料").setInputFiles({
    name: "现场说明.txt",
    mimeType: "application/pdf",
    buffer: Buffer.from("%PDF-1.4\n% test")
  });
  await page.getByRole("button", { name: /提交反馈/ }).click();
  await expect(page.locator(".success-box")).toContainText(/提交成功，查询编号：MS/);
  const codeText = await page.locator(".success-box strong").innerText();

  await page.getByRole("button", { name: /进度查询/ }).click();
  await page.getByPlaceholder("例如：MS202604250001").fill(codeText);
  await page.getByRole("button", { name: "查询", exact: true }).click();
  await expect(page.getByText("道路井盖松动")).toBeVisible();
});

test("县级后台只能办理本县区事项", async ({ page }) => {
  await page.goto("/");
  await page.getByRole("button", { name: /管理后台/ }).click();
  await page.getByPlaceholder("city / county-east / county-west").fill("county-east");
  await page.getByPlaceholder("123456").fill("123456");
  await page.getByRole("button", { name: /登录后台/ }).click();
  await expect(page.getByText("东城区 办理工作台")).toBeVisible();
  await expect(page.locator(".complaint-row").first()).toContainText("东城区");
  await page.getByRole("button", { name: "受理", exact: true }).click();
  await expect(page.getByText("办理状态已更新")).toBeVisible();
  await page.getByRole("button", { name: "转派", exact: true }).click();
  await expect(page.getByText("办理状态已更新")).toBeVisible();
});

test("市级后台看到全市统计且手机号脱敏", async ({ page }) => {
  await page.goto("/");
  await page.getByRole("button", { name: /管理后台/ }).click();
  await page.getByPlaceholder("city / county-east / county-west").fill("city");
  await page.getByPlaceholder("123456").fill("123456");
  await page.getByRole("button", { name: /登录后台/ }).click();
  await expect(page.getByText("市级总览")).toBeVisible();
  await expect(page.getByText("县区事项排行")).toBeVisible();
  await page.getByText("窗口排队时间较长").click();
  await expect(page.getByText("138****0000")).toBeVisible();
  await page.getByRole("button", { name: "督办" }).click();
  await expect(page.getByText("办理状态已更新")).toBeVisible();
});

test("移动端首屏关键控件不横向溢出", async ({ page }) => {
  await page.setViewportSize({ width: 390, height: 844 });
  await page.goto("/");
  await expect(page.getByText("民生诉求反馈平台").first()).toBeVisible();
  await expect(page.getByRole("button", { name: /提交反馈/ })).toBeVisible();
  const overflow = await page.evaluate(() => document.documentElement.scrollWidth > document.documentElement.clientWidth);
  expect(overflow).toBe(false);
});
