# ur-2d-platformer

## Self-Review

- **Level Loading and Rendering:** I correctly loaded the levels from image files and rendered them in the game. The level data is accurately represented using RGB values corresponding to different game objects.
- **Player Movement:** I implemented the player character's ability to move left and right, jump, and perform attacks. The movement mechanics are smooth and responsive.
- **Collision Detection:** I ensured that collision detection between the player and level tiles is implemented correctly. The player cannot pass through solid tiles and interacts with them appropriately.
- **Level Completion:** The game detects when the player reaches the finishing point (gray tile) and advances to the next level. I stopped the level timer and added the remaining time to the total score.
- **Game Completion:** When the player completes all 10 levels, the game ends, and the total score is displayed on the game completed screen.

**Remaining Bugs or Defects:**

- **Spikes and Drones:** I couldn't complete the implementation of spikes and drones that shoot projectiles. The spikes don't render correctly, and the drones are not fully functional.
- **Additional Level Elements:** Due to time constraints, I couldn't add some desired level elements and features, such as more diverse obstacles and challenges.

## Design

I decomposed the game into several classes and methods to handle different aspects of the game logic:

- **Game class:** I represented the main game logic and managed the game loop. It initializes necessary classes, handles game updates, rendering, and the game loop.
- **LevelManager class:** I handled the loading and management of levels. It loads level data from image files, creates Level objects, and manages the progression through levels.
- **Level class:** I represented a single level in the game. It stores level data, player spawn points, and handles level-specific logic such as completion detection and timer management.
- **Player class:** I represented the player character in the game. It handles player movement, collision detection, and rendering.
- **GamePanel and GameWindow classes:** I handled the game's graphical user interface and window management.

I implemented the game loop using a separate thread to handle game updates and rendering independently, allowing for smooth gameplay and consistent frame rates.

## Metadata

- **Estimated Total Time Spent:** 20 hours
- **Tricky Bugs Encountered:**
  - **Thread synchronization:** I ensured proper synchronization between game updates and rendering to avoid race conditions and inconsistencies.
  - **Collision detection:** Implementing accurate collision detection between the player and level tiles, particularly when dealing with complex shapes and edge cases.
  - **Spike rendering:** I encountered difficulties in rendering spikes correctly within the levels, requiring further investigation and debugging.

## Manual

### Controls

- **A** Move left
- **D** Move right
- **W** Jump
- **K:** Attack

### Objective

- Navigate through each level and reach the finishing point (gray tile) to advance to the next level.
- Complete all 10 levels to finish the game and obtain a total score based on the remaining time in each level.

### Scoring

- Each level has a timer, and the remaining time when completing a level is added to the total score.
- Try to complete each level as quickly as possible to maximize your score.

## Note

The game is still in development, and some features, such as spikes and drones, are not fully implemented. Future updates will aim to enhance the gameplay experience and add more challenging elements to the levels.

