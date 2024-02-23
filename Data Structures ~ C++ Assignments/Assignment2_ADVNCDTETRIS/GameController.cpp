#include "GameController.h"
#include <fstream>
#include <iostream>

bool GameController::play(BlockFall& game, const string& commands_file){
    ifstream file(commands_file);
    if (!file.is_open()) {
        
    }
    
    int x = 0;
    int y = 0;
    string line;
    while(getline(file,line) && gameover == 0){
        if(line == "PRINT_GRID"){
            displayActiveBlock(game,x,y);
        }
        else if (line == "MOVE_RIGHT"){
            if(!isCollision(game,x+1,y)){
                x++;
            }
        }
        else if (line == "MOVE_LEFT")
        {   
            if(!isCollision(game,x-1,y)){
                x--;
            }
        }
        else if(line == "ROTATE_RIGHT"){
            game.active_rotation = game.active_rotation->right_rotation;
            if(isCollision(game,x,y)){
                game.active_rotation = game.active_rotation->left_rotation;
            } 
        }
        else if(line == "ROTATE_LEFT"){
            game.active_rotation = game.active_rotation->left_rotation;
            if(isCollision(game,x,y)){
                game.active_rotation = game.active_rotation->right_rotation;
            } 
        }
        else if(line == "GRAVITY_SWITCH"){
            game.gravity_mode_on = !game.gravity_mode_on;
            if(game.gravity_mode_on){
                applyGravity(game);

            }
        }
        else if(line == "DROP"){
            while(!isCollision(game,x,y+1)){
                    y++;
            }
            putIt(game,x,y);
            if(game.gravity_mode_on){
                applyGravity(game);
            }
            if(powerDetection(game)){
                applyPowerDetection(game);
            }
            else{
                clearing(game);
            }
            
            x = 0;
            y = 0;
            if(game.initial_block->next_block == nullptr){
                gameover = 1;
            }
            else{
                game.active_rotation = game.initial_block->next_block;
                game.initial_block = game.initial_block->next_block;
            }
            if(isCollision(game,x,y)){
               gameover = 2; 
            }
        }
    }
    std::time_t currentTime = std::time(nullptr);
    LeaderboardEntry* newPerson = new LeaderboardEntry(game.current_score,currentTime,game.player_name);
    game.leaderboard.insert_new_entry(newPerson);

    game.leaderboard.write_to_file(game.leaderboard_file_name);    

    if(gameover == 1){
        std::cout << "YOU WIN!" << "\n" << "No more blocks." << "\n" << "Final grid and score:\n\n";
        std::cout << "Score: " << game.current_score << std::endl;
        std::cout << "High Score: " << game.leaderboard.head_leaderboard_entry->score << std::endl;
        game.printGrid();
        std::cout << std::endl;
        game.leaderboard.print_leaderboard();
        return true;

    }
    if(gameover == 0){
        std::cout << "GAME FINISHED!" << "\n" << "No more commands." << "\n" << "Final grid and score:\n\n";
        std::cout << "Score: " << game.current_score << std::endl;
        std::cout << "High Score: " << game.leaderboard.head_leaderboard_entry->score << std::endl;
        game.printGrid();
        std::cout << std::endl;
        game.leaderboard.print_leaderboard();
        return true;
        
    }
    if(gameover == 2){
        std::cout << "GAME OVER!" << "\n" << "Next block that couldn't fit:\n";
        for(int i = 0 ; i < game.active_rotation->shape.size() ; i++){
            for(int j = 0 ; j < game.active_rotation->shape[i].size() ; j++){
                std::cout << (game.active_rotation->shape[i][j] == 1 ? occupiedCellChar : unoccupiedCellChar);
            }
            std::cout << std::endl;
        }
        std::cout << std::endl;
        std::cout <<  "Final grid and score:\n\n";
        std::cout << "Score: " << game.current_score << std::endl;
        std::cout << "High Score: " << game.leaderboard.head_leaderboard_entry->score << std::endl;
        game.printGrid();
        std::cout << std::endl;
        game.leaderboard.print_leaderboard();
        return false;
        
    }
    return false;
}


