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
