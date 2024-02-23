#include <iostream>
#include <vector>
#include <sstream>
#include <fstream>
#include <cmath>

double** convolution2DWithPadding(double** imageData, int imageHeight, int imageWidth, double** kernel, int kernelHeight, int kernelWidth) {
    int padding = 1;  
    int outputHeight = imageHeight - kernelHeight + 2 * padding + 1;
    int outputWidth = imageWidth - kernelWidth + 2 * padding + 1;;

    // Calculate the dimensions of the padded image
    int paddedHeight = imageHeight + 2 * padding;
    int paddedWidth = imageWidth + 2 * padding;

    // Create an image with zero-padding
    double** paddedImage = new double*[paddedHeight];
    for (int i = 0; i < paddedHeight; i++) {
        paddedImage[i] = new double[paddedWidth];
        for (int j = 0; j < paddedWidth; j++) {
            if (i < padding || i >= paddedHeight - padding || j < padding || j >= paddedWidth - padding) {
                paddedImage[i][j] = 0.0; // Apply zero-padding
            } else {
                paddedImage[i][j] = imageData[i - padding][j - padding]; // Copy the original image
            }
        }
    }

    /*for(int i = 0 ; i < paddedHeight ; i++){
        for(int j = 0 ; j < paddedWidth ; j++){
            std::cout << paddedImage[i][j] << " ";
        }
        std::cout << std::endl;
    }*/
        


    // Create the output matrix
    double** output = new double*[outputHeight];
    for (int i = 0; i < outputHeight; i++) {
        output[i] = new double[outputWidth];
        for (int j = 0; j < outputWidth; j++) {
            output[i][j] = 0.0;
            for (int m = 0; m < kernelHeight; m++) {
                for (int n = 0; n < kernelWidth; n++) {
                    output[i][j] += paddedImage[i + m][j + n] * kernel[m][n];
                }
            }
            output[i][j] = output[i][j]/9;
        }
    }

    // Clean up the paddedImage
    for (int i = 0; i < paddedHeight; i++) {
        delete[] paddedImage[i];
    }
    delete[] paddedImage;

    return output;
}



double** imageSharping(double** imageDataBlur, double** imageDataOrg, int height, int width, int k){
    double** newImage = new double*[height];
    for(int a = 0; a<height ; a++){
        newImage[a] = new double[width];
    }    
    for(int x = 0; x < height ; x++){
        for(int y = 0; y < width ; y++){
            newImage[x][y] = imageDataOrg[x][y] + (imageDataOrg[x][y] - imageDataBlur[x][y])*k;
        }
    }
    return newImage;
}

int decimalToBinary(int decimal){
    std::string bit = "";
    while(decimal > 1){
        bit = std::to_string(decimal % 2);
        return stoi(bit);
    }
    return stoi(bit);
}



std::vector<std::string> splitTheText(std::string text, char del){
    std::vector<std::string> tokens; // Vector to store the split parts
    std::istringstream iss(text);
    std::string token;
    while (std::getline(iss, token, del)) {
        tokens.push_back(token); // Add each part to the vector
    }
    return tokens;
}

std::vector<std::string> splitBinaryTo7Bit(const std::string& binary) {
    std::vector<std::string> result;
    std::string segment;

    for (char bit : binary) {
        segment.push_back(bit);
        if (segment.size() == 7) {
            result.push_back(segment);
            segment.clear();
        }
    }

    // Add padding if the last segment is not 7 bits
    if (!segment.empty()) {
        while (segment.size() < 7) {
            segment.push_back('0');
        }
        result.push_back(segment);
    }

    return result;
}

std::string binaryToASCII(const std::string& binary) {
    int decimalValue = std::stoi(binary, 0, 2); // Convert binary to decimal
    if (decimalValue <= 32) {
        decimalValue += 33; // Adjust if the ASCII value is <= 32
    } else if (decimalValue == 127) {
        decimalValue = 126; // Clip to 126 if the ASCII value is 127
    }
    return std::string(1, static_cast<char>(decimalValue));
}   
bool isPrime(int n) {
    if (n <= 1) {
        return false;
    }
    if (n <= 3) {
        return true;
    }
    if (n % 2 == 0 || n % 3 == 0) {
        return false;
    }
    for (int i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0) {
            return false;
        }
    }
    return true;
}

void adjustMessage(std::string& message) {
    const int fibSequence[] = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610}; // Fibonacci sequence

    for (int i = 0; i < message.size(); i++) {
        if (isPrime(i + 1)) {
            int fibIndex = i % 16; // Wrap around to the length of the Fibonacci sequence
            int fibValue = fibSequence[fibIndex];
            int charValue = static_cast<int>(message[i]);
            charValue += fibValue;

            if (charValue <= 32) {
                charValue += 33;
            } else if (charValue >= 127) {
                charValue = 126;
            }

            message[i] = static_cast<char>(charValue);
        }
    }
}




