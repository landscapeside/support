package com.landside.support.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
import androidx.core.content.FileProvider
import java.io.File

object Installer {
  fun install(
    context: Context,
    file: File
  ) {

    val installAllowed: Boolean
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      installAllowed = context.packageManager.canRequestPackageInstalls()
      if (installAllowed) {
        installApk(context, file)
      } else {
        val intent =
          Intent(ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + context.packageName))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        installApk(context, file)
        return
      }
    } else {
      installApk(context, file)
    }
  }

  private fun installApk(
    context: Context,
    file: File
  ) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      val contentUri =
        FileProvider.getUriForFile(context, AppInfo.getPackageName(context) + ".FileProvider", file)
      intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
    } else {
      intent.setDataAndType(
          Uri.parse("file://" + file.absolutePath),
          "application/vnd.android.package-archive"
      )
    }
    context.startActivity(intent)
  }
}