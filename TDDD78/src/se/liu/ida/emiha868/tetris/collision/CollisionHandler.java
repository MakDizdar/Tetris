package se.liu.ida.emiha868.tetris.collision;

import se.liu.ida.emiha868.tetris.board.Board;

/**
 * This interface follows the State Pattern design, the collisionshandling
 * is based upon the Powerup the player is having.
 * changeBoard() is called whenever the falling
 * poly has fallen. Please read the comment in the
 * for further details interface.
 */
public interface CollisionHandler
{
    public boolean hasCollision(Board board);
    public String getDescription();
}
