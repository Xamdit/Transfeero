# Transfeero Autopilot Extension

ส่วนขยาย (Extension) นี้ถูกสร้างขึ้นมาเพื่อทำงานเป็นบอทอัตโนมัติ (Autopilot) บนเบราว์เซอร์ Firefox (Fenix) สำหรับแพลตฟอร์ม Transfeero โดยมีหน้าที่หลักในการสแกนหางานใหม่ (Offers), กรองงานตามเงื่อนไขที่กำหนด, และกดรับงานโดยอัตโนมัติ

## สถาปัตยกรรม (Architecture)

ตัว Extension ถูกแบ่งการทำงานออกเป็น 3 ส่วนหลัก ได้แก่:

### 1. Background Worker (`assets/index.ts.js`)
ทำหน้าที่เป็นสมองกลหลักของบอท ทำงานอยู่เบื้องหลังเสมอ
- **Main Loop (`Me`)**: ควบคุมจังหวะการทำงานของบอท (Delay, Pause, Rate Limit)
- **API Fetching**: ยิง Request ไปยัง API ของ Transfeero (ผ่าน Content Script เพื่อเลี่ยง CORS/Cloudflare)
- **Filtering**: ตรวจสอบเงื่อนไขที่ผู้ใช้ตั้งไว้ (เช่น ระยะทางสูงสุด, ราคาต่ำสุด, วันเวลา, ประเภทรถ)
- **Auto-Bidding**: หากงานผ่านเงื่อนไข จะทำการยิง API รับงานทันที
- **Visual Refresh**: สั่งให้หน้าเว็บ (`control.transfeero.com`) รีเฟรชภาพตามเวลาที่ตั้งไว้ เพื่อให้ผู้ใช้เห็นความเคลื่อนไหว

### 2. Content Script (`assets/index.tsx.js`)
สคริปต์ที่ถูกฝัง (Inject) ลงไปในหน้าเว็บของ Transfeero
- **Data Bridge**: เป็นสะพานเชื่อมระหว่าง Android UI และ Background Worker โดยรับคำสั่งผ่าน `window.postMessage` (เหตุการณ์ `SYNC_FROM_ANDROID_UI`) และบันทึกลง `chrome.storage.local`
- **Credential Extraction**: ดูดค่า `hash` และ `token` จาก Cookie เพื่อให้บอทนำไปใช้ยิง API
- **Proxy Fetcher**: เป็นตัวกลางในการยิง `fetch` API ออกไป เพื่อให้ Request ดูเหมือนออกมาจากหน้าเว็บจริงๆ
- **UI Interaction**: แสดงกล่องข้อความแจ้งเตือน (Toast) เช่น `[Debug] API Scanned` เมื่อมีการสแกนงาน

### 3. User Interface (`src/pages/panel/index.html`)
หน้าจอควบคุมบอทที่เปิดผ่านปุ่ม FAB ภายในแอป Android
- **Settings Tab**: ตั้งค่าความถี่ (Delay) และการหยุดพัก (Pause) ของบอท
- **Filter Tab**: ตั้งค่าเงื่อนไขการรับงาน เช่น ระยะทาง, ราคา, เวลา, และจุดรับส่ง (Coordinations)
- **Communication**: เมื่อผู้ใช้กดปุ่ม UI จะส่งข้อมูลผ่าน Interface `window.Android.syncToBot(...)` กลับไปยังฝั่ง Android เพื่อส่งต่อให้ Content Script

---

## Data Flow (การไหลของข้อมูล)

การสื่อสารระหว่าง Android UI ไปจนถึง Background Bot เกิดขึ้นดังนี้:

1. **User Input**: ผู้ใช้ตั้งค่าในหน้าต่าง WebView (Settings/Filters)
2. **Android Bridge**: ข้อมูลถูกส่งเข้า Java ผ่าน `@JavascriptInterface` (`syncToBot`) ใน `HomeActivity.kt`
3. **JS Injection**: Android นำข้อมูลที่ได้ มาสร้างเป็นโค้ด JavaScript (`window.postMessage`) และ Inject ลงในหน้าต่าง `control.transfeero.com` ที่กำลังเปิดอยู่ (พร้อมกับแนบค่า `isDebug` จาก `app-config.json`)
4. **Content Script Intercept**: `index.tsx.js` ดักจับ Message `SYNC_FROM_ANDROID_UI` และนำข้อมูล Settings, Filters, Status, และ isDebug ไปบันทึกลงใน `chrome.storage.local`
5. **Background Reacts**: `index.ts.js` อ่านค่าจาก `chrome.storage.local` นำไปปรับใช้กับการสแกนรอบถัดไป หากบอทเปิดอยู่ก็จะเริ่มลูปการทำงานทันที

---

## โหมดผู้พัฒนา (Debug Mode)

คุณสามารถเปิดใช้งาน Debug Mode ได้โดยการแก้ไฟล์ `app-config.json` ที่ Root ของโปรเจกต์:

```json
{
  ...
  "debug": true
}
```

เมื่อตั้งค่าเป็น `true` บอทจะทำการแสดง Toast สีดำที่มุมขวาล่างของจอในทุกๆ ครั้งที่มีการดึง API หางาน (`[Debug] API Scanned: Found X offers`) ซึ่งช่วยให้รู้ว่าตัวบอทกำลังทำงานอยู่จริงแม้หน้าจอเว็บจะยังไม่รีเฟรชก็ตาม

---

## การติดตั้งและการ Build

Extension ตัวนี้จะถูกนำไปติดตั้งลงในแอป Fenix ผ่านสคริปต์ `./update.sh`
1. สคริปต์จะคัดลอกไฟล์ทั้งหมดในโฟลเดอร์นี้ไปที่ `app/src/main/assets/extensions/autopilot`
2. สร้าง `manifest.json` ที่เข้ากันได้กับ GeckoView 
3. เมื่อแอปเปิดขึ้น GeckoView จะทำการโหลด Extension ตัวนี้ขึ้นมาใช้งานอัตโนมัติ