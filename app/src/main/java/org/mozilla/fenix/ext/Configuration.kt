package org.mozilla.fenix.ext
import mozilla.components.service.glean.config.Configuration

fun Configuration.copy(channel: String? = null): Configuration = this
