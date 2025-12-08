<div align="center">

# 🌙 nightd

**screen dimmer for android tv**

[![GitHub release](https://img.shields.io/github/v/release/sudoxnym/nightd?color=00ffff&style=for-the-badge)](https://github.com/sudoxnym/nightd/releases/latest)
[![F-Droid](https://img.shields.io/badge/F--Droid-repo-b400ff?style=for-the-badge)](https://sudoxnym.github.io/fdroid-repo)
[![License](https://img.shields.io/badge/license-MIT-ff00ff?style=for-the-badge)](LICENSE)

*blacks out the display while keeping media playing. perfect for falling asleep to music without screen burn or light pollution.*

---

</div>

## ✨ features

- **dim mode** — single overlay layer for reduced brightness
- **black mode** — full blackout while audio continues
- **toggle hotkey** — cycles through off → dim → black
- **no root required** — just overlay permission
- **tv remote friendly** — works with key mapper

## 📥 install

### option 1: github releases
```bash
adb install nightd.apk
```
[⬇️ download latest apk](https://github.com/sudoxnym/nightd/releases/latest)

### option 2: f-droid repo

add this repo to f-droid or neostore:
```
https://sudoxnym.github.io/fdroid-repo/repo
```

<a href="https://sudoxnym.github.io/fdroid-repo">
  <img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" height="60">
</a>

## ⚙️ setup

1. install the apk
2. open nightd and grant **"display over other apps"** permission
3. use [key mapper](https://github.com/keymapperorg/KeyMapper) to bind to a remote button

### key mapper config

set up an **activity** intent:

| field | value |
|-------|-------|
| action | `com.sudoxnym.nightd.TOGGLE` |
| package | `com.sudoxnym.nightd` |
| class | `com.sudoxnym.nightd.ToggleActivity` |

**available actions:**
- `TOGGLE` — cycle through modes
- `DIM` — single layer dim
- `BLACK` — full blackout  
- `OFF` — disable overlay

## 🎮 usage

- press your mapped button to cycle: **off → dim → black → off**
- press **back** on remote to turn off overlay from any mode
- tap notification to disable

## 📜 license

MIT — do whatever you want with it

---

<div align="center">

made by [sudoxnym](https://sudoxreboot.com) ⚡

</div>
