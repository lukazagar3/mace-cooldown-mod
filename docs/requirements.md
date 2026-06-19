# Requirements — Fabric Combat & Ranked Mod

> Glossary: see [CONTEXT.md](file:///C:/Users/Luka/projects/fabric-combat-ranked-mod/docs/CONTEXT.md).

## 1. Cooldowns

<!-- @REQ1@ -->
### Cooldowns
The system manages weapon and action cooldowns to regulate player-versus-player combat.

<!-- @REQ1.1@ (FROM: @REQ1@) -->
### Mace Cooldown
The system SHALL apply a configurable item cooldown to the Mace when a player hits another player.
- Priority: MUST
- Rationale: Regulates Mace high-damage strikes in PVP.
- Acceptance:
  - Scenario: Mace hits a player
    - GIVEN a player holding a Mace
    - WHEN they strike another player with the Mace
    - THEN the Mace item in their hand is put on a visual cooldown matching the configured duration
  - Scenario: Mace hits an entity
    - GIVEN a player holding a Mace
    - WHEN they strike a sheep or zombie with the Mace
    - THEN the Mace item is not put on the custom visual cooldown, and uses the vanilla attack speed cooldown

<!-- @REQ1.2@ (FROM: @REQ1@) -->
### Combat Cooldown
The system SHALL place players in a Combat State for a configurable duration when they hit or are hit by another player.
- Priority: MUST
- Rationale: Prevents escaping combat immediately.
- Related: @REQ1.3@
- Acceptance:
  - Scenario: Entering combat
    - GIVEN two players out of combat
    - WHEN player A hits player B
    - THEN both player A and player B enter the Combat State
    - AND both players see an actionbar message showing their remaining combat time (e.g. "Combat: 10.0s")
    - AND the message counts down in real-time
  - Scenario: Leaving combat
    - GIVEN a player in the Combat State with 0.1s remaining
    - WHEN 1 tick passes
    - THEN the player leaves the Combat State
    - AND their actionbar shows "Out of Combat" in green before disappearing

<!-- @REQ1.3@ (FROM: @REQ1@) -->
### Pearl Cooldown in Combat
The system SHALL enforce a 5-second visual cooldown on Ender Pearls when a player throws one while in the Combat State.
- Priority: MUST
- Rationale: Limits rapid teleportation escapes during active combat.
- Related: @REQ1.2@
- Acceptance:
  - Scenario: Throwing pearl in combat
    - GIVEN a player in the Combat State
    - WHEN they throw an Ender Pearl
    - THEN their Ender Pearls are placed on a visual cooldown of 5 seconds (100 ticks)
    - AND the player's Combat Cooldown duration is not extended by this throw
  - Scenario: Throwing pearl out of combat
    - GIVEN a player out of combat
    - WHEN they throw an Ender Pearl
    - THEN their Ender Pearls are placed on the default vanilla cooldown (1 second)

---

## 2. Ranked System

<!-- @REQ2@ -->
### Ranked System
The system tracks player statistics to rank players on the server.

<!-- @REQ2.1@ (FROM: @REQ2@) -->
### Leaderboard Display
The system SHALL display a leaderboard showing the top 5 players on the scoreboard sidebar (right side of the screen).
- Priority: MUST
- Rationale: Publicly displays player ranks.
- Acceptance:
  - Scenario: Ranking logic
    - GIVEN players with different kills and deaths
    - WHEN the leaderboard is updated
    - THEN players are ranked in descending order of kills
    - AND if two players have equal kills, the player with fewer deaths is ranked higher
    - AND the scoreboard sidebar displays the top 5 players with their rank and kill count

<!-- @REQ2.2@ (FROM: @REQ2@) -->
### Rank Command
The system SHALL provide a `/rank` command that outputs the full ranked list of players to the chat.
- Priority: MUST
- Rationale: Allows players to view ranks beyond the top 5.
- Acceptance:
  - Scenario: Running rank command
    - GIVEN a player on the server
    - WHEN they execute `/rank`
    - THEN they receive a chat message listing all ranked players in order of their rank (kills and deaths)

<!-- @REQ2.3@ (FROM: @REQ2@) -->
### Kill Trading Protection
The system SHALL ignore kills on the same player within a 30-minute window for ranking purposes.
- Priority: MUST
- Rationale: Prevents boosting ranks by farming the same player.
- Acceptance:
  - Scenario: Repeatedly killing the same victim
    - GIVEN player A has killed player B
    - WHEN player A kills player B again within 30 minutes
    - THEN player A's kill count does not increase
    - AND player B's death count does not increase for ranking purposes
  - Scenario: Killing a different victim
    - GIVEN player A has killed player B
    - WHEN player A kills player C within 30 minutes
    - THEN player A's kill count increments by 1
    - AND player C's death count increments by 1

---

## 3. Security & Patches

<!-- @REQ3@ -->
### Security and Patches
The system implements server-side safeguards to prevent exploits and cheats.

<!-- @REQ3.1@ (FROM: @REQ3@) -->
### Tripwire Hook Duplication Patch
The system SHALL prevent tripwire hooks from dropping as item entities if the block is not actually destroyed.
- Priority: MUST
- Rationale: Patches the redstone tripwire hook duplication glitch.
- Acceptance:
  - Scenario: Block update attempts to drop hook
    - GIVEN a tripwire hook setup
    - WHEN a water block or piston update triggers an exploit update on the tripwire hook
    - BUT the hook block is not destroyed (remains in the world)
    - THEN the mod cancels the item drop of the tripwire hook

<!-- @REQ3.2@ (FROM: @REQ3@) -->
### X-ray and Freecam Prevention
The system SHALL incorporate anti-cheat/obfuscation mods to disable X-ray and Freecam.
- Priority: MUST
- Rationale: Prevents players from locating hidden ores/bases and interacting through walls.
- Acceptance:
  - Scenario: Server setup includes anti-cheat
    - GIVEN the test server directory
    - WHEN the server is initialized
    - THEN the `mods` folder contains the verified `AntiXray` and `AntiFreecam` mods
    - AND ores are obfuscated in chunk packets sent to clients
    - AND players cannot interact with blocks/chests through solid walls

<!-- @REQ3.3@ (FROM: @REQ3@) -->
### Locator Bar Removal
The system SHALL automatically disable the locator bar gamerule on server startup if the game version supports it.
- Priority: MUST
- Rationale: Disables the intrusive HUD player locator bar.
- Acceptance:
  - Scenario: Server starts up
    - GIVEN the server is starting
    - WHEN the server finishes initialization
    - THEN the mod automatically sets the `locatorBar` gamerule to `false` if it is present


---

## 4. Server Environment & Integrations

<!-- @REQ4@ -->
### Server Environment and Integrations
The project provides a pre-configured Fabric server with external mod integrations.

<!-- @REQ4.1@ (FROM: @REQ4@) -->
### Simple Voice Chat Integration
The system SHALL download and include the official Simple Voice Chat mod in the server directory.
- Priority: MUST
- Rationale: Provides in-game proximity voice chat.
- Acceptance:
  - Scenario: Server launch with Voice Chat
    - GIVEN the test server launching
    - WHEN the server starts
    - THEN Simple Voice Chat initializes and binds to its default voice port (24454 UDP)

<!-- @REQ4.2@ (FROM: @REQ4@) -->
### Bedrock and Java Compatibility
The system SHALL include Geyser-Fabric and Floodgate-Fabric to allow Bedrock Edition players to connect.
- Priority: MUST
- Rationale: Ensures multi-platform access for Java and Bedrock clients.
- Acceptance:
  - Scenario: Bedrock player connects
    - GIVEN the server running with Geyser and Floodgate
    - WHEN a Bedrock player connects through Geyser's port (19132 UDP)
    - THEN they are authenticated and logged into the server without requiring a Java account

<!-- @REQ4.3@ (FROM: @REQ4@) -->
### In-game Configuration Wizard
The system SHALL prompt the first OP player who joins to configure the Mace and Combat cooldowns if they are not set.
- Priority: MUST
- Rationale: Enables easy server setup inside the game.
- Acceptance:
  - Scenario: OP joins unconfigured server
    - GIVEN a new server with `isConfigured` set to false
    - WHEN an operator (OP) player joins the game
    - THEN they receive interactive chat messages asking them to input the Mace and Combat cooldown durations
    - AND once they complete the inputs, the values are saved to the config file
    - AND `isConfigured` is set to true