int main(){

    std::string command = "python convert_pil_to_txt.py";
    int result = std::system(command.c_str());

    if (result == 0) {
        std::cout << "Python script executed successfully." << std::endl;
    } else {
        std::cerr << "Error executing Python script. Return code: " << result << std::endl;
    }

    std::ifstream file("output1.txt");

    if (!file.is_open()) {
        std::cerr << "Error opening file: " << std::endl;

    }

    std::string line;
    std::vector<std::vector<double>> tempImageData;

    while (std::getline(file, line)) {
        std::istringstream iss(line);
        std::vector<double> row;
        double pixel;
        while (iss >> pixel) {
            row.push_back(pixel);
        }
        tempImageData.push_back(row);
    }

    file.close();

    int height = static_cast<int>(tempImageData.size());
    if (height == 0) {
        std::cerr << "Error: Empty image data." << std::endl;
    }

    int width = static_cast<int>(tempImageData[0].size());

    // Allocate memory for imageData and copy data
    double** imageData = new double*[height];
    for (int i = 0; i < height; ++i) {
        imageData[i] = new double[width];
        for (int j = 0; j < width; ++j) {
            imageData[i][j] = tempImageData[i][j];
        }
    }

    // UNTIL NOW CREATED THE ORIGINAL IMAGE
    
    for(int i = 0 ; i < height ; i++){
        for(int j = 0 ; j < width ; j++){
            std::cout << imageData[i][j] << " ";
        }
        std::cout << std::endl;
    }

    // CREATING KERNEL WITH POINTER.
    const int kernelHeight = 3;
    const int kernelWidth = 3;
    double** kernel = new double*[kernelHeight];
    for (int i = 0; i < kernelHeight; i++) {
        kernel[i] = new double[kernelWidth];
        for (int j = 0; j < kernelWidth; j++) {
            kernel[i][j] = 1.0; // Customize the kernel values
        }
    }

    // Perform the 2D convolution with zero-padding
    double** output = convolution2DWithPadding(imageData, height, width, kernel, kernelHeight, kernelWidth);
    std::cout << " CONVOLUTION " << std::endl;
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            std::cout << output[i][j] << " ";
        }
        std::cout << std::endl;
    }
    std::cout << " CONVOLUTION " << std::endl;



    double** sharpened = imageSharping(imageData,output,height,width,1);
    std::cout << "SHARP " << std::endl;
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            std::cout << sharpened[i][j] << " ";
        }
        std::cout << std::endl;
    }
    std::cout << "SHARP " << std::endl;

    double** kernelX = new double*[kernelHeight];
    for (int i = 0; i < kernelHeight; i++) {
        kernelX[i] = new double[kernelWidth];
        for (int j = 0; j < kernelWidth; j++) {
            kernelX[i][j] = 0.0; // Customize the kernel values
        }
        
    }
    kernelX[0][0] = -1;
    kernelX[0][1] = -2;
    kernelX[0][2] = -1;
    kernelX[2][0] = 1;
    kernelX[2][1] = 2;
    kernelX[2][2] = 1;

    double** outputX = convolution2DWithPadding(imageData, height, width, kernelX, 3, 3);

    double** kernelY = new double*[kernelHeight];
    for (int i = 0; i < kernelHeight; i++) {
        kernelY[i] = new double[kernelWidth];
        for (int j = 0; j < kernelWidth; j++) {
            kernelY[i][j] = 0.0; // Customize the kernel values
        }
        
    }
    kernelY[0][0] = -1;
    kernelY[1][0] = -2;
    kernelY[2][0] = -1;
    kernelY[0][2] = 1;
    kernelY[1][2] = 2;
    kernelY[2][2] = 1;

    double** outputY  = convolution2DWithPadding(imageData, height, width, kernelY, 3, 3);
    double total = 0.0;
    for(int x = 0; x < height ; x++){
        for(int y = 0; y < width ; y++){
           total += sqrt(pow(outputX[x][y],2) + pow(outputY[x][y],2));
        }
    }
    double thresHold = total/double(height*width);

    std::cout << thresHold << std::endl;

    std::vector<std::pair<int, int>> edges;

    for(int x = 0; x < height ; x++){
        for(int y = 0; y < width ; y++){
            if((int)sqrt(pow(outputX[x][y],2) + pow(outputY[x][y],2))>thresHold){
                std::pair<int,int> qwe((int)sqrt(pow(outputX[x][y],2) + pow(outputY[x][y],2)),(int)sharpened[x][y]);
                edges.push_back(qwe);
            }
        }
    }
    std::string bin;
    for(int x = 0; x < edges.size() ; x++){
        std::cout << edges[x].first << " " << edges[x].second << " " << decimalToBinary(edges[x].first) << std::endl;
        bin = bin + std::to_string(decimalToBinary(edges[x].first));
    }
    std::cout << bin << std::endl;
    
    splitTheText(bin,*"");

    std::vector<std::string> binarySegments = splitBinaryTo7Bit(bin);

    std::string message;
    for (const std::string& segment : binarySegments) {
        std::string adjustedChar = binaryToASCII(segment);
        message += adjustedChar;
    }

    // Step 2.2.1: Character Transformation
    adjustMessage(message);

    // Step 2.2.2: Right Circular Shifting
    rightCircularShift(message, message.length() / 2);

    std::cout << message << std::endl;




    

    return 0;


}

