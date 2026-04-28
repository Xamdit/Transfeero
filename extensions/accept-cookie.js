(function() {
    const ACCEPT_TEXTS = [/Accept All/i, /Accept all cookies/i, /Agree/i, /Accept/i, /ยอมรับทั้งหมด/i, /ยอมรับ/i, /Allow all/i];
    const COOKIE_SELECTORS = [
        '.cky-btn-accept', 
        'button[data-cky-tag="accept-button"]', 
        '#cky-btn-accept',
        '.osano-cm-accept-all', 
        '#accept-all', 
        '.accept-all-btn',
        '[aria-label="Accept All"]',
        '[aria-label="ยอมรับทั้งหมด"]'
    ];

    const findInShadows = (selector, root = document) => {
        // Direct search in current root
        const el = root.querySelector(selector);
        if (el) return el;
        
        // Search all elements for shadow roots
        const heads = root.querySelectorAll('*');
        for (const head of heads) {
            if (head.shadowRoot) {
                const found = findInShadows(selector, head.shadowRoot);
                if (found) return found;
            }
        }
        return null;
    };

    const findAllButtonsInShadows = (root = document, results = []) => {
        const buttons = root.querySelectorAll('button, a, [role="button"]');
        results.push(...Array.from(buttons));
        
        const allElements = root.querySelectorAll('*');
        for (const el of allElements) {
            if (el.shadowRoot) {
                findAllButtonsInShadows(el.shadowRoot, results);
            }
        }
        return results;
    };

    const tryClickButton = () => {
        console.log("Fenix Extension: Attempting to find cookie banner...");

        // 1. ลองใช้ CookieYes API ถ้ามี
        if (typeof cwryAcceptAll === 'function') {
            console.log("Fenix Extension: Detected CookieYes API. Clicking...");
            cwryAcceptAll();
            return true;
        }

        // 2. ลองหาด้วย Selectors มาตรฐาน (รวม Shadow DOM)
        for (const selector of COOKIE_SELECTORS) {
            const btn = findInShadows(selector);
            if (btn && btn.offsetParent !== null) {
                console.log("Fenix Extension: Found button by selector: " + selector);
                btn.click();
                return true;
            }
        }

        // 3. ลองหาด้วยข้อความบนปุ่ม (รวม Shadow DOM)
        const allButtons = findAllButtonsInShadows();
        for (const btn of allButtons) {
            const text = (btn.innerText || btn.textContent || "").trim();
            if (ACCEPT_TEXTS.some(regex => regex.test(text)) && (btn.offsetParent !== null || btn.getClientRects().length > 0)) {
                console.log("Fenix Extension: Found button by text match: " + text);
                btn.click();
                return true;
            }
        }

        return false;
    };

    // รันทันทีและดักจับการเปลี่ยนแปลงของหน้าจอ
    console.log("Fenix Extension: Cookie Acceptor (Shadow DOM optimized) Active...");
    
    // ทยอยรันหลายๆ ช่วงเวลาเผื่อการโหลดที่ล่าช้า
    [500, 1500, 3000, 5000].forEach(delay => setTimeout(tryClickButton, delay));

    const observer = new MutationObserver((mutations) => {
        if (tryClickButton()) {
            console.log("Fenix Extension: Successfully clicked. Disconnecting observer temporarily.");
            observer.disconnect();
            setTimeout(() => observer.observe(document.documentElement, { childList: true, subtree: true }), 5000);
        }
    });

    observer.observe(document.documentElement, {
        childList: true,
        subtree: true
    });
})();