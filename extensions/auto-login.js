// ตรวจสอบว่ามีข้อมูลใน Storage หรือไม่ตอนโหลดหน้า
chrome.storage.local.get(['savedEmail', 'savedPassword'], (data) => {
    if (data.savedEmail && data.savedPassword) {
        document.querySelector('input[name="email"]').value = data.savedEmail;
        document.querySelector('input[name="password"]').value = data.savedPassword;
        
        // สั่ง Login อัตโนมัติ
        document.querySelector('form.needs-validation').submit();
    }
});

// ดักจับตอน User พิมพ์ Login ครั้งแรกเพื่อบันทึกข้อมูล
document.querySelector('form.needs-validation').addEventListener('submit', () => {
    const email = document.querySelector('input[name="email"]').value;
    const password = document.querySelector('input[name="password"]').value;
    
    chrome.storage.local.set({ savedEmail: email, savedPassword: password });
});