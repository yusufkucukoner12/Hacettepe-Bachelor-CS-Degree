#include "Sector.h"
#include <cmath>

// Constructor implementation

Sector::Sector(int x, int y, int z) : x(x), y(y), z(z), left(nullptr), right(nullptr), parent(nullptr), color(RED) {
        // TODO: Calculate the distance to the Earth, and generate the sector code
        std::string xR;
        if (0 > x) {
            xR = "L";
        } else if (0 < x) {
            xR = "R";
        } else {
            xR = "S";  
        }
        std::string yR;
        if (0 > y) {
            yR = "D";
        } else if (0 < y) {
            yR = "U";
        } else {
            yR = "S";  
        }
        std::string zR;
        if (0 > z) {
            zR = "B";
        } else if (0  < z) {
            zR = "F";
        } else {
            zR = "S";  
        }
        
        double number = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
        int downnumber = static_cast<int>(floor(number));
        distance_from_earth = number;
        sector_code = std::to_string(downnumber) + xR + yR + zR;
}

Sector::~Sector() {
    // TODO: Free any dynamically allocated memory if necessary
}

Sector& Sector::operator=(const Sector& other) {
    this->color = other.color;
    this->left = other.left;
    this->right = other.right;
    this->x = other.x;
    this->y = other.y;
    this->z = other.z;
    this->distance_from_earth = other.distance_from_earth;
    this->sector_code = other.sector_code;
    this->parent = other.parent;
    return *this;
}

bool Sector::operator==(const Sector& other) const {
    return (x == other.x && y == other.y && z == other.z);
}

bool Sector::operator!=(const Sector& other) const {
    return !(*this == other);
}
