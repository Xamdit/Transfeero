// accept-ride.js
// Extension อัตโนมัติสำหรับกดปุ่มรับงาน (Accept Ride) บน control.transfeero.com
(function() {
    const ACCEPT_KEYWORDS = [
        'accept ride', 'accept job', 'accept booking',
        'รับงาน', 'รับการจอง',
        'confirm ride', 'confirm booking',
    ];

    function isVisible(el) {
        if (!el) return false;
        const style = window.getComputedStyle(el);
        return style.display !== 'none' && style.visibility !== 'hidden' && el.offsetParent !== null;
    }

    function tryAccept() {
        const buttons = document.querySelectorAll('button, a, [role="button"], input[type="button"], input[type="submit"]');
        for (const btn of buttons) {
            const text = (btn.innerText || btn.value || '').toLowerCase().trim();
            if (ACCEPT_KEYWORDS.some(kw => text.includes(kw)) && isVisible(btn)) {
                console.log("Fenix accept-ride: Clicking '" + text + "'...");
                btn.click();
                return true;
            }
        }
        return false;
    }

    // ฟังก์ชันสำหรับเช็ค URL และจัดการ Refresh
    function checkURLAndRefresh() {
        if (!window.location.href.includes('/new_rides')) return;

        // ลองหาปุ่มทันที
        const found = tryAccept();
        
        if (!found) {
            console.log("Fenix accept-ride: No ride found on /new_rides. Refreshing in 10s...");
            setTimeout(() => {
                // เช็คอีกครั้งเผื่อปุ่มเพิ่งขึ้นมาใน 10 วินาทีนี้
                if (!tryAccept()) {
                    location.reload();
                }
            }, 10000);
        } else {
            console.log("Fenix accept-ride: Ride found and clicked!");
        }
    }

    // เริ่มการทำงาน
    checkURLAndRefresh();

    // คอยดู DOM สำหรับ Dialog/Popup ที่ขึ้นมาทีหลัง (ใช้ MutationObserver อย่างเดิม)
    const observer = new MutationObserver(() => tryAccept());
    observer.observe(document.documentElement, { childList: true, subtree: true });
})();
