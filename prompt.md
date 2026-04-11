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
