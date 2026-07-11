process.env.NODE_ENV = "test";
async function main() {
    const { app } = await import("./index.js");
    const server = app.listen(0, "127.0.0.1");
    await new Promise((resolve) => server.once("listening", resolve));
    const address = server.address();
    if (!address || typeof address === "string")
        throw new Error("Cannot resolve smoke server address");
    const base = `http://127.0.0.1:${address.port}`;
    const health = await fetch(`${base}/api/health`);
    if (!health.ok)
        throw new Error(`health failed: ${health.status}`);
    const complaint = new FormData();
    complaint.set("mode", "anonymous");
    complaint.set("county", "东城区");
    complaint.set("category", "城市管理");
    complaint.set("title", "自动检查事项");
    complaint.set("content", "用于验证提交接口和查询接口是否可用。");
    const created = await fetch(`${base}/api/complaints`, { method: "POST", body: complaint });
    if (!created.ok)
        throw new Error(`create failed: ${created.status}`);
    const createdJson = (await created.json());
    const queried = await fetch(`${base}/api/complaints/${createdJson.code}`);
    if (!queried.ok)
        throw new Error(`query failed: ${queried.status}`);
    console.log(`smoke ok: ${createdJson.code}`);
    await new Promise((resolve, reject) => server.close((error) => (error ? reject(error) : resolve())));
}
await main();
export {};
