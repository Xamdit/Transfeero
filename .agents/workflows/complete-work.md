# 🏁 Complete Work Workflow (Instruction & State)

## 📋 Instructions
เมื่อได้รับคำสั่ง `/complete-work` ให้ดำเนินการตามลำดับดังนี้:
1. **Stage & Commit**: รัน `git add .` และ `git commit` ด้วยข้อความที่สรุปงาน
2. **Push**: รัน `git push` งานขึ้น Repository
3. **Capture State**: สรุปสถานะปัจจุบันลงในหัวข้อ "Last Known State" ด้านล่างนี้ (Overwrite หรือ Append) โดยระบุ:
    - **Context**: โปรเจกต์นี้คืออะไร (Fenix - AI Assistant / Android Browser)
    - **Done**: สิ่งที่ทำสำเร็จแล้วใน Turn นี้
    - **Pending**: งานที่ยังค้างอยู่ หรือปัญหาที่ต้องแก้ต่อ
4. **Summary**: แจ้งผู้ใช้ว่าบันทึกงานและสถานะเรียบร้อยแล้ว

---
## 🧠 Last Known State (AI Memory)
**Project Context**: Fenix (Firefox for Android fork) พัฒนาเพื่อเป็น AI Assistant/Browser สำหรับ Transfeero โดยมีการติดตั้ง Extension อัตโนมัติ

**Latest Progress**:
- แก้ไข Build Failed ใน `GeckoProvider.kt` โดยใช้ `arguments` และ `configFilePath` เรียบร้อยแล้ว
- บิลด์ผ่านและสามารถผลิต APK ได้สำเร็จ รองรับ Android 8-15 (API 26-34)
- ปรับปรุง `accept-cookie.js` ให้รองรับ Shadow DOM และ CookieYes selectors อย่างสมบูรณ์
- Hard-disable `StrictMode` ใน `StrictModeManager.kt` เพื่อแก้ปัญหา App Crash บนบางอุปกรณ์ (เช่น Vivo)
- เพิ่ม Diagnostic Logs (StartupStep 0-10) ใน `FenixApplication.kt` เพื่อระบุตำแหน่งที่แอป Crash เมื่อ Restart
- ตั้งค่าระบบ Workflow `/complete-work` และ `/resume-work` ผ่าน `.agents/workflows/`

**Pending Issues**:
- ตรวจสอบ Logcat เพื่อหาค่า "StartupStep" สุดท้ายก่อนที่แอปจะ Crash เมื่อ Restart (เพื่อระบุ Module ที่มีปัญหา)
- `InvalidSignatureError` ยังปรากฏใน Log ของ GeckoView ต้องหาวิธีแก้ในระดับ Deep config ต่อไปหากต้องการความสมบูรณ์
- ทดสอบการทำงานของ `accept-cookie.js` บน Shadow DOM ของจริงในอุปกรณ์เป้าหมาย
