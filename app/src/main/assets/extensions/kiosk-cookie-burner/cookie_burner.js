(function() {
    const keywords = [
        'accept all', 'allow all', 'accept', 'ok', 'agree', 'confirm',
        'ยอมรับทั้งหมด', 'อนุญาตทั้งหมด', 'ตกลง', 'ยอมรับ'
    ];

    function findAndClick() {
        const buttons = document.querySelectorAll('button, a, div[role="button"]');
        for (const btn of buttons) {
            const text = btn.innerText.toLowerCase().trim();
            if (keywords.some(k => text.includes(k)) && btn.offsetParent !== null) {
                console.log("Kiosk Extension: Found cookie button: " + text);
                btn.click();
                // Optionally stop after first click if we only expect one banner
                // return; 
            }
        }
    }

    // Initial scan
    setTimeout(findAndClick, 1000);
    setTimeout(findAndClick, 3000);

    // Watch for dynamic banners
    const observer = new MutationObserver((mutations) => {
        findAndClick();
    });
    observer.observe(document.body, { childList: true, subtree: true });
})();
