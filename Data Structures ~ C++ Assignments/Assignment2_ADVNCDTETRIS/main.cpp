#include "BlockFall.h"
#include "GameController.h"
#include <iostream>

int main(int argc, char **argv) {

    // Create a BlockFall instance
    string gravity_mode(argv[4]);
    BlockFall game(argv[1], argv[2], (gravity_mode == "GRAVITY_ON"), argv[5], argv[6]);

    // Create a GameController instance 
    GameController controller;
    

    // Play 
    controller.play(game, argv[3]);
    

    return 0;
}