package com.taghlid.maqamreel.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** پالت الهام‌گرفته از هویت osool: شبانه، طلایی، آرام. */
object Palette {
    val night = Color(0xFF05070D)
    val nightSoft = Color(0xFF0B1226)
    val ink = Color(0xFF111A33)
    val gold = Color(0xFFD9B36C)
    val goldSoft = Color(0xFF8C7448)
    val bone = Color(0xFFF2EDE3)
    val muted = Color(0xFF9AA6BE)
    val teal = Color(0xFF3E7C77)
}

object Type {
    val quran = FontFamily.Serif
    val latin = FontFamily.SansSerif

    val verse = 30.sp
    val hook = 28.sp
    val label = 12.sp
    val body = 15.sp
}

object Metrics {
    val screenPad = 28.dp
    val corner = 22.dp
    val hairline = 1.dp
}

/** وزن‌های تایپوگرافی که در چند جای فریم استفاده می‌شوند. */
object Weights {
    val display = FontWeight.Light
    val strong = FontWeight.SemiBold
    val normal = FontWeight.Normal
}
