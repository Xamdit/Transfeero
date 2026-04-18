(function() {
    // Hide common toolbar/header elements using CSS injection
    const style = document.createElement('style');
    style.textContent = `
        header, .toolbar, .navbar, #toolbar, [class*="toolbar"], [class*="nav-bar"], [class*="navbar"] {
            display: none !important;
        }
    `;
    (document.head || document.documentElement).appendChild(style);

    // Signal to the native app that a page has loaded and we want to hide UI
    // Note: This requires a corresponding listener in the Kotlin code to have any effect on the browser UI.
    try {
        if (typeof browser !== 'undefined') {
            browser.runtime.sendMessage({ action: "hideToolbar" });
        }
    } catch (e) {
        // Silently fail if messaging is not available or handled
    }
})();
