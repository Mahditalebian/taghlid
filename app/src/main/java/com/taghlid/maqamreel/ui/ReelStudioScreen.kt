package com.taghlid.maqamreel.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taghlid.maqamreel.export.ReelExporter
import com.taghlid.maqamreel.model.ReelContent
import com.taghlid.maqamreel.model.ReelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * استودیوی تولید ریل: انتخاب نمونه، جابه‌جایی بین فریم پرسش و پاسخ،
 * و خروجی PNG در ابعاد ۱۰۸۰×۱۹۲۰.
 */
@Composable
fun ReelStudioScreen() {
    val context = LocalContext.current
    var index by remember { mutableIntStateOf(0) }
    var state by remember { mutableStateOf(ReelState.QUESTION) }
    var showTranslation by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }

    val content = ReelContent.samples[index]
    val graphicsLayer = rememberGraphicsLayer()
    val scope = rememberCoroutineScope()
    val exporter = remember { ReelExporter(context) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Palette.night)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StudioHeader()
            Spacer(Modifier.height(16.dp))

            // فریمی که هم نمایش داده می‌شود و هم ضبط می‌شود
            Box(
                Modifier
                    .fillMaxWidth()
                    .drawWithContentRecording(graphicsLayer)
            ) {
                ReelFrame(
                    content = content,
                    state = state,
                    showTranslation = showTranslation,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(20.dp))
            StateToggle(state) { state = it }
            Spacer(Modifier.height(12.dp))
            SamplePicker(ReelContent.samples.size, index) { index = it }
            Spacer(Modifier.height(12.dp))
            TranslationToggle(showTranslation) { showTranslation = it }
            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        busy = true
                        scope.launch {
                            runCatching {
                                withContext(Dispatchers.Default) {
                                    val bitmap = exporter.export(graphicsLayer)
                                    exporter.saveToGallery(
                                        bitmap,
                                        "reel-${content.answer.latin}-${state.name.lowercase()}.png"
                                    )
                                }
                            }.onSuccess {
                                Toast.makeText(context, "ذخیره شد در Pictures/MaqamReel", Toast.LENGTH_LONG).show()
                            }.onFailure {
                                Toast.makeText(context, "خطا: ${it.message}", Toast.LENGTH_LONG).show()
                            }
                            busy = false
                        }
                    },
                    enabled = !busy,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Palette.gold,
                        contentColor = Palette.night
                    )
                ) {
                    Text(if (busy) "…" else "ذخیره PNG", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = {
                        busy = true
                        scope.launch {
                            runCatching {
                                withContext(Dispatchers.Default) {
                                    val bitmap = exporter.export(graphicsLayer)
                                    exporter.share(
                                        bitmap,
                                        "reel-${content.answer.latin}-${state.name.lowercase()}.png"
                                    )
                                }
                            }.onSuccess { intent ->
                                context.startActivity(
                                    android.content.Intent.createChooser(intent, "اشتراک‌گذاری فریم")
                                )
                            }.onFailure {
                                Toast.makeText(context, "خطا: ${it.message}", Toast.LENGTH_LONG).show()
                            }
                            busy = false
                        }
                    },
                    enabled = !busy,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Palette.bone)
                ) {
                    Text("اشتراک", fontSize = 14.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = "خروجی: ۱۰۸۰×۱۹۲۰ پیکسل · مناسب ریل",
                color = Palette.muted,
                fontSize = 11.sp
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

/** محتوا را در لایهٔ گرافیکی ضبط می‌کند تا بعداً PNG گرفته شود. */
private fun Modifier.drawWithContentRecording(layer: GraphicsLayer): Modifier =
    this.then(
        Modifier.drawWithContent {
            layer.record { this@drawWithContent.drawContent() }
            drawLayer(layer)
        }
    )

@Composable
private fun StudioHeader() {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "مقام · استودیو",
            color = Palette.bone,
            fontSize = 22.sp,
            fontWeight = FontWeight.Light,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Text(
            text = "ساخت فریم ریل ۹:۱۶ برای تحلیل تلاوت",
            color = Palette.muted,
            fontSize = 12.sp,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
    }
}

@Composable
private fun StateToggle(state: ReelState, onChange: (ReelState) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ReelState.entries.forEach { candidate ->
            val selected = candidate == state
            Box(
                Modifier
                    .weight(1f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (selected) Palette.ink else Palette.nightSoft)
                    .border(
                        1.dp,
                        if (selected) Palette.gold else Palette.gold.copy(alpha = 0.15f),
                        RoundedCornerShape(50)
                    )
                    .clickableNoRipple { onChange(candidate) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (candidate == ReelState.QUESTION) "فریم پرسش" else "فریم پاسخ",
                    color = if (selected) Palette.gold else Palette.muted,
                    fontSize = 13.sp,
                    style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
                )
            }
        }
    }
}

@Composable
private fun SamplePicker(count: Int, index: Int, onSelect: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(count) { i ->
            val selected = i == index
            Box(
                Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (selected) Palette.gold else Palette.ink)
                    .clickableNoRipple { onSelect(i) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${i + 1}",
                    color = if (selected) Palette.night else Palette.muted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun TranslationToggle(show: Boolean, onChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "نمایش ترجمهٔ انگلیسی",
            color = Palette.muted,
            fontSize = 12.sp,
            style = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Rtl)
        )
        Spacer(Modifier.size(10.dp))
        Switch(
            checked = show,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Palette.night,
                checkedTrackColor = Palette.gold,
                uncheckedThumbColor = Palette.muted,
                uncheckedTrackColor = Palette.ink
            )
        )
    }
}

private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier =
    this.then(
        Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            onClick = onClick
        )
    )
