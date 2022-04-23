# Feature list

# Features

## HUDUpdateInterval

Change tab logging hud update interval

* Type: `int`
* Default: `20`
* Options: `1`, `5`, `20`, `100`

## antiSpamDisabled

allow you to spew item in creative with no cooldown

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## hopperCountersUnlimitedSpeed

hopper counter has no cooldown when enabled

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## invalidTileEntityCrashFix

try to fix null or wrong tile entity cause server to crash (<3 worldedit)

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## creativeOpenContainerNoCheck

Creative player can open chest and shulker box regardless when its blocked

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## xpCounter

Count xp sucked by armor stand

TODO: player xp counter

* Type: `String`
* Default: `off`
* Options: `player`, `armorstand`, `off`
* Extra: also enable /xpcounter command to reset or query the counter

## disableBatSpawning

disable Bat Spawning

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## hopperNoItemCost

hopper with wool on top of it doesn't cost item when transfering item out

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## renameFakePlayer

rename bad(length>16) fake player name, This only mean to trigger when bot.conf file corrupted somehow and saved player names get rekted

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## liquidNoBlockBreaking

liquid no longer destory blocks, e.g redstone wire

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## allowNetherWater

allow water to be placed in nether

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

## newRule

do sth

* Type: `boolean`
* Default: `false`
* Options: `true`, `false`

# Commands

## commandServer

bungeecord command tab completions

Related rules:

```java
String serverList = "112s,112c,112m";
```

you can define what tab completion will return to you, but this normally won't matter since bungeecord will take over from /server.

## commandData

/data get player

return player's pos and dim

## commandOverworld

/overworld

tp to overworld

you can tp to default loc or same position as you currently at or any coords you want

## commandNether

/nether

same as commandOverworld

## commandEd

/ed

same as commandOverworld

## commandSurvivalCarpet

/survivalcarpet \<rule> \<value>

allow non op player to toggle some carpet feature, e.g missingTools

Related rules:

```java
String survivalCarpetList = "missingTools";
```

list of feature that allowed by commandSurvivalCarpet, separate different feature name by comma
