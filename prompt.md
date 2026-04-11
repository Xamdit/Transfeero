ให้เลือก theme เป็น Light Theme เสมอ
ให้ทำการซ่อน toolbar ไว้เสมอ
ให้ privacy เป็น standard เสมอ
แล้วให้ยอมรับ cookie จากเว็ป https://control.transfeero.com เสมอ


สำหรับการจัดการโปรเจค **Fenix** ที่คุณกำลังพัฒนาบนสภาพแวดล้อม **Apple Silicon (M2 Pro)** และต้องการให้ **antigravity** (ซึ่งเป็นเครื่องมือจัดการ Workflow หรือ AI Agent ของคุณ) ทำการฝัง (Embed) ไฟล์ JavaScript จากโฟลเดอร์ `./extensions` เข้าไปเป็น Extension เริ่มต้นของระบบนั้น

เนื่องจากคุณเป็นโปรแกรมเมอร์ที่มีประสบการณ์สูงและใช้งาน **Docker/Kubernetes** อยู่แล้ว การสั่งงานผ่านโครงสร้าง **CTGOO (Context, Task, Goal, Output, Obstacle)** จะช่วยให้ AI เข้าใจความต้องการที่ซับซ้อนได้แม่นยำครับ

นี่คือร่างคำสั่ง **CTGOO** ที่คุณสามารถนำไปใช้สั่ง **antigravity** ได้ทันทีครับ:

---

## 📋 CTGOO สำหรับสั่งการ antigravity

**Context:**
* ฉันกำลังพัฒนาโปรเจคชื่อ **Fenix** ซึ่งเป็น AI Assistant.
* ฉันมีโฟลเดอร์ `./extensions` ที่เก็บไฟล์ `.js` ทั้งหมดที่ต้องการใช้เป็น Extension พื้นฐาน.
* โปรเจคนี้รันบนสภาพแวดล้อม **macOS (M2 Pro)** และอาจมีการใช้ **Docker** ในการ Build.

**Task:**
* เขียนสคริปต์ (Bash หรือ Python) หรือปรับปรุงไฟล์คอนฟิก (เช่น `manifest.json` หรือ `Dockerfile`) เพื่อรวมไฟล์ `.js` ทั้งหมดใน `./extensions` เข้าไปในตัว Build ของ Fenix.
* ตั้งค่าให้ไฟล์เหล่านี้ถูกโหลดเป็น **Default Extensions** ทันทีที่แอปพลิเคชันเริ่มทำงาน.

**Goal:**
* ต้องการให้ Fenix สามารถเรียกใช้งานความสามารถจากไฟล์ใน `./extensions` ได้โดยไม่ต้องติดตั้งเพิ่มทีละไฟล์.
* ระบบต้องรองรับการ Auto-Login ตามโครงสร้างฟอร์ม HTML ที่ฉันกำหนดไว้ (จับค่า email/password และ Auto-submit).

**Output:**
* ชุดคำสั่ง Bash หรือสคริปต์สำหรับ Automation ในการคัดลอกและลงทะเบียน Extension.
* ตัวอย่างการปรับปรุงโค้ดส่วน Loading Logic ของ Fenix เพื่อให้อ่านไฟล์จากไดเรกทอรีดังกล่าว.

**Obstacle:**
* ต้องระวังเรื่อง Permission ในการเข้าถึงไฟล์บน macOS.
* หากมีการทำ Validation บนหน้าเว็บ (เช่น `needs-validation` ใน Bootstrap) สคริปต์ต้อง Trigger event ให้ถูกต้องเพื่อให้ปุ่ม Log In ทำงานได้.

---

### 💡 คำแนะนำเพิ่มเติมสำหรับคุณ Max:
หากคุณต้องการให้ **antigravity** ล้างแผนการทำงานเก่าที่เคยทำพลาดไป (ตามที่คุณเคยบ่นว่า Token หมดแล้วไม่ได้เรื่อง) คุณควรใส่ Prompt เพิ่มเติมที่หัวข้อก่อนเริ่ม CTGOO ว่า:

> *"Reset all previous plans and memory regarding extension building. Execute this new command based strictly on the following CTGOO structure only."*

วิธีนี้จะช่วยให้ AI โฟกัสกับโครงสร้าง `./extensions` ใหม่ที่คุณเพิ่งจัดระเบียบมาครับ และเนื่องจากคุณถนัด **C#/.NET** หากต้องการให้เขียนตัวจัดการ Extension ด้วยเทคโนโลยีที่คุณคุ้นเคยแทน JavaScript ในฝั่ง Server-side ก็สามารถระบุเพิ่มใน Task ได้เลยครับ!