//
// Created by alperen on 10.11.2023.
//

#ifndef PA2_TESTUTILS_H
#define PA2_TESTUTILS_H


#include <iostream>
#include <iomanip>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "Serialization.h"
#include "Deserialization.h"
#include <algorithm> // For std::shuffle
#include <random>    // For std::default_random_engine
#include <chrono>    // For std::chrono

using namespace std;
const char* whitespaces = " \t\n\r\f\v";

std::vector<std::string> read_string_vector_from_file(const std::string& filename) {
    std::vector<std::string> vec;
    std::ifstream inFile(filename);
    std::string line;

    if (!inFile.is_open()) {
        throw std::runtime_error("Unable to open file for reading: " + filename);
    }

    while (std::getline(inFile, line)) {
        if (!line.empty()) {
            vec.push_back(line);
        }
    }

    inFile.close();
    return vec;
}

void delete_tree(Sector* node) {
    if (node == nullptr) {
        return;
    }

    // Recursively delete left and right subtrees
    delete_tree(node->left);
    delete_tree(node->right);

    // Delete the current node after deleting its children
    delete node;
}

void delete_kd_tree(KDTreeNode* node) {
    if (node != nullptr) {
        if (!node->isLeaf()) {
            kd_tree_inter_node* interNode = static_cast<kd_tree_inter_node*>(node);
            delete_kd_tree(interNode->left);
            delete_kd_tree(interNode->right);
        }
        delete node;
    }
}


Sector* deserializeBST(string filename) {
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening the file!" << std::endl;
        throw;
    }

    std::stringstream buffer;
    buffer << inputFile.rdbuf();

    // Close the file
    inputFile.close();
    string jsonString = buffer.str();

    json serialParsed = json::parse(jsonString);
    Sector* root = deserializeBST(serialParsed);
    return root;
}


Sector* deserializeLLRBT(string filename) {
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening the file!" << std::endl;
        throw;
    }

    std::stringstream buffer;
    buffer << inputFile.rdbuf();

    // Close the file
    inputFile.close();
    string jsonString = buffer.str();

    json serialParsed = json::parse(jsonString);
    Sector* root = deserializeLLRBT(serialParsed);
    return root;
}

KDTreeNode* load_root_kdtree(string filename) {
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening the file!" << std::endl;
        throw;
    }

    std::stringstream buffer;
    buffer << inputFile.rdbuf();

    // Close the file
    inputFile.close();
    string jsonString = buffer.str();

    json serialParsed = json::parse(jsonString);
    KDTreeNode* root = deserializeKDTree(serialParsed);
    return root;
}

vector<Sector*> load_path_bst(string filename) {
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening the file!" << std::endl;
        throw;
    }

    std::stringstream buffer;
    buffer << inputFile.rdbuf();

    // Close the file
    inputFile.close();
    string jsonString = buffer.str();

    json serialParsed = json::parse(jsonString);
    vector<Sector*> path = deserializePathBST(serialParsed);
    return path;
}

vector<Sector*> load_path_llrbt(string filename) {
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening the file!" << std::endl;
        throw;
    }

    std::stringstream buffer;
    buffer << inputFile.rdbuf();

    // Close the file
    inputFile.close();
    string jsonString = buffer.str();

    json serialParsed = json::parse(jsonString);
    vector<Sector*> path = deserializePathLLRBT(serialParsed);
    return path;
}

void test_silent(double (*code)()) {
#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__) || defined(__NT__)
    fflush(stdout);

    // Redirect stdout to 'nul' (Windows equivalent of '/dev/null')
    FILE* stream;
    freopen("nul", "w", stdout);

    // Test code here
    double score = code();

    // Flush stdout and restore the original stdout
    fflush(stdout);
    freopen("CON", "w", stdout);

    std::cout << std::fixed << std::setprecision(6) << score;
#else
    fflush(stdout);
    int original_stdout_fd = dup(fileno(stdout));
    freopen("/dev/null", "w", stdout);

    // Test code here
    double score = code();

    // Enable out
    fflush(stdout);
    dup2(original_stdout_fd, fileno(stdout));
    close(original_stdout_fd);
    cout << fixed << setprecision(6) << score;
#endif
}

// trim from end of string (right)
inline std::string& rtrim(std::string& s, const char* t = whitespaces)
{
    s.erase(s.find_last_not_of(t) + 1);
    return s;
}

// trim from beginning of string (left)
inline std::string& ltrim(std::string& s, const char* t = whitespaces)
{
    s.erase(0, s.find_first_not_of(t));
    return s;
}

// trim from both ends of string (right then left)
inline std::string& trim(std::string& s, const char* t = whitespaces)
{
    return ltrim(rtrim(s, t), t);
}

vector<string> split(string s, string delimiter) {
    size_t pos_start = 0, pos_end, delim_len = delimiter.length();
    string token;
    vector<string> res;

    while ((pos_end = s.find(delimiter, pos_start)) != string::npos) {
        token = s.substr(pos_start, pos_end - pos_start);
        pos_start = pos_end + delim_len;
        res.push_back(token);
    }

    res.push_back(s.substr(pos_start));
    return res;
}

// No empty lines, all lines trimmed
vector<string> split_trim(string s, string delimiter) {
    size_t pos_start = 0, pos_end, delim_len = delimiter.length();
    string token;
    vector<string> res;

    while ((pos_end = s.find(delimiter, pos_start)) != string::npos) {
        token = s.substr(pos_start, pos_end - pos_start);
        pos_start = pos_end + delim_len;
        trim(token);
        if (!token.empty()) {
            res.push_back(token);
        }
    }
    token = s.substr(pos_start);
    trim(token);
    if (!token.empty()) {
        res.push_back(token);
    }
    return res;
}


#endif //PA2_TESTUTILS_H
