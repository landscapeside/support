package com.landside.support.extensions

import org.jetbrains.annotations.NotNull
import java.util.regex.Pattern

fun String?.withDefault(def: String): String = if (isNullOrEmpty()) def else this!!

fun String.addSuffix(suffix: String): String = if (isNullOrEmpty()) this else "${this}${suffix}"

fun String.resetIf(
  @NotNull predicate: String,
  resultSetter: () -> String = { "" }
) = if (isNullOrEmpty() || this == predicate) resultSetter() else this

fun String.maskPhone(): String = replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")

val PHONE_PATTERN = "^[1][2-9][0-9]{9}$"

fun String.isMobile(): Boolean {
  if (isEmpty()) {
    return false
  }
  val phonePattern = Pattern.compile(PHONE_PATTERN)
  return phonePattern.matcher(this)
      .matches()
}

const val SMS_CODE_PATTERN = "^[0-9]+$"

fun String.isSmsCode(): Boolean {
  if (isEmpty()) {
    return false
  }
  val pattern = Pattern.compile(SMS_CODE_PATTERN)
  return pattern.matcher(this)
      .matches()
}

fun String.coerceInLength(
  minLength: Int,
  maxLength: Int
): Boolean =
  length in minLength..maxLength

fun String.isOneOf(vararg args: String): Boolean {
  return args.any { it == this }
}

fun String.isNoneOf(vararg args: String): Boolean {
  return args.none { it == this }
}

fun String.ensureHttps(): String = if (startsWith("//")) "https:$this" else this

fun String.ensureHttp(): String =
  if (startsWith("http").not() && startsWith("https")) {
    "http://${this}"
  } else this

fun String.ellipsis(
  maxSize: Int = 6
) = if (this.length > maxSize) {
  this.substring(0, maxSize) + "..."
} else {
  this
}

val String.birthDayByIdCard: String
  get() = run {
    when (length) {
      15 -> {
        "19${substring(6, 12)}"
      }
      18 -> {
        substring(6, 14)
      }
      else -> {
        ""
      }
    }
  }