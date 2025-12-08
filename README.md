<p align="center">
  <img src="assets/banner.png" alt="nightd banner" width="400"/>
</p>

<h1 align="center">nightd</h1>
<p align="center">
  <em>screen dimmer for android tv</em><br>
  <strong>keep your media playing while the screen goes dark</strong>
</p>

<p align="center">
  <a href="https://github.com/sudoxnym/nightd/releases/latest"><img src="https://img.shields.io/github/v/release/sudoxnym/nightd?style=flat-square&color=00d4ff" alt="latest release"/></a>
  <a href="https://github.com/sudoxnym/nightd/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-MIT-ff69b4?style=flat-square" alt="license"/></a>
  <img src="https://img.shields.io/badge/android-7.0+-7B68EE?style=flat-square" alt="android 7.0+"/>
</p>

---

## what is this?

nightd is a tiny android tv app that blacks out your screen while keeping your media playing. perfect for:

- **falling asleep to music/podcasts** - screen goes dark, audio keeps playing
- **saving power on oled displays** - no burn-in, no wasted pixels
- **background audio** - cast audio without lighting up your room

## modes

| mode | what it does |
|------|-------------|
| **dim** | single overlay layer (~40% darker) |
| **black** | 5 stacked layers = pitch black (android limits overlay opacity to 80%, so we stack 'em) |

cycle order: `off → dim → black → off`

## installation

1. download the latest [nightd.apk](https://github.com/sudoxnym/nightd/releases/latest)
2. sideload via adb: `adb install nightd.apk`
3. grant "display over other apps" permission when prompted

## usage

### with key mapper (recommended)

use [key mapper](https://github.com/keymapperorg/KeyMapper) to bind nightd to remote buttons:

| action | intent |
|--------|--------|
| toggle | `com.sudox.nightd.TOGGLE` |
| dim mode | `com.sudox.nightd.DIM` |
| black mode | `com.sudox.nightd.BLACK` |
| turn off | `com.sudox.nightd.OFF` |

**package:** `com.sudox.nightd`  
**service:** `com.sudox.nightd.NightdService`

example: bind "zoom in" to toggle, "zoom out" to off.

### with adb

```bash
# toggle (cycles through off → dim → black → off)
adb shell am startservice -a com.sudox.nightd.TOGGLE com.sudox.nightd/.NightdService

# specific modes
adb shell am startservice -a com.sudox.nightd.DIM com.sudox.nightd/.NightdService
adb shell am startservice -a com.sudox.nightd.BLACK com.sudox.nightd/.NightdService
adb shell am startservice -a com.sudox.nightd.OFF com.sudox.nightd/.NightdService
```

## how it works

android limits overlay opacity to 80% per layer. to achieve true black, nightd stacks 5 layers:

```
0.8 × 0.8 × 0.8 × 0.8 × 0.8 = 0.032 (96.8% blocked)
```

plus it sets screen brightness to 0 for good measure.

## permissions

- **SYSTEM_ALERT_WINDOW** - required to draw overlay
- **FOREGROUND_SERVICE** - keeps the overlay running
- **WRITE_SETTINGS** - adjusts screen brightness (optional, gracefully fails)

## building from source

```bash
# clone
git clone https://github.com/sudoxnym/nightd.git
cd nightd

# build (requires android ndk)
./gradlew assembleRelease

# or use the aapt/d8/apksigner toolchain directly
```

## license

MIT - do whatever you want with it.

---

<p align="center">
  <sub>built by <a href="https://github.com/sudoxnym">sudoxnym</a> for the sleep-deprived</sub>
</p>
