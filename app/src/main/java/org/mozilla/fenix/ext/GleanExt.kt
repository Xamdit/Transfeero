package org.mozilla.fenix.ext
import android.content.Context
import mozilla.components.service.glean.config.Configuration

fun getCustomGleanServerUrlIfAvailable(context: Context): String? = null
fun Configuration.setCustomEndpointIfAvailable(url: String?): Configuration = this