bool GameController::isCollision(BlockFall& game, int x, int y){
    
    if(x<0){
        return true;
    }
    if(game.cols < x+game.active_rotation->shape[0].size()){
        return true;
    }
    
    if((game.grid.size() - game.active_rotation->shape.size() < y)){
        return true;
    }
    
    for(int i = 0 ; i<game.active_rotation->shape.size() ; i++){
        for(int j = 0; j<game.active_rotation->shape[i].size() ; j++){
            if(game.active_rotation->shape[i][j] && game.grid[i+y][j+x] == 1){
                return true;
            }
        }
    }
    return false;
}
void GameController::clearing(BlockFall& game){
    bool write = false;
    std::vector<std::vector<int>> beforeGrid = game.grid;
    for(int i = 0; i<game.grid.size(); ++i){
        bool clear = true;
        for(int j = 0 ; j<game.grid[i].size() ; j++){
            if(game.grid[i][j]){
                
            }
            else{
                clear = false;
            }
        }
        if(clear){
            game.grid.erase(game.grid.begin() + i);
            std::vector<int> newVector(game.grid[i].size(), 0);
            game.grid.insert(game.grid.begin(), newVector);

            game.current_score = game.current_score + game.grid[i].size();
            write = true;
            

        }
    }
    if(write){
        std::cout << "Before cleaning: " << std::endl;
        for(int i = 0 ; i<beforeGrid.size() ; i++){
            for(int j = 0 ; j<beforeGrid[i].size() ;j++ ){
                 std::cout << (beforeGrid[i][j] == 1 ? occupiedCellChar : unoccupiedCellChar);
            }
        cout << endl;
        }
        std::cout << "\n\n";
    }
}

void GameController::applyGravity(BlockFall& game){
    for (int i = game.grid.size()-2; i >= game.active_rotation->shape.size(); i--) {
        for (int j = 0; j < game.grid[i].size(); j++) {
            if (game.grid[i][j] == 1 && (game.grid[i + 1][j]) == 0) {
                int a = 1;
                game.grid[i][j] = 0;
                while(i + a < game.grid.size() && game.grid[i+a][j] == 0){
                    a++;
                }
                game.grid[i + a - 1][j] = 1;
            } 
        }
    }
    if(powerDetection(game)){
        applyPowerDetection(game);
    }
    else{
        clearing(game);
    }
}
void GameController::displayActiveBlock(BlockFall& game, int xOffset, int yOffset) {
        std::vector<std::vector<int>> tempGrid = game.grid;

    
        for (size_t i = 0; i < game.active_rotation->shape.size(); ++i) {
            for (size_t j = 0; j < game.active_rotation->shape[i].size(); ++j) {
                if (game.active_rotation->shape[i][j] && i + yOffset < tempGrid.size() && j + xOffset < tempGrid[i].size()) {
                    tempGrid[i + yOffset][j + xOffset] = true;
                }
            }
        }
        std::cout << "Score: " << game.current_score << std::endl;
        std::cout << "High Score: " << game.leaderboard.head_leaderboard_entry->score << std::endl;

        for(int i = 0 ; i<tempGrid.size() ; i++){
            for(int j = 0 ; j<game.cols;j++ ){
                 std::cout << (tempGrid[i][j] == 1 ? occupiedCellChar : unoccupiedCellChar);
            }
            cout << endl; 
        }
        cout << "\n\n"; 
}
void GameController::putIt(BlockFall& block, int x, int y) {
    for (int i = 0; i < block.active_rotation->shape.size(); ++i) {
        for (int j = 0; j < block.active_rotation->shape[i].size(); ++j) {
            if (block.active_rotation->shape[i][j]) {
                block.grid[i + y][j+x] = 1;
                block.current_score = block.current_score + y;
            }
        }
    }
}
bool GameController::powerDetection(BlockFall& game){
    for (int i = 0; i <= game.grid.size()-game.power_up.size(); i++) {
        for (int j = 0; j <= game.grid[0].size()-game.power_up[0].size(); j++) {
            bool check = true;
            for (int k = 0; k < game.power_up.size(); k++) {
                for (int l = 0; l < game.power_up[0].size(); l++) {
                    if (game.power_up[k][l] != game.grid[i+k][j+l] ) {
                        check = false;
                    }
                }
            }
            if(check){
                return true;
            }
        }
    }
    return false;
}
void GameController::applyPowerDetection(BlockFall& game){
    std::cout << "Before cleaning:" << std::endl;
    for(int i = 0 ; i<game.grid.size() ; i++){
        for(int j = 0 ; j<game.grid[i].size() ;j++ ){
             std::cout << (game.grid[i][j] == 1 ? occupiedCellChar : unoccupiedCellChar);
        }
    cout << endl;
    }
    game.current_score += 1000;
    for(int i = 0; i<game.grid.size();i++){
        for(int j = 0; j<game.grid[i].size(); j++){
            if(game.grid[i][j] == 1){
                game.current_score++;
            }
            game.grid[i][j] = 0;
        }
    }
    std::cout << "\n\n";
}