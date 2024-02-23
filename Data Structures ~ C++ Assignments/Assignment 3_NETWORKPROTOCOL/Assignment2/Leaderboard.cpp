#include "Leaderboard.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <ctime>
void Leaderboard::insert_new_entry(LeaderboardEntry * new_entry) {
    size++;
    if(head_leaderboard_entry == nullptr){
        head_leaderboard_entry = new_entry;
    }
    else{
        
        LeaderboardEntry* current = head_leaderboard_entry;
        LeaderboardEntry* insertNext = head_leaderboard_entry;
        if(new_entry->score > head_leaderboard_entry->score){
            new_entry->next_leaderboard_entry = head_leaderboard_entry;
            head_leaderboard_entry = new_entry;
        }
        else{
            while(current != nullptr && current->score >= new_entry->score){
                insertNext = current;
                current = current->next_leaderboard_entry;
            }
            insertNext->next_leaderboard_entry = new_entry;
            new_entry->next_leaderboard_entry = current;
        }
    }

    if(size > 10){
        remove_Last(head_leaderboard_entry);
    }
}

void Leaderboard::write_to_file(const string& filename) {
    ofstream MyFile(filename);
    if(!MyFile.is_open()){
        std::cout << "" << std::endl;
        return;
    }
    LeaderboardEntry* nextNode = head_leaderboard_entry;
    int a = 0;
    while(nextNode != nullptr){
        a++;
        MyFile << nextNode->score <<  " " <<  nextNode->last_played << " " << nextNode->player_name << "\n";
        nextNode = nextNode->next_leaderboard_entry;
    }
    MyFile.close();
}

void Leaderboard::read_from_file(const string& filename) {
    fstream file(filename);
    if(!file.is_open()){
        return;
    }
    string line;
    while(getline(file,line)){
            istringstream ss(line);
            int score; ss >> score;
            std::time_t time; ss >> time;
            std::string name; ss >> name;
            std::string const realName = name;

            LeaderboardEntry* addIt = new LeaderboardEntry(score,time,realName);
            insert_new_entry(addIt);
    }
    
    
}


void Leaderboard::print_leaderboard() {
    LeaderboardEntry* nextNode = head_leaderboard_entry;
    int a = 0;
    std::cout << "Leaderboard" << "\n" << "-----------" << "\n";
    while(nextNode != nullptr){
        a++;
        std::cout << a << ". " << nextNode->player_name << " " << nextNode->score << " " << timeConverter(nextNode->last_played) << "\n";
        nextNode = nextNode->next_leaderboard_entry;
    }
    std::cout << "\n\n\n";
}

void Leaderboard::remove_Last(LeaderboardEntry * head){
    LeaderboardEntry* nextNode = head_leaderboard_entry;
    LeaderboardEntry* willBeDeleted;
    while(nextNode->next_leaderboard_entry != nullptr){
        willBeDeleted = nextNode;
        nextNode = nextNode->next_leaderboard_entry;
    }
    delete nextNode;
    nextNode = nullptr;
    willBeDeleted->next_leaderboard_entry = nullptr;
}
std::string Leaderboard::timeConverter(time_t timestamp){
    std::tm* timeinfo = std::localtime(&timestamp);

    char buffer[80];
    std::strftime(buffer, sizeof(buffer), "%H:%M:%S/%d.%m.%Y", timeinfo);

    return std::string(buffer);
}

Leaderboard::~Leaderboard() {
   while (head_leaderboard_entry != nullptr) {
        LeaderboardEntry* temp = head_leaderboard_entry;  
        head_leaderboard_entry = head_leaderboard_entry->next_leaderboard_entry;  
        delete temp;     
        temp = nullptr;   
   }
    head_leaderboard_entry = nullptr;  
}


