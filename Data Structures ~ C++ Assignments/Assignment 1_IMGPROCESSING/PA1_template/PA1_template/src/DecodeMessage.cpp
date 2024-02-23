// DecodeMessage.cpp

#include "DecodeMessage.h"
#include <iostream>

// Default constructor
DecodeMessage::DecodeMessage() {
    // Nothing specific to initialize here
}

// Destructor
DecodeMessage::~DecodeMessage() {
    // Nothing specific to clean up
}


std::string DecodeMessage::decodeFromImage(const ImageMatrix& image, const std::vector<std::pair<int, int>>& edgePixels) {
        std::string total = "";

        for(int i = 0 ; i < edgePixels.size() ; i++){
            int x = edgePixels[i].first;
            int y = edgePixels[i].second;

            total += std::to_string(returnLSB(image.get_data(x,y))) ;

        }
        std::string padded = addPadding(total);

        std::vector<std::string> bitsBy7 = splitBinaryTo7Bit(padded);

        std::string result = "";
        
        for(std::string bits : bitsBy7){
            result += binaryToASCII(bits);
        }

        return result;
}

std::string DecodeMessage::binaryToASCII(const std::string& binary) {
    int decimal = std::stoi(binary, 0, 2); 
    if (decimal <= 32) {
        decimal += 33; 
    } else if (decimal == 127) {
        decimal= 126; 
    }
    return std::string(1, static_cast<char>(decimal));
}     

int DecodeMessage::returnLSB(int decimal){
    int bit;
    while(decimal > 1){
        bit = decimal % 2;
        return bit;
    }
    return decimal;
}

std::string DecodeMessage::addPadding(std::string& binary) {
    std::string segment;

    if(binary.size()%7 != 0){
        int a = 7-binary.size()%7;

        for(int i = 0 ; i < a ; i++){
            binary = "0" + binary;
        }
    }
    return binary;
}

std::vector<std::string> DecodeMessage::splitBinaryTo7Bit(const std::string& binary) {
    std::vector<std::string> result;
    std::string bit7;

    for (char bit : binary) {
        bit7.push_back(bit);
        if (bit7.size() == 7) {
            result.push_back(bit7);
            bit7.clear();
        }
    }
    return result;
}

