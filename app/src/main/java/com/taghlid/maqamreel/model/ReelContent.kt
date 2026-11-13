package com.taghlid.maqamreel.model

/** حالت فریمی که رندر می‌شود: پرسش یا نمایش پاسخ. */
enum class ReelState { QUESTION, ANSWER }

/** یک مقام موسیقیایی با نام و ویژگی‌های کوتاه. */
data class Maqam(
    val arabic: String,
    val latin: String,
    val traits: List<String>
)

/**
 * محتوای یک ریل: آیه، قاری، گزینه‌ها و پاسخ.
 * توجه: صحت مقام هر نمونه را باید با کارشناس تلاوت تأیید کنید؛
 * مقدارهای فعلی فقط برای دیدن ظاهر و جریان کار است.
 */
data class ReelContent(
    val surah: String,
    val ayahRange: String,
    val verse: String,
    val translation: String,
    val reciter: String,
    val hook: String,
    val options: List<Maqam>,
    val answer: Maqam,
    val analysis: String
) {
    companion object {
        val samples: List<ReelContent> = listOf(
            ReelContent(
                surah = "سورة العلق",
                ayahRange = "96 : 6-8",
                verse = "كَلَّا إِنَّ الْإِنسَانَ لَيَطْغَىٰ ۝ أَن رَّآهُ اسْتَغْنَىٰ ۝ إِنَّ إِلَىٰ رَبِّكَ الرُّجْعَىٰ",
                translation = "No! Indeed, man transgresses, because he sees himself self-sufficient. Indeed, to your Lord is the return.",
                reciter = "الشيخ أحمد الغيطاني",
                hook = "بأيِّ مقامٍ يتلو الشيخ؟",
                options = listOf(
                    Maqam("البيات", "Bayati", listOf("قرار دافئ", "صعود تدريجي", "حزن هادئ")),
                    Maqam("الحجاز", "Hijaz", listOf("فاصل مجاز", "شجن واضح", "قفلة حادة")),
                    Maqam("الصبا", "Saba", listOf("إحساس حزين", "درجة رابعة منخفضة", "بطء نسبي")),
                    Maqam("النهاوند", "Nahawand", listOf("لون غربي", "سلاسة", "استقرار")),
                ),
                answer = Maqam("البيات", "Bayati", listOf("قرار دافئ", "صعود تدريجي", "حزن هادئ")),
                analysis = "مقام البيات: يبدأ من قرار دافئ ثم يصعد عبر جنس البيات، مع قفلة تعيد الاستقرار. نمونهٔ نمایشی، برای تأیید با کارشناس تلاوت بررسی شود."
            ),
            ReelContent(
                surah = "سورة الرحمن",
                ayahRange = "55 : 1-4",
                verse = "الرَّحْمَٰنُ ۝ عَلَّمَ الْقُرْآنَ ۝ خَلَقَ الْإِنسَانَ ۝ عَلَّمَهُ الْبَيَانَ",
                translation = "The Most Merciful taught the Qur'an, created man, and taught him eloquence.",
                reciter = "الشيخ أحمد الغيطاني",
                hook = "هل هذا المقام فرح أم شجن؟",
                options = listOf(
                    Maqam("الرست", "Rast", listOf("اتزان", "صعود مطمئن", "قرار واضح")),
                    Maqam("البيات", "Bayati", listOf("قرار دافئ", "صعود تدريجي", "حزن هادئ")),
                    Maqam("العجم", "Ajam", listOf("بهجة", "درجة رابعة مرتفعة", "انفتاح")),
                    Maqam("الكرد", "Kurd", listOf("لون شرقي حاد", "بساطة", "حركة مباشرة")),
                ),
                answer = Maqam("الرست", "Rast", listOf("اتزان", "صعود مطمئن", "قرار واضح")),
                analysis = "مقام الرست: البداية المتزنة والدرجات المستقرة تعطي إحساس الطمأنينة المناسب لسياق الآيات. نمونهٔ نمایشی."
            ),
            ReelContent(
                surah = "سورة الفاتحة",
                ayahRange = "1 : 1-2",
                verse = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝ الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                translation = "In the name of Allah, the Entirely Merciful, the Especially Merciful. All praise is due to Allah, Lord of the worlds.",
                reciter = "الشيخ أحمد الغيطاني",
                hook = "مقامان يتشابهان… أحدهما هنا",
                options = listOf(
                    Maqam("الصبا", "Saba", listOf("إحساس حزين", "درجة رابعة منخفضة", "بطء نسبي")),
                    Maqam("الحجاز", "Hijaz", listOf("فاصل مجاز", "شجن واضح", "قفلة حادة")),
                    Maqam("البيات", "Bayati", listOf("قرار دافئ", "صعود تدريجي", "حزن هادئ")),
                ),
                answer = Maqam("الحجاز", "Hijaz", listOf("فاصل مجاز", "شجن واضح", "قفلة حادة")),
                analysis = "مقام الحجاز: الفاصل الزائد بين الدرجتين الثانية والثالثة يعطي الشجن المميز. نمونهٔ نمایشی."
            ),
        )
    }
}
