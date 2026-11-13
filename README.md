# taghlid

پروژه ساخت اپلیکیشن اندروید (APK).

## Android agent skills

اسکیل‌های رسمی اندروید (از [`android/skills`](https://github.com/android/skills) — مخزن سازمان اندروید گوگل) برای سطح پروژه نصب شده‌اند تا عامل هوش مصنوعی از ابتدا طبق راهنمای رسمی `developer.android.com` کار کند.

نصب‌شده‌ها در `.agents/skills/` (با symlink در `.claude/skills/`):

| Skill | کاربرد |
| --- | --- |
| `android-cli` | ساخت پروژه، نصب APK روی دستگاه/امولاتور، اجرا، AVD، SDK، مستندات |
| `navigation-3` | ناوبری با Navigation 3 |
| `adaptive` | سازگاری UI با گوشی، تبلت، تاشو، TV، Auto، XR |
| `edge-to-edge` | نمایش تمام‌صفحه و مدیریت insets |
| `testing-setup` | راه‌اندازی تست‌های unit و instrumentation |
| `r8-analyzer` | تحلیل و رفع خطاهای R8/minify در build نهایی |
| `android-permissions-security` | مدل مجوزها و امنیت |
| `android-intent-security` | ایمن‌سازی intentها (exported components و…) |

نسخه‌ها در `skills-lock.json` قفل شده‌اند.

### مدیریت

```bash
npx skills list                # فهرست اسکیل‌های پروژه
npx skills update              # به‌روزرسانی همه
npx skills add android/skills --skill camerax -y   # افزودن اسکیل تازه
npx skills remove r8-analyzer  # حذف
```

اسکیل‌های پرکاربرد دیگر که هنوز نصب نشده‌اند: `camerax`، `jetpack-compose-performance`، `anr-audit-and-fix`، `play-policy-insights`، `media3-cast-integration`، `glance-appwidget-updates-and-memory`، `wear-compose-m3`.

## اپ «مقام ریل» (اندروید / Compose)

استودیوی تولید فریم ریل ۹:۱۶ برای محتوای تحلیل مقام تلاوت — الهام‌گرفته از قالب ریل‌های `osool.life`.

| فایل | نقش |
| --- | --- |
| `ui/ReelFrame.kt` | رندر یک فریم کامل: آیه، قاری، گزینه‌های مقام یا پاسخ + تحلیل |
| `ui/ReelStudioScreen.kt` | انتخاب نمونه، جابه‌جایی فریم پرسش/پاسخ، نمایش ترجمه |
| `export/ReelExporter.kt` | خروجی PNG در ۱۰۸۰×۱۹۲۰ با `GraphicsLayer`، ذخیره در گالری یا اشتراک |
| `model/ReelContent.kt` | سه نمونهٔ آماده + ساختار داده |

پیش‌نمایش طرح (رندر با اسکریپت، نه از دستگاه): `docs/preview/reel-question.png` و `docs/preview/reel-answer.png`.

### ساخت APK

این محیط JDK و Android SDK ندارد (`dl.google.com` هم بلاک است)، پس APK اینجا build نمی‌شود. روی ماشین خودتان:

```bash
./gradlew :app:assembleDebug          # خروجی: app/build/outputs/apk/debug/app-debug.apk
./gradlew :app:installDebug           # نصب روی دستگاه/امولاتور متصل
```

یا در Android Studio پوشهٔ ریشه را باز کنید و Run بزنید.

### نکات

- `GraphicsLayer.toImageBitmap()` محتوا را با چگالی دستگاه ضبط می‌کند؛ خروجی به ۱۰۸۰×۱۹۲۰ مقیاس می‌خورد. برای کیفیت حداکثری، فریم را روی دستگاه با عرض ~۳۶۰dp نمایش دهید.
- صحت مقام هر نمونه باید با کارشناس تلاوت تأیید شود؛ داده‌های فعلی نمایشی‌اند.
