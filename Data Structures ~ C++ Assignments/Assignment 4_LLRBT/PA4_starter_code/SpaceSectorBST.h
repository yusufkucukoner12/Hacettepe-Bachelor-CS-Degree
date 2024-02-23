#ifndef SPACESECTORBST_H
#define SPACESECTORBST_H

#include <iostream>
#include <fstream>  
#include <sstream>
#include <vector>

#include "Sector.h"
using namespace std;

class SpaceSectorBST {
  
public:
    Sector *root;
    SpaceSectorBST();
    ~SpaceSectorBST();
    void readSectorsFromFile(const std::string& filename); 
    void insertSectorByCoordinates(int x, int y, int z);
    void deleteSector(const std::string& sector_code);
    void displaySectorsInOrder();
    void displaySectorsPreOrder();
    void displaySectorsPostOrder();
    std::vector<Sector*> getStellarPath(const std::string& sector_code);
    void printStellarPath(const std::vector<Sector*>& path);
    vector<string> splitTheText(string inputString,char discriminator);
    Sector* findTheLocation(Sector* root, Sector* newItem);
    void displaySectorsInOrderHelper(Sector* root);
    void displaySectorsPreOrderHelper(Sector* root);
    void displaySectorsPostOrderHelper(Sector* root);
    void processLeftmost(Sector*& nodePtr, string& willDeleted, int& x, int& y, int& z, double& distance);
    void deleteSectorHelper(Sector*& root, const std::string& sector_code,Sector* copy);
    Sector* returnTheCopy(Sector* root, string& sector_code);
    void findThePath(std::vector<Sector*> &path, Sector* copy);
    void deleteTree(Sector* root);
    void deleteNode(Sector *&willBeDeleted);

    


};

#endif // SPACESECTORBST_H
