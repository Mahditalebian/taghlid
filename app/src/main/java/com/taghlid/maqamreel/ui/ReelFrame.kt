package com.taghlid.maqamreel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taghlid.maqamreel.model.Maqam
import com.taghlid.maqamreel.model.ReelContent
import com.taghlid.maqamreel.model.ReelState

/**
 * یک فریم کامل ۹:۱۶ برای ریل.
 * همه‌چیز با نسبت اندازه‌گیری می‌شود تا در هر عرضی یکسان دیده شود.
 */
@Composable
fun ReelFrame(
    content: ReelContent,
    state: ReelState,
    showTranslation: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    0f to Palette.nightSoft,
                    0.45f to Palette.night,
                    1f to Color(0xFF020307)
                )
            )
    ) {
        // هالهٔ نور پشت آیه
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.9f)
                .height(220.dp)
                .background(
                    Brush.radialGradient(
                        listOf(Palette.gold.copy(alpha = 0.10f), Color.Transparent)
                    )
                )
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = Metrics.screenPad, vertical = 30.dp)
        ) {
            FrameHeader(content)
            Spacer(Modifier.height(26.dp))
            VerseBlock(content, showTranslation)
            Spacer(Modifier.weight(1f))
            if (state == ReelState.QUESTION) {
                QuestionBlock(content)
            } else {
                AnswerBlock(content)
            }
            Spacer(Modifier.weight(1f))
            FrameFooter(content)
        }

        // نشان موج صدا در لبهٔ راست
        WaveMark(
            Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        )
    }
}

@Composable
private fun FrameHeader(content: ReelContent) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = content.surah,
                color = Palette.gold,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
            )
            Text(
                text = content.ayahRange,
                color = Palette.muted,
                fontSize = Type.label,
                letterSpacing = 1.5.sp,
                style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Ltr)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(Palette.teal)
            )
            Spacer(Modifier.width(7.dp))
            Text(
                text = "تحليل التلاوة",
                color = Palette.muted,
                fontSize = Type.label
            )
        }
    }
}

@Composable
private fun VerseBlock(content: ReelContent, showTranslation: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = content.verse,
            color = Palette.bone,
            fontSize = Type.verse,
            lineHeight = 52.sp,
            textAlign = TextAlign.Center,
            fontFamily = Type.quran,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        if (showTranslation) {
            Spacer(Modifier.height(14.dp))
            Text(
                text = content.translation,
                color = Palette.muted,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                textAlign = TextAlign.Center,
                fontFamily = Type.latin,
                style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Ltr)
            )
        }
        Spacer(Modifier.height(18.dp))
        HangingRule()
        Spacer(Modifier.height(12.dp))
        Text(
            text = "القارئ: ${content.reciter}",
            color = Palette.goldSoft,
            fontSize = Type.label,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
    }
}

@Composable
private fun HangingRule() {
    Box(
        Modifier
            .width(54.dp)
            .height(1.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(Color.Transparent, Palette.gold.copy(alpha = 0.65f), Color.Transparent)
                )
            )
    )
}

@Composable
private fun QuestionBlock(content: ReelContent) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = content.hook,
            color = Palette.bone,
            fontSize = Type.hook,
            lineHeight = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Spacer(Modifier.height(22.dp))
        content.options.forEach { option ->
            OptionPill(option, highlighted = false)
            Spacer(Modifier.height(9.dp))
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = "علّق باسم المقام 👇",
            color = Palette.muted,
            fontSize = 13.sp,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
    }
}

@Composable
private fun AnswerBlock(content: ReelContent) {
    val answer = content.answer
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "الإجابة",
            color = Palette.muted,
            fontSize = Type.label,
            letterSpacing = 2.sp,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "مقام ${answer.arabic}",
            color = Palette.gold,
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Text(
            text = answer.latin.uppercase(),
            color = Palette.goldSoft,
            fontSize = Type.label,
            letterSpacing = 2.5.sp,
            fontFamily = Type.latin,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Ltr)
        )
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            answer.traits.forEach { trait -> TraitChip(trait) }
        }
        Spacer(Modifier.height(20.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Metrics.corner))
                .background(Palette.ink.copy(alpha = 0.85f))
                .border(Metrics.hairline, Palette.gold.copy(alpha = 0.18f), RoundedCornerShape(Metrics.corner))
                .padding(16.dp)
        ) {
            Text(
                text = content.analysis,
                color = Palette.bone.copy(alpha = 0.88f),
                fontSize = Type.body,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
            )
        }
    }
}

@Composable
private fun OptionPill(option: Maqam, highlighted: Boolean) {
    val border = if (highlighted) Palette.gold else Palette.gold.copy(alpha = 0.22f)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .border(Metrics.hairline, border, RoundedCornerShape(50))
            .background(Palette.ink.copy(alpha = if (highlighted) 0.9f else 0.45f))
            .padding(horizontal = 18.dp, vertical = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = option.arabic,
            color = if (highlighted) Palette.gold else Palette.bone,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Text(
            text = option.latin,
            color = Palette.muted,
            fontSize = 11.sp,
            letterSpacing = 1.2.sp,
            fontFamily = Type.latin,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Ltr)
        )
    }
}

@Composable
private fun TraitChip(text: String) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(Palette.teal.copy(alpha = 0.18f))
            .padding(horizontal = 11.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Palette.bone.copy(alpha = 0.92f),
            fontSize = 11.sp,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
    }
}

@Composable
private fun FrameFooter(content: ReelContent) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "osool-style · نموذج تجريبي",
                color = Palette.goldSoft,
                fontSize = 10.sp,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "#مقامات #تلاوة #القرآن",
                color = Palette.muted.copy(alpha = 0.75f),
                fontSize = 10.sp,
                style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
            )
        }
        SwipeHint()
    }
}

@Composable
private fun SwipeHint() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "اسحب للمزيد",
            color = Palette.muted.copy(alpha = 0.6f),
            fontSize = 10.sp,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Spacer(Modifier.width(6.dp))
        Box(
            Modifier
                .size(22.dp)
                .clip(CircleShape)
                .border(Metrics.hairline, Palette.gold.copy(alpha = 0.35f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("›", color = Palette.gold, fontSize = 13.sp)
        }
    }
}

/** موج صوتی عمودی، اشاره به تحلیل تلاوت. */
@Composable
private fun WaveMark(modifier: Modifier = Modifier) {
    val bars = listOf(0.35f, 0.7f, 1f, 0.55f, 0.85f, 0.4f)
    Row(
        modifier = modifier.height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        bars.forEach { factor ->
            Box(
                Modifier
                    .width(2.dp)
                    .height((72 * factor).dp)
                    .clip(RoundedCornerShape(50))
                    .background(Palette.gold.copy(alpha = 0.22f))
            )
        }
    }
}
