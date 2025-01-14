// GameConstants.java

package other;

public class GameConstants {

    public static final int IDLE = 0;
    public static final int RUN = 1;
    public static final int JUMP = 2;
    public static final int ATTACK1 = 3;
    public static final int ATTACK2 = 4;
    public static final int ATTACK3 = 5;


    public static final int[] LEVEL_TIMERS = {
            15, // Level 1: 10 seconds
            20,
            20,
            15,
            30,
            35,
            20,
            20,
            20,
            30
    };




    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    // Get the action name based on the action constant
    public static String getActionName(int action) {
        switch (action) {
            case IDLE:
                return "idle";
            case RUN:
                return "run";
            case JUMP:
                return "jump";
            case ATTACK1:
                return "attack1";
            case ATTACK2:
                return "attack2";
            case ATTACK3:
                return "attack3";
            default:
                return "";
        }
    }

    // Get the number of sprites for the specified player action
    public static int getSpriteAmount(int player_action) {
        switch (player_action) {
            case IDLE, JUMP, IDLE + 6, JUMP + 6:
                return 4;
            case RUN, ATTACK1, RUN + 6, ATTACK1 + 6:
                return 6;
            case ATTACK2, ATTACK3, ATTACK2 + 6, ATTACK3 + 6:
                return 8;
            default:
                return 2;
        }
    }
}