(function() {
    const hideChat = () => {
        // ดักจับ Intercom Launcher หรือ element ที่มี class intercom-launcher
        const chatLaunchers = document.querySelectorAll('.intercom-lightweight-app-launcher, .intercom-launcher');
        if (chatLaunchers.length > 0) {
            chatLaunchers.forEach(el => {
                if (el.style.display !== 'none') {
                    console.log("Fenix Extension: Hiding chat launcher...");
                    el.style.setProperty('display', 'none', 'important');
                }
            });
        }
    };

    // รันทันที
    hideChat();

    // ดักจับเผื่อเด้งมาทีหลัง
    const observer = new MutationObserver(() => {
        hideChat();
    });

    observer.observe(document.documentElement, {
        childList: true,
        subtree: true
    });
})();
