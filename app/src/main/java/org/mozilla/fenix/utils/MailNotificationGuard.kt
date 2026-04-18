/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import mozilla.components.support.base.log.logger.Logger
import org.mozilla.fenix.R
import org.mozilla.fenix.ext.settings

/**
 * MailNotificationGuard
 *
 * ตรวจสอบว่า Default Mail Application บนเครื่องเปิด Notification ไว้หรือไม่
 * ถ้าปิดอยู่จะแสดง AlertDialog และนำ user ไปเปิด Settings จนกว่าจะเปิดจริง
 *
 * การใช้งาน:
 *   MailNotificationGuard.check(activity)   ← เรียกใน onResume()
 */
object MailNotificationGuard {

    private val logger = Logger("MailNotificationGuard")

    // ป้องกัน dialog โผล่ซ้ำขณะที่ dialog กำลังแสดงอยู่
    private var isDialogShowing = false

    /**
     * Entry point — เรียกจาก onResume() ของ HomeActivity
     * 1. ตรวจสอบ Gmail แบบบังคับก่อน
     * 2. ตรวจสอบ Default Mail App อื่นๆ (ถ้าผู้ใช้ไม่ได้กด ignore)
     */
    fun check(activity: AppCompatActivity) {
        if (isDialogShowing) return

        // --- 1. บังคับตรวจสอบ Gmail (com.google.android.gm) ---
        val gmailPackage = "com.google.android.gm"
        if (isPackageInstalled(activity, gmailPackage) && !isMailNotificationEnabled(activity, gmailPackage)) {
            logger.debug("Gmail notification is DISABLED — showing MANDATORY dialog")
            showGmailMandatoryDialog(activity, gmailPackage)
            return
        }

        // --- 2. ตรวจสอบ Default Mail App แบบทั่วไป (ถ้าไม่ใช่ Gmail และไม่ได้ ignore) ---
        if (activity.settings().ignoreMailNotificationGuard) return

        val mailPackage = getDefaultMailPackage(activity) ?: run {
            logger.debug("No default mail app found — skipping notification check")
            return
        }

        // ถ้า default mail app เป็น Gmail เราตรวจสอบไปแล้วข้างบน ไม่ต้องทำซ้ำ
        if (mailPackage == gmailPackage) return

        logger.debug("Default mail app: $mailPackage")

        if (isMailNotificationEnabled(activity, mailPackage)) {
            logger.debug("Mail notification is enabled ✓")
            return
        }

        logger.debug("Mail notification is DISABLED — showing optional dialog")
        showNotificationRequiredDialog(activity, mailPackage)
    }


