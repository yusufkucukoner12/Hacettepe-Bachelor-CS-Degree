#include "EncodeMessage.h"
#include <cmath>
#include <iostream>




// Default Constructor
EncodeMessage::EncodeMessage() {

}

// Destructor
EncodeMessage::~EncodeMessage() {
    
}

// Function to encode a message into an image matrix
ImageMatrix EncodeMessage::encodeMessageToImage(const ImageMatrix &img, const std::string &message, const std::vector<std::pair<int, int>>& positions) {
    std::string newMessage;
    
    std::cout << positions.size() << " " <<  message.length() << std::endl;
    for(int i = 0; i<message.size();i++){
        int value = static_cast<int>(message[i]);
        if(isPrime(i)){
            value += Fibonacci(i);
            if(value >= 127){
                value = 126;
            }

            char myChar = static_cast<char>(value);
            newMessage += myChar;

            
        }
        else{
            char myChar = static_cast<char>(value);
            newMessage += myChar;

            
        }
    }

    newMessage = rightCircularShift(newMessage);
    
    std::string finalBitMessage;

    for(int a = 0 ; a<message.size() ; a++){
        finalBitMessage += turnToBinary(static_cast<int>(newMessage[a]));
        
    }

    std::cout << finalBitMessage  << std::endl;

    for (int i = 0; i < positions.size(); i++) {


        int x = positions[i].first;
        int y = positions[i].second;

        int pixelvalue = img.get_data()[x][y];

        pixelvalue = (pixelvalue & 0xFE) | (static_cast<char>(finalBitMessage[i]) & 1); 

        img.get_data()[x][y] = pixelvalue;
    }


    return img;

}

std::string EncodeMessage::turnToBinary(int number){
    std::string binaryString;
    while (number > 0) {
        int lsb = number & 1; 
        char lsbChar = '0' + lsb; 
        binaryString = lsbChar + binaryString; 
        number >>= 1;
    }

     while (binaryString.size() < 7) {
        binaryString = '0' + binaryString;
    }
    return binaryString;
}


int EncodeMessage::Fibonacci(int n){
    if(n <= 1){
        return 1;
    }
    else{
        return Fibonacci(n-1) + Fibonacci(n-2);
    }   
}

bool EncodeMessage::isPrime(int n)
{
    if (n <= 1)
        return false;
    if(n == 2){
        return true;
    }
    for (int i = 2; i <= n / 2; i++)
        if (n % i == 0)
            return false;
 
    return true;
}



std::string EncodeMessage::rightCircularShift(const std::string& message) {
    int shift = message.length() / 2;
    std::string shiftedMessage = message;

    for (int i = 0; i < message.length(); i++) {
        int newIndex = (i + shift) % message.length();
        shiftedMessage[newIndex] = message[i];
    }

    return shiftedMessage;
}