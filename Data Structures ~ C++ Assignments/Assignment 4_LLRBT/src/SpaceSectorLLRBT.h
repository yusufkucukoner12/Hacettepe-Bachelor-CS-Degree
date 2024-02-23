#ifndef SPACESECTORLLRBT_H
#define SPACESECTORLLRBT_H

#include "Sector.h"
#include <iostream>
#include <fstream>  
#include <sstream>
#include <vector>

class SpaceSectorLLRBT {
public:
    Sector* root;
    SpaceSectorLLRBT();
    ~SpaceSectorLLRBT();
    void readSectorsFromFile(const std::string& filename);
    void insertSectorByCoordinates(int x, int y, int z);
    void displaySectorsInOrder();
    void displaySectorsPreOrder();
    void displaySectorsPostOrder();
    std::vector<Sector*> getStellarPath(const std::string& sector_code);
    void printStellarPath(const std::vector<Sector*>& path);
    std::vector<std::string> splitTheText(std::string inputString,char discriminator);
    Sector* put(Sector* root, Sector* newItem);
    Sector* rotateLeft(Sector*& h);
    Sector* rotateRight(Sector*& h);
    void flipColors(Sector*& h);
    void displaySectorsInOrderHelper(Sector* root);
    void displaySectorsPreOrderHelper(Sector* root);
    void displaySectorsPostOrderHelper(Sector* root);
    void findThePath( std::vector<Sector*> &path, Sector* copy);
    Sector* returnTheCopy(Sector* root, std::string& sector_code);
    void deleteTree(Sector* root); 



};

#endif // SPACESECTORLLRBT_H
