# 🚀 Resume Work Workflow (Memory Recovery & Activation)

## 📋 Instructions
เมื่อได้รับคำสั่ง `/resume-work` ให้ดำเนินการตามลำดับดังนี้:

1. **Memory Recovery**: 
    - เปิดอ่านไฟล์ [complete-work.md](file:///Users/parinkanthakamala/Documents/workspace/fenix-main/.agents/workflows/complete-work.md) ทันที
    - อ่านหัวข้อ **"Last Known State (AI Memory)"** เพื่อฟื้นฟูความจำว่า ระบบคืออะไร และงานค้างอยู่ที่ไหน
2. **Sync**: รัน `git pull` เพื่อดึงงานล่าสุด
3. **Environment Update**: รัน `./update.sh` เพื่ออัปเดต Extension และบิลด์แอป Fenix
4. **Action**: เริ่มต้นแก้ไขงานในส่วนที่ระบุไว้ใน **Pending Issues** ของไฟล์ `complete-work.md` หรือตามคำสั่งผู้ใช้
5. **Ready**: แจ่งผู้ใช้ว่าฟื้นฟูความจำและสภาพแวดล้อมพร้อมแล้ว พร้อมระบุสั้นๆ ว่าจะทำอะไรต่อ
