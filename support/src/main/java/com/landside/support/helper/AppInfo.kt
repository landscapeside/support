package com.landside.support.helper

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Environment
import java.io.File
import java.math.BigDecimal

object AppInfo {
  fun getAppVersionCode(context: Context): Int {
    var versionCode: Int = 0
    try {
      val pkgName = context.applicationInfo
          .packageName
      versionCode = context.packageManager
          .getPackageInfo(pkgName, 0)
          .versionCode
    } catch (t: Throwable) {
    }
    return versionCode
  }

  fun getAppVersionName(context: Context): String {
    var appVersionName = ""
    try {
      val packageInfo: PackageInfo = context.applicationContext
          .packageManager
          .getPackageInfo(context.packageName, 0)
      appVersionName = packageInfo.versionName
    } catch (e: NameNotFoundException) {
      e.printStackTrace()
    }
    return appVersionName
  }

  fun getPackageName(context: Context): String {
    return try {
      val manager = context.packageManager
      val info = manager.getPackageInfo(
          context.packageName,
          0
      )
      info.packageName
    } catch (e: NameNotFoundException) {
      ""
    }
  }

  val OSVersion:String = Build.VERSION.RELEASE

  val OSName:String = Build.DISPLAY

  @Throws(Exception::class)
  fun getTotalCacheFormatSize(context: Context): String {
    return getFormatSize(getTotalCacheSize(context).toDouble())
  }

  @Throws(Exception::class)
  fun getTotalCacheSize(context: Context):Long{
    var cacheSize =
      getFolderSize(context.cacheDir)
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
      cacheSize += getFolderSize(context.externalCacheDir)
    }
    return cacheSize
  }

  @Throws(Exception::class)
  private fun getFolderSize(file: File?): Long {
    var size: Long = 0
    try {
      val fileList = file!!.listFiles()
      for (i in fileList.indices) {
        // 如果下面还有文件
        size = if (fileList[i]
                .isDirectory
        ) {
          size + getFolderSize(fileList[i])
        } else {
          size + fileList[i]
              .length()
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return size
  }

  private fun getFormatSize(size: Double): String {
    val kiloByte = size / 1024
    if (kiloByte < 1) {
      return "0K"
    }
    val megaByte = kiloByte / 1024
    if (megaByte < 1) {
      val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
      return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
          .toPlainString() + "K"
    }
    val gigaByte = megaByte / 1024
    if (gigaByte < 1) {
      val result2 = BigDecimal(java.lang.Double.toString(megaByte))
      return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
          .toPlainString() + "M"
    }
    val teraBytes = gigaByte / 1024
    if (teraBytes < 1) {
      val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
      return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
          .toPlainString() + "GB"
    }
    val result4 = BigDecimal(teraBytes)
    return (result4.setScale(2, BigDecimal.ROUND_HALF_UP)
        .toPlainString()
        + "TB")
  }

  fun clearAllCache(context: Context) {
    deleteDir(context.cacheDir)
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
      deleteDir(context.externalCacheDir)
    }
  }

  private fun deleteDir(dir: File?): Boolean {
    if (dir != null && dir.isDirectory) {
      val children = dir.list()
      for (i in children.indices) {
        val success = deleteDir(File(dir, children[i]))
        if (!success) {
          return false
        }
      }
    }
    return dir!!.delete()
  }
}