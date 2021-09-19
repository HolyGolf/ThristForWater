[![logo](/images/11.png)](https://www.spigotmc.org/resources/thirstforwater.84634/)
# ThristForWater
![GitHub release (latest by date)](https://img.shields.io/github/v/release/HolyGolf/ThirstForWater?style=flat-square)
![GitHub issues](https://img.shields.io/github/issues/HolyGolf/ThirstForWater?style=flat-square)
![GitHub last commit](https://img.shields.io/github/last-commit/HolyGolf/ThirstForWater?style=flat-square)
![GitHub all releases](https://img.shields.io/github/downloads/HolyGolf/ThirstForWater/total?style=flat-square)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/HolyGolf/ThirstForWater?style=flat-square)

This plugin adds a water thirst mechanic that can be quenched by drinking water from a bottle or directly from a reservoir.

![craft](/images/222.png)

If you drink a bottle of water, there is a certain chance that you will get poisoned. You can also drink directly from the reservoir (Shift + Right Click on water), but if you are not the king of fortune, then you will get poisoned.

- When you run, the water disappears faster.

- In the default nether, water disappears a little faster =)

- Purified water restores 35 points, and ordinary water restores 20 points.

- I recommend boiling water in the furnace ^-^

```yml
Note: Remember that if you are AFK, then the water disappear anyway.
```
![bar](/images/333.png)
If you have only 3 points of water left, then you cannot run. Unless, of course, you turn it off in the configs.

```yml
PlaceHolders:

%thirst_percents%
%thirst_status%
%thirst_indicator1%
%thirst_indicator2%
```
```yml
Commands:

/tfw
/tfw help
/tfw reload (Thirstforwater.tfw.reload)
/tfw settings (Thirstforwater.tfw.settings).
/tfw enable | disable (Thirstforwater.tfw.settings).
```
```yml
Permissions:

Thirstforwater.tfw.settings
Thirstforwater.tfw.reload
thirstforwater.nothirst - With this permission, the player's thirst is not reduced
Thirstforwater.tfw.vip - With this permission, thirst is spent longer (x2 slower)
```
When you are saturated with water, the indicator turns blue.

![placeholders](/images/5.png)

![placeholders](/images/6.png)

![zero](/images/444.png)

If there are no points left, then you will start dying. Hooray!

And of course you can customize a lot in the configuration file.

```yml
Plugin: true
  #Enable/Disable plugin

Enabled_Worlds:
 - world:15
  - world_nether:11
  - world_the_end:15

#World : Time for which thirst will increase at a normal pace (Seconds)

SprintRate_Worlds:
 - world:6
  - world_nether:4
  - world_the_end:6

#World : Time for which thirst will increase when running (Seconds)

#-------------------------------------------------------

Poisoning bottle chance: 50
  #Chance to get poisoned when the player drinks a bottle of plain water.
Poisoning water chance: 90
  #Chance to get poisoned when the player drinks water from the reservoir.
Poisoning duration: 20
  #How long does the poisoning last (In seconds).
Damage: 3
  #Damage to player per second when water runs out.
WaterRecoveryBottle: 20
  #The amount of water added when drinking from a regular water bottle.
WaterRecoveryClearWater: 35
  #The amount of water added when drinking from a clean water bottle.
WaterRecoveryWater: 20
  #The amount of added water when drinking from the water block.

#-------------------------------------------------------

Sprint: false
  #If the water is at level 19 or less, then the player can't run.
CustomRecipe: true
  #Turn on or off the custom recipe for a clean water bottle.
Actionbar: true
  #Water indicator above the toolbar. (true/false)

Indicator_full: '&6&l}|&1####################&6&l|{'
Indicator_empty: '&6&l}|&4---------------------&6&l|{'
Char1: '&9#'
Char2: '&8-'
Char_before_Indicator: '&6&l}|'
Char_after_Indicator: '&6&l|{'

PlaceHolder_Indicator2_Indicator_full: '&6&l}|&1####################&6&l|{'
PlaceHolder_Indicator2_Indicator_empty: '&6&l}|&4---------------------&6&l|{'
PlaceHolder_Indicator2_Char1: '&9#'
PlaceHolder_Indicator2_Char2: '&8-'
PlaceHolder_Indicator2_Char_before_Indicator: '&6&l}|'
PlaceHolder_Indicator2_Char_after_Indicator: '&6&l|{'

Messages: true
  #Messages if the water indicator is off

#-------------------------------------------------------

Nopermission: '&cYou have no permission to do this!'
  #Message if the player does not have permission to execute the command
LowWaterMessage: '&cYou need to drink!'
  #Message if the water indicator is off and the player is running out of water.
HighWaterMessage: '&aYou dont want to drink anymore.'
  #Message if the water indicator is off and the player has drunk enough water.
WaterLore: '&aThe water in this bottle is purified.'
  #Description of the clear water bottle.
WaterName: '&bClear water bottle'
  #The name of the pure water bottle.

#-------------------------------------------------------

debug: false
#Don't touch data. It's Player UUID : Amount of water.
data:
```
