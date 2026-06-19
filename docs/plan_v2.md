# Combat Mod Implementation Plan

Implement the requested features for the Combat Ranked Mod:
- Combat logging penalty (death on disconnect while in combat).
- Commands to modify configuration dynamically.
- Ender Pearl cooldown while in combat.
- Verify Mace cooldown implementation.

## Proposed Changes

### `com.example.combatmod.listener`
#### [MODIFY] [ModListeners.java](file:///c:/Users/Luka/projects/fabric-combat-ranked-mod/src/main/java/com/example/combatmod/listener/ModListeners.java)
- Add a listener for `ServerPlayConnectionEvents.DISCONNECT` to kill the player if `CooldownManager.isInCombat(player)` is true. Broadcast a death message to all players: `[PlayerName] died through combat log`.
- Add a listener to `UseItemCallback` to intercept Ender Pearl usage. If the player throws an Ender Pearl and is in combat, apply the configured `pearlCooldownSeconds` to the `ItemCooldownManager` for `Items.ENDER_PEARL`.

### `com.example.combatmod.command`
#### [NEW] [ConfigCommand.java](file:///c:/Users/Luka/projects/fabric-combat-ranked-mod/src/main/java/com/example/combatmod/command/ConfigCommand.java)
- Create a new command registerable via `CommandRegistrationCallback`.
- Add subcommands under `/combatmod config <key> <value>` to modify `maceCooldownSeconds`, `combatCooldownSeconds`, and `pearlCooldownSeconds` on the fly.
- Require operator permissions (level 2 or higher) to use the command.
- Once a value is changed, update `ModConfig` and call `ConfigManager.save()` to persist changes.

### `com.example.combatmod`
#### [MODIFY] [CombatMod.java](file:///c:/Users/Luka/projects/fabric-combat-ranked-mod/src/main/java/com/example/combatmod/CombatMod.java)
- Call `ConfigCommand.register()` in `onInitialize()` to make the command available on the server.

## Verification Plan

### Automated Tests
- Run `.\gradlew build` to ensure the mod compiles and passes basic checks.

### Manual Verification
- Start the server and have a player join.
- **Combat Log:** Hit another player to enter combat, then disconnect. Rejoin to verify the player died and that the chat broadcasted the death message.
- **Mace Cooldown:** Hit another player with a Mace. Ensure the cooldown applies. Hit a mob with a Mace. Ensure the cooldown does NOT apply.
- **Ender Pearl:** Throw an ender pearl while out of combat (no cooldown). Hit a player to enter combat, then throw an ender pearl. Ensure the cooldown is applied visually.
- **Commands:** Run `/combatmod config combatCooldownSeconds 20` and verify the new time applies and that the `fabric-combat-ranked-mod.json` file is updated.
