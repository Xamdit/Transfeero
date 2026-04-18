(function() {
    'use strict';

    console.log('TransfeeroLog: ride.js extension loaded');

    function initAutomation() {
        const url = window.location.href;
        
        // Match http://control.transfeero.com/new_rides or ends with new_rides
        const isTargetPage = url === 'http://control.transfeero.com/new_rides' || url.endsWith('new_rides');
        
        if (!isTargetPage) {
            console.log('TransfeeroLog: Not on target page, skipping automation');
            return;
        }

        console.log('TransfeeroLog: Automation active for new_rides');

        const intervalId = setInterval(() => {
            // 1. Check for "No rides" alert
            const emptyState = document.querySelector('.no_reservations.rides-empty-state');
            if (emptyState) {
                console.log('TransfeeroLog: No rides available. Will refresh in 60s.');
                clearInterval(intervalId);
                setTimeout(() => {
                    console.log('TransfeeroLog: Refreshing page...');
                    location.reload();
                }, 60000);
                return;
            }

            // 2. Look for container-fluid and the Accept button
            const container = document.querySelector('.container-fluid');
            if (container) {
                const dropdown = container.querySelector('.js-example-basic-vehicle');
                const acceptButton = container.querySelector('button.ame_action[data-action="accept"]');

                // If we found a ride (dropdown and accept button exist)
                if (dropdown && acceptButton) {
                    console.log('TransfeeroLog: Ride found! Processing...');
                    
                    // Stop checking once we find the elements
                    clearInterval(intervalId);

                    // Select the bottom-most object in the dropdown
                    if (dropdown.options && dropdown.options.length > 0) {
                        const lastIndex = dropdown.options.length - 1;
                        const lastValue = dropdown.options[lastIndex].value;
                        
                        console.log('TransfeeroLog: Selecting vehicle:', dropdown.options[lastIndex].text);

                        // If Select2/jQuery is available (it is in ride.html), use it to ensure the UI updates
                        if (window.jQuery && window.jQuery.fn.select2) {
                            const $dropdown = window.jQuery(dropdown);
                            $dropdown.val(lastValue).trigger('change');
                        } else {
                            dropdown.selectedIndex = lastIndex;
                            dropdown.dispatchEvent(new Event('change', { bubbles: true }));
                        }

                        // Short delay to ensure selection is processed before clicking Accept
                        setTimeout(() => {
                            console.log('TransfeeroLog: Clicking Accept button');
                            acceptButton.click();
                        }, 1000);
                    } else {
                        console.log('TransfeeroLog: Dropdown found but has no options.');
                    }
                }
            }
        }, 2000); // Check every 2 seconds
    }

    // Run on load
    if (document.readyState === 'complete') {
        initAutomation();
    } else {
        window.addEventListener('load', initAutomation);
    }
})();
