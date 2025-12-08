# nightd

screen dimmer for android tv. keeps media playing while the screen goes dark.

## download

[nightd.apk](https://github.com/sudoxnym/nightd/releases/latest)

## install

```bash
adb install nightd.apk
```

grant "display over other apps" permission when prompted.

## usage

use [key mapper](https://github.com/keymapperorg/KeyMapper) to bind to a remote button.

set up an **activity** intent:
- **action:** `com.sudoxnym.nightd.TOGGLE`
- **package:** `com.sudoxnym.nightd`
- **class:** `com.sudoxnym.nightd.ToggleActivity`

other actions: `OFF`, `DIM`, `BLACK`

## license

MIT
