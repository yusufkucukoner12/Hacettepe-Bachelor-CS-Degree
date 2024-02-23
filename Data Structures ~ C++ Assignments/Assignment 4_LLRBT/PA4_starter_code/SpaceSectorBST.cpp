#include "SpaceSectorBST.h"


using namespace std;

SpaceSectorBST::SpaceSectorBST() : root(nullptr) {}

SpaceSectorBST::~SpaceSectorBST() {
    deleteTree(root);
}

void SpaceSectorBST::deleteTree(Sector* root) {
    if (root != nullptr) {
        deleteTree(root->left);
        deleteTree(root->right);
        delete root;
    }
}

void SpaceSectorBST::readSectorsFromFile(const std::string& filename) {
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

void SpaceSectorBST::insertSectorByCoordinates(int x, int y, int z) {
    Sector* newItem = new Sector(x, y, z);
    root = findTheLocation(root, newItem);  
}

Sector* SpaceSectorBST::findTheLocation(Sector* root, Sector* newItem) {
    if(root == nullptr){
        return newItem;
    }
    else if ((newItem->x < root->x || (newItem->x == root->x && newItem->y < root->y) || (newItem->x == root->x && newItem->y == root->y && newItem->z < root->z)))
    {   
        root->left = findTheLocation(root->left, newItem);  
        root->left->parent = root;  
    }
    else if ((newItem->x > root->x) || (newItem->x == root->x && newItem->y > root->y) || (newItem->x == root->x && newItem->y== root->y && newItem->z > root->z))
    {
       root->right = findTheLocation(root->right, newItem);
       root->right->parent = root;
    }
    return root; 
}

void SpaceSectorBST::deleteSector(const std::string& sector_code) {
    string a = sector_code;
    Sector* copy = returnTheCopy(root,a);
    if(copy != nullptr){
        deleteSectorHelper(root,sector_code,copy);
    }
}

void SpaceSectorBST::deleteSectorHelper(Sector*& root, const std::string& sector_code,Sector* copy){
    if(root == nullptr)
        return;
    else if (sector_code == root->sector_code)
    {
        deleteNode(root);   
    }
    else if ((copy->x < root->x || (copy->x == root->x && copy->y < root->y) || (copy->x == root->x && copy->y == root->y && copy->z < root->z)))
    {
        deleteSectorHelper(root->left,sector_code,copy);
    }
    else if ((copy->x > root->x) || (copy->x == root->x && copy->y > root->y) || (copy->x == root->x && copy->y== root->y && copy->z > root->z))
    {
        deleteSectorHelper(root->right,sector_code,copy);
    }
}

void SpaceSectorBST::deleteNode(Sector *&willBeDeleted){
    if(willBeDeleted->left == nullptr && willBeDeleted->right == nullptr){
        delete willBeDeleted;
        willBeDeleted = nullptr;
    }
    else if (willBeDeleted->right == nullptr)
    {
        Sector* delPtr = willBeDeleted;
        willBeDeleted = willBeDeleted->left;
        delPtr->left = nullptr;
        delete delPtr;
    }
    else if (willBeDeleted->left == nullptr)
    {
        Sector* delPtr = willBeDeleted;
        willBeDeleted = willBeDeleted->right;
        delPtr->right = nullptr;
        delete delPtr;
    }
    else{
        processLeftmost(willBeDeleted->right,willBeDeleted->sector_code,willBeDeleted->x,willBeDeleted->y,willBeDeleted->z,willBeDeleted->distance_from_earth);
    }
    
}
void SpaceSectorBST::processLeftmost(Sector*& nodePtr, string& sektorcod, int& x, int& y, int& z, double& distance) {
    if (nodePtr->left == nullptr) {
        sektorcod = nodePtr->sector_code;;
        x = nodePtr->x; 
        y = nodePtr->y;
        z = nodePtr->z;
        distance = nodePtr->distance_from_earth;

        Sector* delPtr = nodePtr;
        if(nodePtr->right != nullptr){
            
        }
        nodePtr = nodePtr->right;
        delPtr->right = nullptr;

        delete delPtr;
    } else {
        processLeftmost(nodePtr->left,sektorcod,x,y,z,distance);
    }
}



Sector* SpaceSectorBST::returnTheCopy(Sector* root, string& sector_code) {
    if (root == nullptr) {
        return nullptr;
    }

    Sector* left = returnTheCopy(root->left, sector_code);
    if (left != nullptr) {
        return left;
    }
    if (root->sector_code == sector_code) {
        return root;
    }
    Sector* right = returnTheCopy(root->right, sector_code);
    if (right != nullptr) {
        return right;
    }
}

void SpaceSectorBST::displaySectorsInOrder() {
    cout << "Space sectors inorder traversal:" << endl;
    displaySectorsInOrderHelper(root);
    
    cout << endl;

}
void SpaceSectorBST::displaySectorsInOrderHelper(Sector* root) {
    if (root != nullptr) {
        displaySectorsInOrderHelper(root->left);

        std::cout << root->sector_code << endl;

        displaySectorsInOrderHelper(root->right);
    }
}

void SpaceSectorBST::displaySectorsPreOrder() {
    cout << "Space sectors preorder traversal:" << endl;

    displaySectorsPreOrderHelper(root);
    
    cout << endl;

}
void SpaceSectorBST::displaySectorsPreOrderHelper(Sector* root) {
    if (root != nullptr) {
        std::cout << root->sector_code << endl;

        displaySectorsPreOrderHelper(root->left);

        displaySectorsPreOrderHelper(root->right);
    }
}

void SpaceSectorBST::displaySectorsPostOrder() {
    cout << "Space sectors postorder traversal:" << endl;

    displaySectorsPostOrderHelper(root);

    cout << endl;
}

void SpaceSectorBST::displaySectorsPostOrderHelper(Sector* root) {
    if (root != nullptr) {
        displaySectorsPostOrderHelper(root->left);

        displaySectorsPostOrderHelper(root->right);

        std::cout << root->sector_code << endl;
    }
}


std::vector<Sector*> SpaceSectorBST::getStellarPath(const std::string& sector_code) {
    std::vector<Sector*> path;
    std::vector<Sector*> path1;

    string a = sector_code;
    Sector* copy = returnTheCopy(root, a);
    if(copy != nullptr){
        findThePath(path1,copy);
    }
    for(int i = path1.size() - 1 ; i >= 0 ; i--){
        path.push_back(path1[i]);
    }
    
    return path;
}
void SpaceSectorBST::findThePath(std::vector<Sector*> &path, Sector* copy){
    if(copy->parent == nullptr){
        path.push_back(copy);
        return;
    }
    else{
        path.push_back(copy);
        findThePath(path,copy->parent);
    }
}

void SpaceSectorBST::printStellarPath(const std::vector<Sector*>& path) {
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
    
    // TODO: Print the stellar path obtained from the getStellarPath() function 
    // to STDOUT in the given format.
}
vector<string> SpaceSectorBST::splitTheText(string inputString,char discriminator){
    std::istringstream iss(inputString);
    std::vector<std::string> tokens; 
    std::string token;
    while (std::getline(iss, token, discriminator)) {
        tokens.push_back(token); 
    }
    return tokens;
}