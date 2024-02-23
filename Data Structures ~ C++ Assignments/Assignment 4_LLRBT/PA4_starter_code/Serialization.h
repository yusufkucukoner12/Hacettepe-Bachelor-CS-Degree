#ifndef PA3_SERIALIZATION_H
#define PA3_SERIALIZATION_H


#include "json.hpp"
#include "Sector.h"
#include "KDT_Node.h"
#include "kNN_Data.h"

using nlohmann::json;

using namespace std;

json serializeSectorBST(const Sector& sector) {
    json sectorJson;
    sectorJson["x"] = sector.x;
    sectorJson["y"] = sector.y;
    sectorJson["z"] = sector.z;
    sectorJson["distance_from_earth"] = sector.distance_from_earth;
    sectorJson["sector_code"] = sector.sector_code;
    return sectorJson;
}

json serializeBST(const Sector* root) {
    if (root == nullptr) {
        return nullptr;
    }
    json bstJson;
    bstJson["node"] = serializeSectorBST(*root);
    bstJson["left"] = serializeBST(root->left);
    bstJson["right"] = serializeBST(root->right);
    return bstJson;
}


json serializeSectorLLRBT(const Sector& sector) {
    json sectorJson;
    sectorJson["x"] = sector.x;
    sectorJson["y"] = sector.y;
    sectorJson["z"] = sector.z;
    sectorJson["distance_from_earth"] = sector.distance_from_earth;
    sectorJson["sector_code"] = sector.sector_code;
    sectorJson["color"] = sector.color;
    return sectorJson;
}

json serializeLLRBT(const Sector* root) {
    if (root == nullptr) {
        return nullptr;
    }
    json bstJson;
    bstJson["node"] = serializeSectorLLRBT(*root);
    bstJson["left"] = serializeLLRBT(root->left);
    bstJson["right"] = serializeLLRBT(root->right);
    return bstJson;
}

json serializePoint(const Point & point) {
    json pointJson;
    pointJson["label"] = point.label;
    for (const auto& feature : point.features) {
        pointJson["features"].push_back(feature);
    }
    return pointJson;
}

json serializeDataset(const Dataset & data) {
    json dataJson;

    for (const auto& point : data.points) {
        dataJson["points"].push_back(serializePoint(point));
    }
    return dataJson;
}

json serializeKDNode(const KDTreeNode* kdNode) {
    json nodeJson;
    nodeJson["isLeaf"] = kdNode->isLeaf();
    
    if (kdNode->isLeaf()) {
        const kd_tree_leaf_node * tmp = static_cast<const kd_tree_leaf_node*>(kdNode);
        nodeJson["data"] = serializeDataset(tmp->data);
    } else {
        const kd_tree_inter_node * tmp = static_cast<const kd_tree_inter_node*>(kdNode); // Added missing semicolon
        nodeJson["split_dimension"] = tmp->split_dimension;
        nodeJson["split_value"] = tmp->split_value;
    }
    return nodeJson;
}

json serializeKDTree(const KDTreeNode* root) {
    if (root == nullptr) {
        return nullptr;
    }
    json kdJson;
    kdJson["node"] = serializeKDNode(root);


    if (!root->isLeaf()) {     
        
        const kd_tree_inter_node* interNode = dynamic_cast<const kd_tree_inter_node*>(root);
        kdJson["left"] = serializeKDTree(interNode->left);
        kdJson["right"] = serializeKDTree(interNode->right);
    }
    return kdJson;
}


std::string serializePathBST(const std::vector<Sector*>& path) {
    json j;
    for (const Sector* sector : path) {
        j.push_back(serializeSectorBST(*sector));
    }
    return j.dump(4); // 4 is the indentation level for pretty printing
}

std::string serializePathLLRBT(const std::vector<Sector*>& path) {
    json j;
    for (const Sector* sector : path) {
        j.push_back(serializeSectorLLRBT(*sector));
    }
    return j.dump(4); // 4 is the indentation level for pretty printing
}

#endif