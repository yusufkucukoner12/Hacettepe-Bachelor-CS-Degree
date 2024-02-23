#include "SpaceSectorLLRBT.h"
#include <cmath>

using namespace std;

SpaceSectorLLRBT::SpaceSectorLLRBT() : root(nullptr) {}

void SpaceSectorLLRBT::readSectorsFromFile(const std::string& filename) {
    // TODO: read the sectors from the input file and insert them into the LLRBT sector map
    // according to the given comparison critera based on the sector coordinates.

    ifstream file(filename);
    if(!file.is_open()){
        cerr << "Error opening file: " << filename << endl;
    }
    std::string line;
    getline(file,line);
    while(getline(file,line)){
        vector<string> splittedText = splitTheText(line,',');
        insertSectorByCoordinates(stof(splittedText[0]),stof(splittedText[1]),stof(splittedText[2]));
    }
}

SpaceSectorLLRBT::~SpaceSectorLLRBT() {
    deleteTree(root);
}

void SpaceSectorLLRBT::deleteTree(Sector* root) {
    if (root != nullptr) {
        deleteTree(root->left);
        deleteTree(root->right);
        delete root;
    }
}

void SpaceSectorLLRBT::insertSectorByCoordinates(int x, int y, int z) {
    // TODO: Instantiate and insert a new sector into the space sector LLRBT map 
    // according to the coordinates-based comparison criteria.
    Sector* newItem = new Sector(x,y,z);
    root = put(root, newItem);
    root->color = BLACK;
}
Sector* SpaceSectorLLRBT::put(Sector* root, Sector* newItem){
    if(root == nullptr){
        return newItem;
    }
    else if ((newItem->x < root->x || (newItem->x == root->x && newItem->y < root->y) || (newItem->x == root->x && newItem->y == root->y && newItem->z < root->z)))
    {   
        root->left = put(root->left, newItem);  
        root->left->parent = root;  
    }
    else if ((newItem->x > root->x) || (newItem->x == root->x && newItem->y > root->y) || (newItem->x == root->x && newItem->y== root->y && newItem->z > root->z))
    {
       root->right = put(root->right, newItem);
       root->right->parent = root;  

    }
    if (root->right != nullptr) {
        if ((root->right->color == RED) && ((root->left == nullptr) || (root->left->color == BLACK))) {
            root = rotateLeft(root);
        }
    }
    if (root->left != nullptr && root->left->left != nullptr) {
        if ((root->left->color == RED) && (root->left->left->color == RED)) {
            root = rotateRight(root);
        }
    }
    if (root->left != nullptr && root->right != nullptr) {
        if ((root->left->color == RED) && (root->right->color == RED)) {
            flipColors(root);
        }
    }

    return root;
}
void SpaceSectorLLRBT::flipColors(Sector*& h){
    h->color = RED;
    h->left->color = BLACK;
    h->right->color = BLACK;
}

Sector* SpaceSectorLLRBT::rotateLeft(Sector*& h) {
        Sector* x = h->right;
        x->parent = h->parent;
        h->parent = x;
        h->right = x->left;
        x->left = h;
        x->color = h->color;
        h->color = RED;
        if(h->right != nullptr){
            h->right->parent = h;
        }
        return x;
}
Sector* SpaceSectorLLRBT::rotateRight(Sector*& h) {
        Sector* x = h->left;
        x->parent = h->parent;
        h->parent = x;
        h->left = x->right;
        x->right = h;
        x->color = h->color;
        h->color = RED;
        if(h->left != nullptr){
            h->left->parent = h;
        }
        return x;
}
void SpaceSectorLLRBT::displaySectorsInOrder() {
    cout << "Space sectors inorder traversal:" << endl;
    
    displaySectorsInOrderHelper(root);
    
    cout << endl;

}
void SpaceSectorLLRBT::displaySectorsInOrderHelper(Sector* root) {
    if (root != nullptr) {
        displaySectorsInOrderHelper(root->left);

        if(root->color){
            std::cout << "RED sector: " << root->sector_code << endl;
        }
        else{
            std::cout << "BLACK sector: " << root->sector_code << endl;
        }

        displaySectorsInOrderHelper(root->right);
    }
}

