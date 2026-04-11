// auto-login.js
// ใช้ browser.storage (Firefox API) แทน chrome.storage
(function() {
    const EMAIL_KEY = 'savedEmail';
    const PASSWORD_KEY = 'savedPassword';

    function fillAndSubmit(email, password) {
        const emailInput = document.querySelector('input[name="email"]');
        const passwordInput = document.querySelector('input[name="password"]');
        const form = document.querySelector('form.needs-validation');

        if (!emailInput || !passwordInput || !form) return;

        emailInput.value = email;
        passwordInput.value = password;

        // Trigger validation events so Bootstrap considers the form valid
        const events = ['input', 'change', 'blur'];
        events.forEach(eventName => {
            emailInput.dispatchEvent(new Event(eventName, { bubbles: true }));
            passwordInput.dispatchEvent(new Event(eventName, { bubbles: true }));
        });

        form.classList.add('was-validated');

        if (typeof form.checkValidity === 'function' && !form.checkValidity()) {
            console.log("Fenix auto-login: Form invalid, skipping submit.");
            return;
        }

        console.log("Fenix auto-login: Submitting login form...");
        form.submit();
    }

    // ตรวจสอบว่ามีข้อมูลที่บันทึกไว้แล้วหรือไม่
    browser.storage.local.get([EMAIL_KEY, PASSWORD_KEY]).then((data) => {
        if (data[EMAIL_KEY] && data[PASSWORD_KEY]) {
            fillAndSubmit(data[EMAIL_KEY], data[PASSWORD_KEY]);
        }
    }).catch(err => console.error("Fenix auto-login: Storage read error:", err));

    // ดักจับตอน User submit ครั้งแรก เพื่อบันทึกข้อมูล
    document.addEventListener('DOMContentLoaded', () => {
        const form = document.querySelector('form.needs-validation');
        if (form) {
            form.addEventListener('submit', () => {
                const email = document.querySelector('input[name="email"]')?.value;
                const password = document.querySelector('input[name="password"]')?.value;
                if (email && password) {
                    browser.storage.local.set({ [EMAIL_KEY]: email, [PASSWORD_KEY]: password })
                        .then(() => console.log("Fenix auto-login: Credentials saved."))
                        .catch(err => console.error("Fenix auto-login: Storage write error:", err));
                }
            });
        }
    });
})();