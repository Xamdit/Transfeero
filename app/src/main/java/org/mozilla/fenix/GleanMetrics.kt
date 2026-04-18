package org.mozilla.fenix

import java.util.UUID
import java.util.Calendar

/**
 * A comprehensive mock of Glean metrics to satisfy the compiler when the 
 * Glean Gradle plugin is not available.
 */
@Suppress("LargeClass", "TooManyFunctions", "LongParameterList", "MagicNumber")
object GleanMetrics {
    class MetricStub {
        fun record(extras: Any? = null) {}
        fun add(amount: Int = 1) {}
        fun set(value: Any?) {}
        fun submit(value: Any? = null) {}
        fun accumulate(samples: Long) {}
        fun accumulateSamples(samples: List<Long>) {}
        fun testGetValue(): Any? = null
        fun generateAndSet(): UUID = UUID.randomUUID()
        operator fun get(key: Any?): MetricStub = this
    }

    class MapStub {
        operator fun get(key: Any?): MetricStub = MetricStub()
    }

    // Top-level objects
    object Activation { val activation = MetricStub(); val identifier = MetricStub(); val submit = MetricStub(); val activationId = MetricStub() }
    object Addresses { val formDetected = MetricStub(); val autofilled = MetricStub(); val autofillPromptShown = MetricStub() }
    object Addons { val openAddonInToolbarMenu = MetricStub(); class OpenAddonInToolbarMenuExtra(val name: Any? = null) }
    object AndroidAutofill { val formDetected = MetricStub(); val autofilled = MetricStub(); val autofillPromptShown = MetricStub(); val autofillPromptExpanded = MetricStub(); val autofillPromptDismissed = MetricStub() }
    object AppTheme { val themeChanged = MetricStub() }
    object Awesomebar { val copyTapped = MetricStub(); val searchTapped = MetricStub(); val selectAllTapped = MetricStub(); val shareTapped = MetricStub() }
    object BrowserSearch { class SearchExtra(val autocomplete: Any? = null) }
    object Collections {
        val renamed = MetricStub(); val saved = MetricStub(); val tabsAdded = MetricStub(); val tabSelectOpened = MetricStub()
        class SavedExtra(val count: Any? = null, val source: Any? = null)
        class TabsAddedExtra(val count: Any? = null, val source: Any? = null)
    }
    object ContextMenu { val itemClicked = MetricStub() }
    object ContextualMenu { val itemClicked = MetricStub() }
    object CookieBanners { val bannerHandled = MetricStub() }
    object CreditCards { val formDetected = MetricStub(); val autofilled = MetricStub(); val autofillPromptShown = MetricStub() }
    object CustomizeHome {
        val openingScreen = MetricStub(); val jumpBackIn = MetricStub(); val recentlySaved = MetricStub(); val mostVisitedSites = MetricStub(); val recentlyVisited = MetricStub(); val pocket = MetricStub(); val sponsoredPocket = MetricStub(); val contile = MetricStub()
    }
    object ErrorPage { val visitedError = MetricStub(); class VisitedErrorExtra(val source: Any? = null, val reason: Any? = null) }
    object Events {
        val appOpenedResourceId = MetricStub(); val appOpened = MetricStub(); val openedLink = MetricStub(); val marketingNotificationAllowed = MetricStub()
        class AppOpenedExtra(val source: Any? = null); class OpenedLinkExtra(val source: Any? = null, val reason: Any? = null)
    }
    object FirstSession {
        val campaign = MetricStub(); val adgroup = MetricStub(); val creative = MetricStub(); val network = MetricStub(); val timestamp = MetricStub(); val firstSession = MetricStub(); val submit = MetricStub()
    }
    object GleanBuildInfo {
        val buildInfo: mozilla.components.service.glean.BuildInfo = mozilla.components.service.glean.BuildInfo(
            "1", "1.0", Calendar.getInstance()
        )
        val applicationOnCreate: Any? = null
    }
    object History { class HistoryItemExtra(val isRemote: Any? = null, val timeGroup: Any? = null, val isPrivate: Any? = null) }
    object HomeMenu { val itemClicked = MetricStub() }
    object LoginDialog { val loginPromptShown = MetricStub(); val loginPromptDismissed = MetricStub() }
    object Logins { val savePromptCreate = MetricStub(); val savePromptUpdate = MetricStub(); val savePromptShown = MetricStub(); val requestMatchingLogins = MetricStub(); val requestNoMatchingLogins = MetricStub() }
    object MediaNotification { val play = MetricStub(); val pause = MetricStub(); val stop = MetricStub() }
    object MediaState { val pictureInPicture = MetricStub(); val fullscreen = MetricStub() }
    object Messaging { val malformed = MetricStub(); class MalformedExtra(val source: Any? = null, val reason: Any? = null) }
    object Metrics {
        val defaultBrowser = MetricStub(); val defaultMozBrowser = MetricStub(); val distributionId = MetricStub(); val mozillaProducts = MetricStub()
        val adjustCampaign = MetricStub(); val adjustAdGroup = MetricStub(); val adjustCreative = MetricStub(); val adjustNetwork = MetricStub()
        val searchWidgetInstalled = MetricStub(); val tabsOpenCount = MetricStub(); val hasTopSites = MetricStub(); val topSitesCount = MetricStub()
        val hasDesktopBookmarks = MetricStub(); val desktopBookmarksCount = MetricStub(); val hasMobileBookmarks = MetricStub(); val mobileBookmarksCount = MetricStub()
        val toolbarPosition = MetricStub(); val tabViewSetting = MetricStub(); val closeTabSetting = MetricStub(); val installSource = MetricStub()
        val defaultWallpaper = MetricStub(); val notificationsAllowed = MetricStub(); val marketingNotificationAllowed = MetricStub()
        val supported = MetricStub(); val enabled = MetricStub(); val searchSuggestionsEnabled = MetricStub(); val remoteDebuggingEnabled = MetricStub()
        val studiesEnabled = MetricStub(); val telemetryEnabled = MetricStub(); val browsingHistorySuggestion = MetricStub(); val bookmarksSuggestion = MetricStub()
        val clipboardSuggestionsEnabled = MetricStub(); val searchShortcutsEnabled = MetricStub(); val voiceSearchEnabled = MetricStub(); val openLinksInAppEnabled = MetricStub()
        val signedInSync = MetricStub(); val syncItems = MetricStub(); val toolbarPositionSetting = MetricStub(); val enhancedTrackingProtection = MetricStub()
        val etpCustomCookiesSelection = MetricStub(); val accessibilityServices = MetricStub(); val userTheme = MetricStub(); val inactiveTabsEnabled = MetricStub()
        val openInNewTabs = MetricStub(); val openInPrivateTabs = MetricStub(); val hasOpenTabs = MetricStub(); val hasInstalledAddons = MetricStub()
        val installedAddons = MetricStub(); val hasEnabledAddons = MetricStub(); val enabledAddons = MetricStub(); val contextId = MetricStub()
        val coldMainAppToFirstFrame = MetricStub(); val coldViewAppToFirstFrame = MetricStub(); val coldUnknwnAppToFirstFrame = MetricStub()
        val startupType = MetricStub(); val queryStatsDuration = MetricStub(); val killForegroundAge = MetricStub(); val killBackgroundAge = MetricStub()
    }
    object Onboarding {
        val openingScreen = MetricStub(); val jumpBackIn = MetricStub(); val recentlySaved = MetricStub(); val mostVisitedSites = MetricStub(); val recentlyVisited = MetricStub(); val pocket = MetricStub(); val sponsoredPocket = MetricStub(); val contile = MetricStub()
    }
    object PerfAwesomebar { val medianLifetime = MetricStub() }
    object PerfStartup { val applicationOnCreate = MetricStub() }
    object Pings { operator fun get(key: String): MetricStub = MetricStub(); val submit = MetricStub() }
    object Pocket {
        val homeRecsSpocShown = MetricStub(); val spocShim = MetricStub(); val homeRecsShown = MetricStub(); val homeRecsCategoryClicked = MetricStub()
        val homeRecsSpocClicked = MetricStub(); val homeRecsTopicClicked = MetricStub(); val homeRecsSaveClicked = MetricStub(); val homeRecsShareClicked = MetricStub()
        val homeRecsDismissClicked = MetricStub(); val homeRecsOpenClicked = MetricStub()
        class HomeRecsSpocShownExtra(val spocReasonCodes: Any? = null)
    }
    object Preferences { val testGetValue = MetricStub(); val studiesEnabled = MetricStub(); val telemetryEnabled = MetricStub() }
    object ProgressiveWebApp { val installTap = MetricStub(); val opened = MetricStub() }
    object ReaderMode { val available = MetricStub() }
    object RecentSyncedTabs { val recentSyncedTabTimeToLoad = MetricStub(); val recentSyncedTabShown = MetricStub(); val recentSyncedTabOpened = MetricStub() }
    object ReviewPrompt { val promptAttempt = MetricStub(); class PromptAttemptExtra(val promptWasDisplayed: Any? = null, val localDatetime: Any? = null, val numberOfAppLaunches: Any? = null) }
    object SearchDefaultEngine { class SearchDefaultEngineExtra(val defaultEngine: Any? = null, val name: Any? = null, val searchUrl: Any? = null) }
    object SearchShortcuts { val homescreenTap = MetricStub(); val installTap = MetricStub() }
    object SearchWidget { val searchWidgetInstalled = MetricStub() }
    object SitePermissions { val promptShown = MetricStub(); val promptDismissed = MetricStub() }
    object StartOnHome { val enabled = MetricStub() }
    object SyncAuth { val signIn = MetricStub(); val signUp = MetricStub(); val paired = MetricStub(); val recovered = MetricStub(); val otherExternal = MetricStub(); val signOut = MetricStub() }
    object SyncedTabs { val syncedTabClicked = MetricStub() }
    object Tab { val closed = MetricStub() }
    object Tabs { val tabCount = MetricStub() }
    object TopSites { val mostVisitedSites = MetricStub(); val contile = MetricStub() }
    object TrackingProtection { val enabled = MetricStub(); val supported = MetricStub() }
    object UnifiedSearch { val searchItemSelected = MetricStub(); val searchDisplayed = MetricStub(); val confirmSuccessful = MetricStub(); val confirmCancelled = MetricStub(); val unlockSuccessful = MetricStub(); val unlockCancelled = MetricStub(); val syncedTabsSuggestionClicked = MetricStub(); val bookmarkSuggestionClicked = MetricStub(); val clipboardSuggestionClicked = MetricStub(); val historySuggestionClicked = MetricStub(); val searchActionClicked = MetricStub(); val searchSuggestionClicked = MetricStub(); val openedTabSuggestionClicked = MetricStub(); val searchTermSuggestionClicked = MetricStub() }
    object Wallpapers { class WallpaperExtra(val isSelected: Any? = null, val collectionName: Any? = null) }
}
