/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.utils

import android.content.Context
import mozilla.components.support.base.log.logger.Logger
import org.json.JSONObject
import java.io.InputStream

/**
 * Singleton to manage application configuration from assets/app-config.json.
 */
object AppConfig {
    private val logger = Logger("AppConfig")
    private const val CONFIG_FILE = "app-config.json"

    var toolbarVisible: Boolean = true
        private set

    var homeUrl: String? = null
        private set

    var appName: String = "Fenix"
        private set

    var contextMenuEnabled: Boolean = true
        private set

    var telemetryEnabled: Boolean = true
        private set

    var remoteSettingsEnabled: Boolean = true
        private set

    /**
     * Loads the configuration from the assets folder.
     */
    fun load(context: Context) {
        try {
            val inputStream: InputStream = context.assets.open(CONFIG_FILE)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)
            val jsonObject = JSONObject(json)

            if (jsonObject.has("toolbar")) {
                toolbarVisible = jsonObject.getBoolean("toolbar")
            }

            if (jsonObject.has("home")) {
                homeUrl = jsonObject.getString("home")
                if (homeUrl?.isEmpty() == true) {
                    homeUrl = null
                }
            }

            if (jsonObject.has("app_name")) {
                appName = jsonObject.getString("app_name")
            }

            if (jsonObject.has("context_menu")) {
                contextMenuEnabled = jsonObject.getBoolean("context_menu")
            }

            if (jsonObject.has("telemetry")) {
                telemetryEnabled = jsonObject.getBoolean("telemetry")
            }

            if (jsonObject.has("remote_settings")) {
                remoteSettingsEnabled = jsonObject.getBoolean("remote_settings")
            }

            logger.info("Configuration loaded: toolbar=$toolbarVisible, home=$homeUrl, appName=$appName, contextMenuEnabled=$contextMenuEnabled, telemetryEnabled=$telemetryEnabled, remoteSettingsEnabled=$remoteSettingsEnabled")
        } catch (e: Exception) {
            logger.error("Failed to load app-config.json: ${e.message}")
            // Use defaults if file is missing or malformed
            toolbarVisible = true
            homeUrl = null
            appName = "Fenix"
            contextMenuEnabled = true
            telemetryEnabled = true
            remoteSettingsEnabled = true
        }
    }
}
