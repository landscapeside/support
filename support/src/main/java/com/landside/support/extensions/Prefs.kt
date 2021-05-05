/**
 * Copyright (C) 2017 Tony Shen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.landside.support.extensions

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private inline fun <T> SharedPreferences.delegate(
    key: String? = null,
    defaultValue: T,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: Editor.(String, T) -> Editor
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defaultValue)!!

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            edit().setter(key ?: property.name, value).apply()
    }

fun SharedPreferences.int(key: String? = null, defValue: Int = 0): ReadWriteProperty<Any, Int> {
    return delegate(key, defValue, SharedPreferences::getInt, Editor::putInt)
}

fun SharedPreferences.long(key: String? = null, defValue: Long = 0): ReadWriteProperty<Any, Long> {
    return delegate(key, defValue, SharedPreferences::getLong, Editor::putLong)
}

fun SharedPreferences.float(key: String? = null, defValue: Float = 0f): ReadWriteProperty<Any, Float> {
    return delegate(key, defValue, SharedPreferences::getFloat, Editor::putFloat)
}

fun SharedPreferences.boolean(key: String? = null, defValue: Boolean = false): ReadWriteProperty<Any, Boolean> {
    return delegate(key, defValue, SharedPreferences::getBoolean, Editor::putBoolean)
}

fun SharedPreferences.string(key: String? = null, defValue: String = ""): ReadWriteProperty<Any, String> {
    return delegate(key, defValue, SharedPreferences::wrapStringGet, Editor::wrapStringPut)
}

fun SharedPreferences.wrapStringGet(
    key: String,
    def: String
): String {
    return getString(key, def) ?: ""
}

fun Editor.wrapStringPut(
    key: String,
    value: String?
): Editor {
    return putString(key, value ?: "")
}

fun SharedPreferences.double(
    key: String? = null,
    defValue: Double = 0.0
): ReadWriteProperty<Any, Double> {
    return delegate(key, defValue, SharedPreferences::getDouble, Editor::putDouble)
}

fun SharedPreferences.getDouble(
    key: String,
    def: Double
): Double {
    if (!contains(key)) {
        return def
    }
    return Double.fromBits(getLong(key, 0))
}

fun Editor.putDouble(
    key: String,
    value: Double
): Editor {
    return putLong(key, value.toRawBits())
}

fun SharedPreferences.clear() {
    edit().clear().apply()
}