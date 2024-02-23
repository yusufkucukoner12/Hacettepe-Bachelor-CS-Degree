#ifndef PA3_DESERIALIZATION_H
#define PA3_DESERIALIZATION_H

#include "json.hpp"
#include "Sector.h"
#include "KDT_Node.h"
#include "kNN_Data.h"

using nlohmann::json;

using namespace std;


Sector* deserializeSectorBST(const json& sectorJson) {
    int x = sectorJson["x"];
    int y = sectorJson["y"];
    int z = sectorJson["z"];
    Sector* sector = new Sector(x, y, z);
    sector->distance_from_earth = sectorJson["distance_from_earth"];
    sector->sector_code = sectorJson["sector_code"];
    return sector;
}

Sector* deserializeBST(const json& bstJson) {
    if (bstJson.is_null()) {
        return nullptr;
    }
    Sector* root = deserializeSectorBST(bstJson["node"]);
    root->left = deserializeBST(bstJson["left"]);
    root->right = deserializeBST(bstJson["right"]);
    if (root->left != nullptr) root->left->parent = root;
    if (root->right != nullptr) root->right->parent = root;
    return root;
}

Sector* deserializeSectorLLRBT(const json& sectorJson) {
    int x = sectorJson["x"];
    int y = sectorJson["y"];
    int z = sectorJson["z"];
    Sector* sector = new Sector(x, y, z);
    sector->distance_from_earth = sectorJson["distance_from_earth"];
    sector->sector_code = sectorJson["sector_code"];
    sector->color = sectorJson["color"];
    return sector;
}

Sector* deserializeLLRBT(const json& bstJson) {
    if (bstJson.is_null()) {
        return nullptr;
    }
    Sector* root = deserializeSectorLLRBT(bstJson["node"]);
    root->left = deserializeLLRBT(bstJson["left"]);
    root->right = deserializeLLRBT(bstJson["right"]);
    if (root->left != nullptr) root->left->parent = root;
    if (root->right != nullptr) root->right->parent = root;
    return root;
}


std::vector<Sector*> deserializePathBST(const std::string& serializedPath) {
    auto j = json::parse(serializedPath);
    std::vector<Sector*> path;
    for (const auto& sectorJson : j) {
        path.push_back(deserializeSectorBST(sectorJson));
    }
    return path;
}

std::vector<Sector*> deserializePathLLRBT(const std::string& serializedPath) {
    auto j = json::parse(serializedPath);
    std::vector<Sector*> path;
    for (const auto& sectorJson : j) {
        path.push_back(deserializeSectorLLRBT(sectorJson));
    }
    return path;
}

Point deserializePoint(const json& pointJson) {
    std::vector<double> features;
    for (const auto& feature : pointJson["features"]) {
        features.push_back(feature);
    }
    std::string label = pointJson["label"];

    return Point(features, label);
}

Dataset deserializeDataset(const json& dataJson) {
    Dataset data;
    for (const auto& pointJson : dataJson["points"]) {
        data.points.push_back(deserializePoint(pointJson));
    }
    return data;
}

KDTreeNode* deserializeKDNode(const json& nodeJson) {
    KDTreeNode * kdNode;
    if (nodeJson["isLeaf"]) {
        Dataset data = deserializeDataset(nodeJson["data"]);
        kdNode =  new kd_tree_leaf_node(data);
    } else {
        int split_dimension = nodeJson["split_dimension"];
        double split_value = nodeJson["split_value"];
        kdNode =  new kd_tree_inter_node(split_dimension, split_value);

    }
    return kdNode;
}

// KDTreeNode* deserializeKDNode(const json& nodeJson) {
//     if (nodeJson["isLeaf"]) {
//         auto kdNode = new KDTreeLeafNode(); // Assuming KDTreeLeafNode is the correct class name
//         kdNode->data = deserializeDataset(nodeJson["data"]);
//         return kdNode;
//     } else {
//         auto kdNode = new KDTreeInternalNode(); // Assuming KDTreeInternalNode is the correct class name
//         kdNode->split_dimension = nodeJson["split_dimension"];
//         kdNode->split_value = nodeJson["split_value"];
//         return kdNode;
//     }
// }


KDTreeNode* deserializeKDTree(const json& kdJson) {
    if (kdJson.is_null()) {
        return nullptr;
    }
    KDTreeNode* root = deserializeKDNode(kdJson["node"]);

    if (!root->isLeaf()) {     
        kd_tree_inter_node* interNode = dynamic_cast<kd_tree_inter_node*>(root);
        interNode->left = deserializeKDTree(kdJson["left"]);
        interNode->right = deserializeKDTree(kdJson["right"]);
        root = interNode;
    }

    return root;
}


#endif
