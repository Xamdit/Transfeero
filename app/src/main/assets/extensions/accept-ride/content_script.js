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

    // ลองคลิกทันที และหลังจาก 1 / 2 วินาที
    tryAccept();
    setTimeout(tryAccept, 1000);
    setTimeout(tryAccept, 2000);

    // คอยดู DOM สำหรับ Dialog/Popup ที่ขึ้นมาทีหลัง
    const observer = new MutationObserver(() => tryAccept());
    observer.observe(document.documentElement, { childList: true, subtree: true });
})();
