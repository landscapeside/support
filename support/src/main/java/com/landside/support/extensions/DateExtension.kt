package com.landside.support.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val YYYYMMDD = "yyyy-MM-dd"
const val YYYYMMDD_CN = "yyyy年MM月dd日"
const val YYYYMMDDHHMM = "yyyy-MM-dd HH:mm"
const val YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss"
const val HHMMSS = " HH:mm:ss"

const val YYYY = "yyyy"
const val MM = "MM"
const val DD = "dd"
const val HH = "HH"
const val mm = "mm"
const val ss = "ss"

fun String.addYear(year:Int,format: String = YYYYMMDD):String{
  val calendar = Calendar.getInstance()
  calendar.time = StringToDate(this, format)
  calendar.add(Calendar.YEAR, year)
  return longToStr(calendar.timeInMillis, format)
}

fun String.getMonth(format: String = YYYYMMDD):Int{
  val calendar = Calendar.getInstance()
  calendar.time = StringToDate(this, format)
  return calendar.get(Calendar.MONTH)
}

fun String.getYear(format: String = YYYYMMDD):Int{
  val calendar = Calendar.getInstance()
  calendar.time = StringToDate(this, format)
  return calendar.get(Calendar.YEAR)
}

fun String.getDay(format: String = YYYYMMDD):Int{
  val calendar = Calendar.getInstance()
  calendar.time = StringToDate(this, format)
  return calendar.get(Calendar.DAY_OF_MONTH)
}

fun String.transDate(
  originFormat: String,
  toFormat: String
):String{
  if (this.isEmpty()) {
    return ""
  }
  val calendar = Calendar.getInstance()
  calendar.time = StringToDate(this, originFormat)
  return longToStr(calendar.timeInMillis, toFormat)
}

fun String.earlyThan(date:String,format: String = YYYYMMDD):Boolean{
  if (this.isEmpty()) return true
  if (date.isEmpty()) return false
  if (this.length != format.length || date.length != format.length) {
    throw IllegalArgumentException("date do not match format")
  }
  val current = Calendar.getInstance()
  current.time = StringToDate(this,format)
  val dest = Calendar.getInstance()
  dest.time = StringToDate(date,format)
  return dest > current
}

fun StringToDate(
  dateStr: String,
  format: String
): Date? {
  val localSimpleDateFormat = SimpleDateFormat(
      format
  )
  var localDate: Date? = null
  try {
    localDate = localSimpleDateFormat.parse(dateStr)
  } catch (localParseException: ParseException) {
    localParseException.printStackTrace()
  }
  return localDate
}

fun longToStr(
  time: Long,
  format: String
): String {
  if (time == 0L){
    return ""
  }
  val dataFormat = SimpleDateFormat(
      format
  )
  val date = Date()
  date.time = time
  return dataFormat.format(date)
}

fun Long.toDate(): String = longToStr(this, YYYYMMDD)

fun Long.toCnDate():String = longToStr(this, YYYYMMDD_CN)

fun Long.toFullDate(): String = longToStr(this, YYYYMMDDHHMMSS)

fun Long.toDelicateDate(): String {
  val today = Calendar.getInstance()
  val calendar = Calendar.getInstance()
  calendar.time = Date(this)
  return when (today.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR)) {
    0 -> "今天 ${longToStr(this, HHMMSS)}"
    1 -> "昨天 ${longToStr(this, HHMMSS)}"
    2 -> "前天 ${longToStr(this, HHMMSS)}"
    3 -> "3天前 ${longToStr(this, HHMMSS)}"
    4 -> "4天前 ${longToStr(this, HHMMSS)}"
    5 -> "5天前 ${longToStr(this, HHMMSS)}"
    else -> toFullDate()
  }
}

fun Date.toSelectedDate():String = longToStr(time, YYYYMMDDHHMM)