#include "BlockFall.h"
#include <fstream>
#include <iostream>

BlockFall::BlockFall(string grid_file_name, string blocks_file_name, bool gravity_mode_on, const string &leaderboard_file_name, const string &player_name) : gravity_mode_on(
        gravity_mode_on), leaderboard_file_name(leaderboard_file_name), player_name(player_name) {
    initialize_grid(grid_file_name);
    read_blocks(blocks_file_name);
    leaderboard.read_from_file(leaderboard_file_name);
}

void BlockFall::read_blocks(const string &input_file) {
    ifstream file(input_file);
    if (!file.is_open()) {
        cerr << "Error opening file: " << input_file << endl;

    }

    vector<vector<bool>> blocks;
    vector<bool> current_block_row;

    char ch;
    while (file.get(ch)) {
        if (ch == '0' || ch == '1') {
            current_block_row.push_back(ch == '1');
        } else if (ch == '\n') {
            if(current_block_row.size() == 0){
                continue;
            }
            blocks.push_back(current_block_row);
            current_block_row.clear();  
        }
        else if(ch == ']')
        {   
            blocks.push_back(current_block_row);
            current_block_row.clear();  
            Block* new_block = new Block();
            new_block->shape = blocks;
            blocks.clear();

            createCircularLL(new_block); 
    
            if(initial_block == nullptr){
                initial_block = new_block;
                active_rotation = new_block;
            }
            else{
                active_rotation->next_block = new_block;
                active_rotation = new_block;
            }
        }
    }
    Block* current = initial_block;
    while (current->next_block->next_block != nullptr) {
        current = current->next_block;
    }
    power_up = current->next_block->shape;
    active_rotation = initial_block;

    Block* powerUpBlock = current->next_block;
    Block* powerUpRotation = current->next_block->right_rotation;

    while (powerUpRotation != nullptr && powerUpRotation != powerUpBlock) {
        Block* nextRotation = powerUpRotation->right_rotation;
        delete powerUpRotation;
        powerUpRotation = nextRotation;
    }
    delete powerUpBlock;
    current->next_block = nullptr;
}

std::vector<std::vector<bool>> BlockFall::rotateRight(std::vector<std::vector<bool>> &willBeRotated){
    std::vector<std::vector<bool>> rotated(willBeRotated[0].size(), std::vector<bool>(willBeRotated.size(), false));
    
    if(willBeRotated.size() == 1){
        for (size_t i = 0; i < willBeRotated[0].size(); ++i) {
            rotated[i][0] = willBeRotated[0][i];
        }
    }
    else{
        for (size_t i = 0; i < willBeRotated.size(); ++i) {
            for (size_t j = 0; j < willBeRotated[i].size(); ++j) {
                rotated[j][willBeRotated.size() - i - 1] = willBeRotated[i][j];
            }
        }
    }    

    return rotated;
}

void BlockFall::createCircularLL(Block* original1){
    Block* newPointa = original1;

    for(int i = 0 ; i<3;i++){
        Block* newblock = new Block;
        newblock->shape = rotateRight(newPointa->shape);
        newPointa->right_rotation = newblock;
        newblock->left_rotation = newPointa;
        newPointa = newblock;
    }
    original1->left_rotation = newPointa;
    newPointa->right_rotation = original1;
}

void BlockFall::initialize_grid(const string &input_file) {
    ifstream file(input_file);
    if (!file.is_open()) {
        cout << "Error opening file: " << input_file << endl;
        return;
    }
    char ch;
    vector<int> grid_row;
    while (file.get(ch)) {
        if(ch == '0' || ch == '1'){
            int digit = std::stoi(std::string(1, ch));
            grid_row.push_back(digit);
        }
        else if(ch == '\n'){
            grid.push_back(grid_row);
            grid_row.clear();
        }
    }
    if(grid_row.size() != '0'){
            grid.push_back(grid_row);
            grid_row.clear();
    }
    cols = grid[0].size();
    rows = grid.size();
}

void BlockFall::printGrid(){
    for(int i = 0 ; i<grid.size() ; i++){
        for(int j = 0 ; j<cols;j++ ){
            std::cout << (grid[i][j] == 1 ? occupiedCellChar : unoccupiedCellChar);
        }
        cout << endl;
    }
}


BlockFall::~BlockFall() {
    Block* currentBlock = initial_block;
    while (currentBlock != nullptr) {
        Block* currentRotation = currentBlock->right_rotation;
        while (currentRotation != nullptr && currentRotation != currentBlock) {
            Block* nextRotation = currentRotation->right_rotation;
            delete currentRotation;
            currentRotation = nextRotation;
        }

        Block* nextBlock = currentBlock->next_block;
        delete currentBlock;
        currentBlock = nextBlock;
    }
}
