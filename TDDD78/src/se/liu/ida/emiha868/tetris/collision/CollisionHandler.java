package se.liu.ida.emiha868.tetris.collision;

import se.liu.ida.emiha868.tetris.board.Board;

/**
 * This interface follows the State Pattern design, the collisionshandling
 * is based upon the Powerup the player is having..
 */
public interface CollisionHandler
{
    public boolean hasCollision(Board board);
    public String getDescription();
}
