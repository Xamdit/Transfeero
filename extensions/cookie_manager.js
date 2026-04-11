// cookie_manager.js
(function() {
    console.log("Fenix Extension: Cookie Manager Loaded.");
    
    const targetKeywords = ['accept all', 'ยอมรับทั้งหมด', 'allow all', 'accept', 'ok'];
    
    function scanAndClick() {
        const elements = document.querySelectorAll('button, a, [role="button"]');
        for (let el of elements) {
            const text = el.innerText.toLowerCase().trim();
            if (targetKeywords.some(k => text.includes(k)) && el.offsetParent !== null) {
                console.log("Fenix found button: " + text);
                el.click();
                break; 
            }
        }
    }

    // ทำงานทันที และดักจับกรณี Pop-up ขึ้นมาภายหลัง
    scanAndClick();
    const observer = new MutationObserver(scanAndClick);
    observer.observe(document.body, { childList: true, subtree: true });
})();