void SpaceSectorLLRBT::displaySectorsPreOrder() {
    cout << "Space sectors preorder traversal:" << endl;
    displaySectorsPreOrderHelper(root);
    
    cout << endl;

}
void SpaceSectorLLRBT::displaySectorsPreOrderHelper(Sector* root) {
    if (root != nullptr) {
        if(root->color){
            std::cout << "RED sector: " << root->sector_code << endl;
        }
        else{
            std::cout << "BLACK sector: " << root->sector_code << endl;
        }

        displaySectorsPreOrderHelper(root->left);

        displaySectorsPreOrderHelper(root->right);
    }
}

void SpaceSectorLLRBT::displaySectorsPostOrder() {
    cout << "Space sectors postorder traversal:" << endl;
    displaySectorsPostOrderHelper(root);

    cout << endl;
}

void SpaceSectorLLRBT::displaySectorsPostOrderHelper(Sector* root) {
    if (root != nullptr) {
        displaySectorsPostOrderHelper(root->left);

        displaySectorsPostOrderHelper(root->right);

        if(root->color){
            std::cout << "RED sector: " << root->sector_code << endl;
        }
        else{
            std::cout << "BLACK sector: " << root->sector_code << endl;
        }
    }
}


std::vector<Sector*> SpaceSectorLLRBT::getStellarPath(const std::string& sector_code) {

    std::vector<Sector*> path;
    string g = sector_code;
    string a = "0SSS";

        std::vector<Sector*> path1, path2,result;

        Sector* copy = returnTheCopy(root,g);
        Sector* copy1 = returnTheCopy(root,a);

        


        if(copy == nullptr || copy1 == nullptr){
            return path;
        }
        findThePath(path1,copy);
        findThePath(path2,copy1);
        int j = 0;
        int k = 99999;
        for(; j<path2.size();j++){ 
            for(int i = 0 ; i<path1.size() ; i++){
                if(path1[i] == path2[j]){
                    k = i;
                    break;
                }
            }
            if(k != 99999){
                break;
            }
        }
        for(int i = 0;i<j;i++){
            path.push_back(path2[i]);
        }   

        for(;k>=0;k--){
            path.push_back(path1[k]);
        }

        for(int i = 0; i < result.size() ; i++){
            path.push_back(result[i]);
        }

        return path;
}

void SpaceSectorLLRBT::printStellarPath(const std::vector<Sector*>& path) {
    if(path.size() > 0){
        cout << "The stellar path to Dr. Elara: ";
        for(int i = 0 ; i < path.size() - 1  ; i++){
            cout << path[i]->sector_code << "->";
        }
        cout << path[path.size()-1]->sector_code << endl;
    }
    else{
        cout << "A path to Dr. Elara could not be found." << endl;
    }
}
vector<string> SpaceSectorLLRBT::splitTheText(string inputString,char discriminator){
    std::istringstream iss(inputString);
    std::vector<std::string> tokens; 
    std::string token;
    while (std::getline(iss, token, discriminator)) {
        tokens.push_back(token); 
    }
    return tokens;
}
void SpaceSectorLLRBT::findThePath(std::vector<Sector*> &path, Sector* copy){
    if(copy->parent == nullptr){
        path.push_back(copy);
        return;
    }
    else{
        path.push_back(copy);
        findThePath(path,copy->parent);
    } 
}
Sector* SpaceSectorLLRBT::returnTheCopy(Sector* root, string& sector_code){
    if(root == nullptr){
        return nullptr;
    }

    Sector* left = returnTheCopy(root->left, sector_code);
    if(left != nullptr){
        return left;
    }
    if(root->sector_code == sector_code){
        return root;
    }
    Sector* right = returnTheCopy(root->right, sector_code);
    if(right != nullptr){
        return right;
    }
}
