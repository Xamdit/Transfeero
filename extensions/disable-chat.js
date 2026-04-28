(function() {
    const hideChat = () => {
        // ดักจับ Intercom Launcher หรือ Messenger Frame
        const chatElements = document.querySelectorAll(
            '.intercom-lightweight-app-launcher, .intercom-launcher, .intercom-messenger-frame, .intercom-app, #intercom-container'
        );
        if (chatElements.length > 0) {
            chatElements.forEach(el => {
                if (el.style.display !== 'none') {
                    console.log("Fenix Extension: Hiding chat element...");
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
