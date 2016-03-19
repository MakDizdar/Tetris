package se.liu.ida.emiha868.tetris.collision;

import se.liu.ida.emiha868.tetris.board.Board;

public interface CollisionHandler
{
    /*I know that the changeBoard() pollutes the interface, but think of it as a listener, it is something that happens
    everytime a block has fallen and what actually happens is based on the powerup. It has nothing to do with collisionhandling,
    i agree, how to implement this so that it follows the state pattern I dont know, perhaps an abstract class?
     */
    public boolean hasCollision(Board board);
    public void changeBoard(Board board);
    public String getDescription();
}
