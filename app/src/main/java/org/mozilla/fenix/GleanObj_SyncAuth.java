package org.mozilla.fenix;
import java.util.Calendar; import mozilla.components.service.glean.BuildInfo;
public class GleanObj_SyncAuth {
    public final GleanStubs.MapStub accessPoint = new GleanStubs.MapStub();
    public final GleanStubs.MapStub kills = new GleanStubs.MapStub();
    public final GleanStubs.MetricStub record = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub set = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub add = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub activation = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub activationId = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub appBytes = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub appOpened = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub autoCloseSeen = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub available = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub browserMenuAction = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub browserToolbarHomeTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub cacheBytes = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub contile = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub copyUrlTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub dataDirBytes = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub defaultBrowserChanged = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub defaultBrowserNotifShown = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub defaultBrowserNotifTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub density = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub densityDpi = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub enteredUrl = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub foregroundMetrics = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub formDataFailure = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub fullscreen = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub hasOpenTabs = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub hasRecentPwas = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub heightPixels = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub identifier = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub inactiveTabsCount = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub jumpBackIn = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub killBackgroundAge = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub killForegroundAge = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub kills = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub marketingNotificationAllowed = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub mostVisitedSites = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub normalAndPrivateUriCount = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub openedLink = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub otherExternal = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub paired = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub performedSearch = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub pictureInPicture = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub pocket = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub preferenceToggled = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub queryStatsDuration = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub reEngagementNotifShown = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub reEngagementNotifTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub recentlyClosedTabsOpened = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub recentlySaved = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub recentlyUsedPwaCount = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub recentlyVisited = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub recovered = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub renamed = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub report = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub saveToPdfFailure = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub saveToPdfTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub searchBarTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub searchCount = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub settingsItemClicked = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub shareToApp = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub shown = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub signIn = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub signOut = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub signUp = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub sponsoredPocket = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub startReasonActivityError = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub startReasonProcessError = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub syncCardImpression = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub syncCloseClicked = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub syncSignInClicked = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub syncSkipClicked = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub syncedTabOpened = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub tabSelectOpened = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub tabViewChanged = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub toolbarMenuVisible = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub welcomeCardImpression = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub welcomeCloseClicked = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub welcomeGetStartedClicked = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub whatsNewTapped = new GleanStubs.MetricStub();
    public final GleanStubs.MetricStub widthPixels = new GleanStubs.MetricStub();
    public static class AppOpenedExtra { public AppOpenedExtra(Object... a) {} public void record() {} }
    public static class BookmarkSelectedTabsExtra { public BookmarkSelectedTabsExtra(Object... a) {} public void record() {} }
    public static class BrowserMenuActionExtra { public BrowserMenuActionExtra(Object... a) {} public void record() {} }
    public static class ChangedPositionExtra { public ChangedPositionExtra(Object... a) {} public void record() {} }
    public static class CloseSelectedTabsExtra { public CloseSelectedTabsExtra(Object... a) {} public void record() {} }
    public static class ClosedExistingTabExtra { public ClosedExistingTabExtra(Object... a) {} public void record() {} }
    public static class ContileClickExtra { public ContileClickExtra(Object... a) {} public void record() {} }
    public static class ContileImpressionExtra { public ContileImpressionExtra(Object... a) {} public void record() {} }
    public static class DarkThemeSelectedExtra { public DarkThemeSelectedExtra(Object... a) {} public void record() {} }
    public static class EngineSelectedExtra { public EngineSelectedExtra(Object... a) {} public void record() {} }
    public static class EnterMultiselectModeExtra { public EnterMultiselectModeExtra(Object... a) {} public void record() {} }
    public static class EnteredUrlExtra { public EnteredUrlExtra(Object... a) {} public void record() {} }
    public static class EtpSettingChangedExtra { public EtpSettingChangedExtra(Object... a) {} public void record() {} }
    public static class ForegroundMetricsExtra { public ForegroundMetricsExtra(Object... a) {} public void record() {} }
    public static class HasInactiveTabsExtra { public HasInactiveTabsExtra(Object... a) {} public void record() {} }
    public static class HomeRecsCategoryClickedExtra { public HomeRecsCategoryClickedExtra(Object... a) {} public void record() {} }
    public static class HomeRecsSpocClickedExtra { public HomeRecsSpocClickedExtra(Object... a) {} public void record() {} }
    public static class HomeRecsSpocShownExtra { public HomeRecsSpocShownExtra(Object... a) {} public void record() {} }
    public static class HomeRecsStoryClickedExtra { public HomeRecsStoryClickedExtra(Object... a) {} public void record() {} }
    public static class ItemTappedExtra { public ItemTappedExtra(Object... a) {} public void record() {} }
    public static class LearnMoreLinkClickExtra { public LearnMoreLinkClickExtra(Object... a) {} public void record() {} }
    public static class LongPressExtra { public LongPressExtra(Object... a) {} public void record() {} }
    public static class MalformedExtra { public MalformedExtra(Object... a) {} public void record() {} }
    public static class MessageClickedExtra { public MessageClickedExtra(Object... a) {} public void record() {} }
    public static class MessageDismissedExtra { public MessageDismissedExtra(Object... a) {} public void record() {} }
    public static class MessageExpiredExtra { public MessageExpiredExtra(Object... a) {} public void record() {} }
    public static class MessageShownExtra { public MessageShownExtra(Object... a) {} public void record() {} }
    public static class MetricsExtra { public MetricsExtra(Object... a) {} public void record() {} }
    public static class OnboardingClosedExtra { public OnboardingClosedExtra(Object... a) {} public void record() {} }
    public static class OpenAddonInToolbarMenuExtra { public OpenAddonInToolbarMenuExtra(Object... a) {} public void record() {} }
    public static class OpenedExistingTabExtra { public OpenedExistingTabExtra(Object... a) {} public void record() {} }
    public static class OpenedItemExtra { public OpenedItemExtra(Object... a) {} public void record() {} }
    public static class OpenedLinkExtra { public OpenedLinkExtra(Object... a) {} public void record() {} }
    public static class PerformedSearchExtra { public PerformedSearchExtra(Object... a) {} public void record() {} }
    public static class PermissionsAllowedExtra { public PermissionsAllowedExtra(Object... a) {} public void record() {} }
    public static class PermissionsDeniedExtra { public PermissionsDeniedExtra(Object... a) {} public void record() {} }
    public static class PrefToggledThemePickerExtra { public PrefToggledThemePickerExtra(Object... a) {} public void record() {} }
    public static class PrefToggledToolbarPositionExtra { public PrefToggledToolbarPositionExtra(Object... a) {} public void record() {} }
    public static class PrefToggledTrackingProtExtra { public PrefToggledTrackingProtExtra(Object... a) {} public void record() {} }
    public static class PreferenceToggledExtra { public PreferenceToggledExtra(Object... a) {} public void record() {} }
    public static class PromptAttemptExtra { public PromptAttemptExtra(Object... a) {} public void record() {} }
    public static class PromptShownExtra { public PromptShownExtra(Object... a) {} public void record() {} }
    public static class RecentSearchesTappedExtra { public RecentSearchesTappedExtra(Object... a) {} public void record() {} }
    public static class SaveButtonExtra { public SaveButtonExtra(Object... a) {} public void record() {} }
    public static class SaveLoginsSettingChangedExtra { public SaveLoginsSettingChangedExtra(Object... a) {} public void record() {} }
    public static class SavedExtra { public SavedExtra(Object... a) {} public void record() {} }
    public static class SearchBarTappedExtra { public SearchBarTappedExtra(Object... a) {} public void record() {} }
    public static class SelectedExtra { public SelectedExtra(Object... a) {} public void record() {} }
    public static class SelectedTabsToCollectionExtra { public SelectedTabsToCollectionExtra(Object... a) {} public void record() {} }
    public static class SettingChangedExtra { public SettingChangedExtra(Object... a) {} public void record() {} }
    public static class ShareSelectedTabsExtra { public ShareSelectedTabsExtra(Object... a) {} public void record() {} }
    public static class ShareToAppExtra { public ShareToAppExtra(Object... a) {} public void record() {} }
    public static class SwipeCarouselExtra { public SwipeCarouselExtra(Object... a) {} public void record() {} }
    public static class TabViewChangedExtra { public TabViewChangedExtra(Object... a) {} public void record() {} }
    public static class TabsAddedExtra { public TabsAddedExtra(Object... a) {} public void record() {} }
    public static class VisitedErrorExtra { public VisitedErrorExtra(Object... a) {} public void record() {} }
    public static class WallpaperSelectedExtra { public WallpaperSelectedExtra(Object... a) {} public void record() {} }
    public static class getBooleanExtra { public getBooleanExtra(Object... a) {} public void record() {} }
    public static class getIntExtra { public getIntExtra(Object... a) {} public void record() {} }
    public static class getShareToAppSafeExtra { public getShareToAppSafeExtra(Object... a) {} public void record() {} }
    public static class getStringArrayListExtra { public getStringArrayListExtra(Object... a) {} public void record() {} }
    public static class getStringExtra { public getStringExtra(Object... a) {} public void record() {} }
    public static class hasExtra { public hasExtra(Object... a) {} public void record() {} }
    public static class putExtra { public putExtra(Object... a) {} public void record() {} }
    public static class removeExtra { public removeExtra(Object... a) {} public void record() {} }
}
