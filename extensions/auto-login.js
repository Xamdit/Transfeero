// auto-login.js
(function() {
    const EMAIL_KEY = 'savedEmail';
    const PASSWORD_KEY = 'savedPassword';
    const ATTEMPTED_KEY = 'fenix_autologin_attempted';

    function fillAndSubmit(email, password) {
        // เช็คว่าใน session นี้เราเคยลอง login ไปหรือยัง เพื่อป้องกัน loop
        if (sessionStorage.getItem(ATTEMPTED_KEY)) {
            console.log("Fenix auto-login: Already attempted in this session, skipping to prevent loops.");
            return;
        }

        const emailInput = document.querySelector('input[name="email"]');
        const passwordInput = document.querySelector('input[name="password"]');
        const submitBtn = document.querySelector('button[type="submit"], input[type="submit"]');
        const form = document.querySelector('form.needs-validation');

        if (!emailInput || !passwordInput || !submitBtn) return;

        console.log("Fenix auto-login: Filling credentials...");
        emailInput.value = email;
        passwordInput.value = password;

        // Trigger validation events
        const events = ['input', 'change', 'blur'];
        events.forEach(eventName => {
            emailInput.dispatchEvent(new Event(eventName, { bubbles: true }));
            passwordInput.dispatchEvent(new Event(eventName, { bubbles: true }));
        });

        if (form) form.classList.add('was-validated');

        // บันทึกว่าลองแล้วนะ
        sessionStorage.setItem(ATTEMPTED_KEY, 'true');

        // รอสักครู่ให้ UI อัปเดตก่อนกดปุ่ม
        setTimeout(() => {
            console.log("Fenix auto-login: Clicking submit button...");
            submitBtn.click();
        }, 500);
    }

    // ดึงข้อมูลจาก storage
    if (typeof browser !== 'undefined' && browser.storage) {
        browser.storage.local.get([EMAIL_KEY, PASSWORD_KEY]).then((data) => {
            if (data[EMAIL_KEY] && data[PASSWORD_KEY]) {
                // รอให้หน้าเว็บพร้อมจริงๆ (ใช้ DOMContentLoaded หรือแอบรอสักพัก)
                if (document.readyState === 'loading') {
                    document.addEventListener('DOMContentLoaded', () => fillAndSubmit(data[EMAIL_KEY], data[PASSWORD_KEY]));
                } else {
                    fillAndSubmit(data[EMAIL_KEY], data[PASSWORD_KEY]);
                }
            }
        }).catch(err => console.error("Fenix auto-login: Storage read error:", err));
    }

    // ดักจับการ Save ข้อมูลใหม่
    document.addEventListener('submit', (e) => {
        const email = document.querySelector('input[name="email"]')?.value;
        const password = document.querySelector('input[name="password"]')?.value;
        if (email && password && typeof browser !== 'undefined' && browser.storage) {
            browser.storage.local.set({ [EMAIL_KEY]: email, [PASSWORD_KEY]: password })
                .then(() => console.log("Fenix auto-login: Credentials saved."))
                .catch(err => console.error("Fenix auto-login: Storage write error:", err));
        }
    });
})();