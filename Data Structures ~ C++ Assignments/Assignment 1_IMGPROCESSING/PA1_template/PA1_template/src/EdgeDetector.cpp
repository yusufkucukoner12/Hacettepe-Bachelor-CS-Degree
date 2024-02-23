// EdgeDetector.cpp

#include "EdgeDetector.h"
#include <cmath>

#include "EdgeDetector.h"
#include <cmath>

// Default constructor
EdgeDetector::EdgeDetector() {

}

// Destructor
EdgeDetector::~EdgeDetector() {

}

// Detect Edges using the given algorithm
std::vector<std::pair<int, int>> EdgeDetector::detectEdges(const ImageMatrix& input_image) {
    double** kernelX = new double*[3];
    for (int i = 0; i < 3; i++) {
        kernelX[i] = new double[3];
        for (int j = 0; j < 3; j++) {
            kernelX[i][j] = 0.0; 
        }
        
    }
    kernelX[0][0] = -1;
    kernelX[0][1] = -2;
    kernelX[0][2] = -1;
    kernelX[2][0] = 1;
    kernelX[2][1] = 2;
    kernelX[2][2] = 1;

    Convolution xConv(kernelX,3,3,1,true);

    ImageMatrix imageX = xConv.convolve(input_image);

    double** kernelY = new double*[3];
    for (int i = 0; i < 3; i++) {
        kernelY[i] = new double[3];
        for (int j = 0; j < 3; j++) {
            kernelY[i][j] = 0.0; 
        }
    }
    kernelY[0][0] = -1;
    kernelY[1][0] = -2;
    kernelY[2][0] = -1;
    kernelY[0][2] = 1;
    kernelY[1][2] = 2;
    kernelY[2][2] = 1;

    Convolution yConv(kernelY,3,3,1,true);

    ImageMatrix imageY = yConv.convolve(input_image);

    double total = 0.0;
    for(int x = 0; x < imageX.get_height() ; x++){
        for(int y = 0; y < imageX.get_width() ; y++){
           total += sqrt(pow(imageX.get_data()[x][y],2) + pow(imageY.get_data()[x][y],2));
        }
    }
    double thresHold = total/double(imageX.get_height()*imageX.get_width());

    std::vector<std::pair<int, int>> edges;

    for(int x = 0; x < imageX.get_height() ; x++){
        for(int y = 0; y < imageX.get_width() ; y++){
            if(sqrt(pow(imageX.get_data()[x][y],2) + pow(imageY.get_data()[x][y],2))>thresHold){
                std::pair<int,int> qwe(x,y);
                edges.push_back(qwe);
            }
        }
    }

    return edges;
}

