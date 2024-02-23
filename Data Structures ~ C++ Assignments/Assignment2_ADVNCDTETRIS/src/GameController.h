#ifndef PA2_GAMECONTROLLER_H
#define PA2_GAMECONTROLLER_H

#include "BlockFall.h"

using namespace std;

class GameController {
public:
    bool play(BlockFall &game, const string &commands_file); // Function that implements the gameplay
    void putIt(BlockFall& block, int x, int y);
    void displayActiveBlock(BlockFall& game, int xOffset, int yOffset);
    bool isCollision(BlockFall& game, int x, int y);
    void applyGravity(BlockFall& game);
    void clearing(BlockFall& game);
    int gameover = 0;
    bool powerDetection(BlockFall& game);
    void applyPowerDetection(BlockFall& game);
    bool isShapeInGrid(const std::vector<std::vector<int>>& grid, const std::vector<std::vector<bool>>& shape, size_t row, size_t col);
};


#endif //PA2_GAMECONTROLLER_H
