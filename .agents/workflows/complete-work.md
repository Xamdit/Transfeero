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
- บิลด์ผ่านและสามารถผลิต APK ได้สำเร็จ
- ปรับปรุง `disable-chat.js` ให้ครอบคลุมการซ่อน Intercom Messenger Frame
- ตรวจสอบ Selectors ของหน้า Login `control.transfeero.com` แล้วว่าตรงกับ `auto-login.js`
- ตั้งค่าระบบ Workflow `/complete-work` และ `/resume-work` ผ่าน `.agents/workflows/`

**Pending Issues**:
- `InvalidSignatureError` ยังปรากฏใน Log ของ GeckoView (แม้แอปจะทำงานได้) ต้องหาวิธีแก้ในระดับ Deep config ต่อไปหากต้องการความสมบูรณ์
- ต้องทดสอบการทำงานของ `accept-ride.js` บนหน้างานจริงเมื่อมี Booking เข้ามา
