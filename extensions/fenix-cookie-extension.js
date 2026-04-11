// fenix-cookie-extension.js

const cookieKeywords = [
  'accept all', 'ยอมรับทั้งหมด', 'allow all', 'accept cookies', 
  'agree', 'confirm', 'ok', 'accept'
];

function findAndClickCookieButton() {
  // 1. ค้นหาปุ่มทั้งหมดในหน้าเว็บ
  const buttons = document.querySelectorAll('button, a, div[role="button"]');

  for (const btn of buttons) {
    const text = btn.innerText.toLowerCase().trim();
    
    // 2. ตรวจสอบว่าข้อความในปุ่มตรงกับ keyword หรือไม่
    const isCookieButton = cookieKeywords.some(keyword => text.includes(keyword));
    
    // 3. ตรวจสอบความเด่นของปุ่ม (ส่วนใหญ่ปุ่ม Accept All มักจะมี class ที่ดูสำคัญ)
    if (isCookieButton && btn.offsetParent !== null) { // ตรวจสอบว่าปุ่มมองเห็นอยู่จริง
        console.log(`Fenix Extension: Clicking "${text}" button...`);
        btn.click();
        return true; // หยุดทำงานเมื่อกดแล้ว
    }
  }
}

// ตั้งเวลาให้ทำงานหลังจากโหลดหน้าเว็บ (หรือใช้ MutationObserver เพื่อดักจับ Pop-up ที่ขึ้นมาช้า)
setTimeout(findAndClickCookieButton, 2000); 

// ใช้ MutationObserver สำหรับพวก Pop-up ที่เด้งมาทีหลัง
const observer = new MutationObserver((mutations) => {
  findAndClickCookieButton();
});

observer.observe(document.body, { childList: true, subtree: true });