    /**
     * ตรวจสอบว่า package นั้นๆ ติดตั้งอยู่ในเครื่องหรือไม่
     */
    private fun isPackageInstalled(context: Context, packageName: String): Boolean {
        return try {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * หา package name ของ default mail application บนเครื่อง
     * กรองเอาเฉพาะแอปที่เป็น Mail Client จริงๆ ไม่เอาพวก System Share หรือ Utility
     */
    fun getDefaultMailPackage(context: Context): String? {
        val preferredPackages = listOf(
            "com.google.android.gm",
            "com.microsoft.office.outlook",
            "com.yahoo.mobile.client.android.mail",
            "com.apple.android.mail.icloud",
            "com.samsung.android.email.provider",
            "com.android.email",
        )

        val blacklistedPackages = listOf(
            "android",
            "com.android.settings",
            "com.android.systemui",
            "com.android.fallback",
            "com.google.android.apps.docs",
        )

        return try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            val pm = context.packageManager
            
            val resolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.queryIntentActivities(
                    intent,
                    PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong()),
                )
            } else {
                @Suppress("DEPRECATION")
                pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            }

            // 1. ลองหาจากลำดับความสำคัญ (Preferred)
            val foundPackages = resolveInfos.mapNotNull { it.activityInfo?.packageName }
            preferredPackages.firstOrNull { it in foundPackages }
                ?: foundPackages.firstOrNull { it !in blacklistedPackages } // 2. หรือเอาอันแรกที่ไม่ใช่ blacklisted
        } catch (e: Exception) {
            logger.error("Failed to resolve default mail package", e)
            null
        }
    }

    /**
     * ตรวจสอบว่า notification ของ package นั้นๆ เปิดอยู่หรือไม่
     *
     * Android 8+ (API 26): ใช้ NotificationManager.getNotificationChannels() —
     *   ถ้า app มี channels แต่ทุก channel ถูก block ถือว่าปิด
     *   ถ้า app ถูก block ระดับ global ก็ถือว่าปิด
     *
     * Android < 8: ตรวจเฉพาะ global notification setting
     */
    fun isMailNotificationEnabled(context: Context, packageName: String): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // ตรวจ global notification สำหรับ package นั้นๆ
                // API 28+: ตรวจผ่าน AppOps หรือ NotificationManagerCompat
                // API 26-27: ตรวจผ่าน AppOps
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // API 28+: ตรวจผ่าน AppOps สำหรับ package อื่น
                    // หรือ NotificationManagerCompat สำหรับ package ตัวเอง
                    if (packageName == context.packageName) {
                        NotificationManagerCompat.from(context).areNotificationsEnabled()
                    } else {
                        isNotificationEnabledForPackageViaOps(context, packageName)
                    }
                } else {
                    // API 26-27
                    isNotificationEnabledForPackageViaOps(context, packageName)
                }
            } else {
                // Pre-Oreo: ตรวจ global ของตัวเราเอง (สมมติ mail app = package ทั่วไป)
                NotificationManagerCompat.from(context).areNotificationsEnabled()
            }
        } catch (e: Exception) {
            logger.error("Error checking notification status for $packageName", e)
            true // ถ้าตรวจไม่ได้ ให้ถือว่าเปิดอยู่ (safe default)
        }
    }

    /**
     * ตรวจ notification status สำหรับ package อื่นผ่าน AppOpsManager
     * ใช้ได้ตั้งแต่ API 19 ขึ้นไป
     */
    private fun isNotificationEnabledForPackageViaOps(context: Context, packageName: String): Boolean {
        return try {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
            @Suppress("DEPRECATION")
            val uid = context.packageManager.getApplicationInfo(packageName, 0).uid

            // OP_POST_NOTIFICATION = 11 (stable across API levels)
            // OPSTR_POST_NOTIFICATION = "android:post_notification" (available API 26+)
            val opStr = "android:post_notification"

            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(opStr, uid, packageName)
            } else {
                @Suppress("DEPRECATION")
                appOps.checkOpNoThrow(opStr, uid, packageName)
            }
            mode == android.app.AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            logger.error("Package not found: $packageName", e)
            true
        } catch (e: Exception) {
            logger.error("AppOps check failed for $packageName", e)
            true
        }
    }

    /**
     * เปิด Notification Settings ของ mail app โดยตรง
     * Android 8+: เปิดหน้า notification settings ของ package นั้นๆ
     * Android < 8: เปิดหน้า app settings ทั่วไป
     */
    fun openMailNotificationSettings(activity: AppCompatActivity, packageName: String) {
        try {
            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    // ใช้ explicit string key เพื่อให้ชัวร์ที่สุดบนหลายๆ OEM
                    putExtra("android.provider.extra.APP_PACKAGE", packageName)
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
            } else {
                @Suppress("DEPRECATION")
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        } catch (e: Exception) {
            logger.error("Failed to open notification settings for $packageName", e)
            // Fallback: เปิดหน้า application details ทั่วไป
            try {
                val fallbackIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                activity.startActivity(fallbackIntent)
            } catch (ex: Exception) {
                logger.error("Fallback settings intent also failed", ex)
            }
        }
    }

    /**
     * แสดง AlertDialog แจ้งให้ user เปิด notification ของ mail app
     */
    fun showNotificationRequiredDialog(activity: AppCompatActivity, packageName: String) {
        if (activity.isFinishing || activity.isDestroyed) return

        isDialogShowing = true

        val mailAppName = getAppName(activity, packageName) ?: "Mail App"

        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_mail_notification_guard, null)
        val ignoreCheckbox = view.findViewById<CheckBox>(R.id.ignore_checkbox)

        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.mail_notification_guard_dialog_title))
            .setMessage(
                activity.getString(
                    R.string.mail_notification_guard_dialog_message,
                    mailAppName,
                    mailAppName,
                ),
            )
            .setView(view)
            .setCancelable(false)
            .setPositiveButton(activity.getString(R.string.mail_notification_guard_dialog_positive_button)) { dialog, _ ->
                if (ignoreCheckbox.isChecked) {
                    activity.settings().ignoreMailNotificationGuard = true
                }
                dialog.dismiss()
                isDialogShowing = false
                openMailNotificationSettings(activity, packageName)
            }
            .setNegativeButton(activity.getString(R.string.mail_notification_guard_dialog_negative_button)) { dialog, _ ->
                if (ignoreCheckbox.isChecked) {
                    activity.settings().ignoreMailNotificationGuard = true
                }
                dialog.dismiss()
                isDialogShowing = false
            }
            .setOnDismissListener {
                isDialogShowing = false
            }
            .show()
    }

    /**
     * แสดง AlertDialog แบบบังคับ (ไม่มีปุ่ม Dismiss/Later) สำหรับ Gmail
     */
    fun showGmailMandatoryDialog(activity: AppCompatActivity, packageName: String) {
        if (activity.isFinishing || activity.isDestroyed) return

        isDialogShowing = true

        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.mail_notification_guard_gmail_title))
            .setMessage(activity.getString(R.string.mail_notification_guard_gmail_message))
            .setCancelable(false)
            .setPositiveButton(activity.getString(R.string.mail_notification_guard_dialog_positive_button)) { dialog, _ ->
                dialog.dismiss()
                isDialogShowing = false
                openMailNotificationSettings(activity, packageName)
            }
            .setOnDismissListener {
                isDialogShowing = false
            }
            .show()
    }

    /**
     * ดึงชื่อแอปจาก packageName สำหรับแสดงใน dialog
     */
    private fun getAppName(context: Context, packageName: String): String? {
        return try {
            val pm = context.packageManager
            @Suppress("DEPRECATION")
            val appInfo = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
