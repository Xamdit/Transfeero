(function() {
    const findAndClickCookie = () => {
        // 1. ตรวจสอบว่ามี Cookie Consent Container หรือไม่
        const container = document.querySelector('.cky-consent-container');
        
        // 2. ถ้าเจอ container หรือพบข้อความ "We value your privacy"
        if (container || document.body.innerText.includes("We value your privacy")) {
            // ลองหาปุ่มที่มี class cky-btn-accept ก่อน (อ้างอิงจาก cookie.html)
            const ckyAcceptBtn = document.querySelector('.cky-btn-accept');
            if (ckyAcceptBtn) {
                console.log("Fenix Extension: Clicking .cky-btn-accept...");
                ckyAcceptBtn.click();
                return;
            }

            // ถ้าไม่เจอด้วย class ให้ลองหาด้วย text "Accept All"
            const buttons = document.querySelectorAll('button, a');
            for (let btn of buttons) {
                const btnText = btn.innerText.trim();
                if (/^Accept All$/i.test(btnText)) {
                    console.log("Fenix Extension: Clicking button with text 'Accept All'...");
                    btn.click();
                    break; 
                }
            }
        }
    };

    // รันทันทีที่โหลด
    findAndClickCookie();

    const observer = new MutationObserver(() => {
        findAndClickCookie();
    });

    observer.observe(document.documentElement, {
        childList: true,
        subtree: true
    });
})();