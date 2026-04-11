(function() {
    const ACCEPT_TEXTS = [/Accept All/i, /Accept all cookies/i, /Agree/i, /Accept/i, /ยอมรับทั้งหมด/i, /ยอมรับ/i];
    const COOKIE_SELECTORS = ['.cky-btn-accept', '.osano-cm-accept-all', '#accept-all', '.accept-all-btn'];

    const findInShadows = (selector, root = document) => {
        const el = root.querySelector(selector);
        if (el) return el;
        
        const heads = root.querySelectorAll('*');
        for (const head of heads) {
            if (head.shadowRoot) {
                const found = findInShadows(selector, head.shadowRoot);
                if (found) return found;
            }
        }
        return null;
    };

    const tryClickButton = () => {
        // 1. ลองใช้ CookieYes API ถ้ามี (แม่นยำที่สุด)
        if (typeof cwryAcceptAll === 'function') {
            console.log("Fenix Extension: Clicking Accept via CookieYes API...");
            cwryAcceptAll();
            return true;
        }

        // 2. ลองหาด้วย Selectors มาตรฐาน (รวม Shadow DOM)
        for (const selector of COOKIE_SELECTORS) {
            const btn = findInShadows(selector);
            if (btn && btn.offsetParent !== null) {
                console.log("Fenix Extension: Clicking cookie button by selector: " + selector);
                btn.click();
                return true;
            }
        }

        // 3. ลองหาด้วยข้อความบนปุ่ม
        const allButtons = document.querySelectorAll('button, a, [role="button"]');
        for (const btn of allButtons) {
            const text = (btn.innerText || btn.textContent).trim();
            if (ACCEPT_TEXTS.some(regex => regex.test(text)) && btn.offsetParent !== null) {
                console.log("Fenix Extension: Clicking cookie button by text: " + text);
                btn.click();
                return true;
            }
        }
        return false;
    };

    // รันทันทีและดักจับการเปลี่ยนแปลงของหน้าจอ
    console.log("Fenix Extension: Cookie Acceptor Active...");
    setTimeout(tryClickButton, 1000); // รอสักนิดเผื่อแบนเนอร์โหลดช้า

    const observer = new MutationObserver((mutations) => {
        if (tryClickButton()) {
            // ถ้ากดได้แล้ว ให้หยุดดูแว่บหนึ่ง
            observer.disconnect();
            setTimeout(() => observer.observe(document.documentElement, { childList: true, subtree: true }), 3000);
        }
    });

    observer.observe(document.documentElement, {
        childList: true,
        subtree: true
    });
})();