import os
import re

objects = ["Activation", "Addons", "Addresses", "AndroidAutofill", "AppTheme", "Autoplay", "Awesomebar", "BookmarksManagement", "BrowserSearch", "Collections", "ContextMenu", "ContextualMenu", "CookieBanners", "CreditCards", "CustomizeHome", "EngineTab", "ErrorPage", "Events", "FirstSession", "GleanBuildInfo", "History", "HomeMenu", "HomeScreen", "LoginDialog", "Logins", "MediaNotification", "MediaState", "Messaging", "Metrics", "Onboarding", "PerfAwesomebar", "PerfStartup", "Pings", "Pocket", "Preferences", "ProgressiveWebApp", "ReaderMode", "RecentBookmarks", "RecentSearches", "RecentSyncedTabs", "RecentTabs", "RecentlyClosedTabs", "RecentlyVisitedHomepage", "ReviewPrompt", "SearchDefaultEngine", "SearchShortcuts", "SearchWidget", "SitePermissions", "StartOnHome", "StorageStats", "SyncAccount", "SyncAuth", "SyncedTabs", "Tab", "Tabs", "TabsTray", "ToolbarSettings", "TopSites", "TrackingProtection", "UnifiedSearch", "VoiceSearch", "Wallpapers", "NimbusMessagingController", "TabCounterBuilder", "ReasonCodes", "Stats"]

mapping = {obj: set() for obj in objects}
extra_mapping = {obj: set() for obj in objects}

for root, dirs, files in os.walk('app/src/main/java'):
    for fname in files:
        if fname.endswith(('.kt', '.java')):
            fpath = os.path.join(root, fname)
            try:
                with open(fpath, 'r') as f:
                    content = f.read()
                    for obj in objects:
                        # Match [Object].[symbol]
                        props = re.findall(obj + r'\.([a-z][a-zA-Z0-9_]*)', content)
                        for p in props: mapping[obj].add(p)
                        
                        # Match [Object].[Extra]
                        extras = re.findall(obj + r'\.([A-Z][a-zA-Z0-9_]*Extra)', content)
                        for ex in extras: extra_mapping[obj].add(ex)
                        
                        # Special check for imports like Events.marketingNotificationAllowed
                        imp_matches = re.findall(r'import .*\.GleanMetrics\.' + obj + r'\.([a-zA-Z0-9_]*)', content)
                        for m in imp_matches:
                            if m[0].islower(): mapping[obj].add(m)
                            elif m.endswith('Extra'): extra_mapping[obj].add(m)
            except: pass

for obj in objects:
    if mapping[obj] or extra_mapping[obj]:
        print(f"MAPPING['{obj}'] = {sorted(list(mapping[obj]))}")
        print(f"EXTRAS['{obj}'] = {sorted(list(extra_mapping[obj]))}")
