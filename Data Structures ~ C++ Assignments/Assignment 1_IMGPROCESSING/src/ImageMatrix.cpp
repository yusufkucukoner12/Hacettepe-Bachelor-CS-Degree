#include "ImageMatrix.h"
#include <iostream>


// Default constructor
ImageMatrix::ImageMatrix(){
    
}


// Parameterized constructor for creating a blank image of given size
ImageMatrix::ImageMatrix(int imgHeight, int imgWidth) : height(imgHeight), width(imgWidth), data(nullptr) {
    // Allocate memory for the image matrix
    data = new double*[height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
    }

    // Initialize the image data to zero
    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
            data[i][j] = 0.0;
        }
    }
}

// Parameterized constructor for loading image from file. PROVIDED FOR YOUR CONVENIENCE
ImageMatrix::ImageMatrix(const std::string &filepath) {
    ImageLoader imageLoader(filepath);

    // Get the dimensions of the loaded image
    height = imageLoader.getHeight();
    width = imageLoader.getWidth();

    // Allocate memory for the matrix
    data = new double*[height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
    }

    // Copy data from imageLoader to data
    double** imageData = imageLoader.getImageData();
    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; j++) {
            data[i][j] = imageData[i][j];
        }
    }
}



// Destructor
ImageMatrix::~ImageMatrix() {
    for(int i = 0 ; i<height ; i++){
        delete[] data[i];
    }
    delete[] data;

}

// Parameterized constructor - direct initialization with 2D matrix
ImageMatrix::ImageMatrix(const double** inputMatrix, int imgHeight, int imgWidth)
    : height(imgHeight), width(imgWidth), data(nullptr) {

    // Allocate memory for the image matrix and copy data
    data = new double*[height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
        for (int j = 0; j < width; ++j) {
            data[i][j] = inputMatrix[i][j];
        }
    }
}


// Copy constructor
ImageMatrix::ImageMatrix(const ImageMatrix &other) : height(other.get_height()), width(other.get_width()) {
    // Allocate memory for the matrix and copy data
    data = new double*[height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
        for (int j = 0; j < width; ++j) {
            data[i][j] = other.get_data(i,j);
        }
    }
}


ImageMatrix& ImageMatrix::operator=(const ImageMatrix &other) {
    if (this == &other) {
        return *this; // self-assignment check
    }

    // Deallocate old memory
    if (data != nullptr) {
        for (int i = 0; i < height; ++i) {
            delete[] data[i];
        }
        delete[] data;
    }

    // Copy from other
    height = other.get_height();
    width = other.get_width();
    data = new double*[height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
        for (int j = 0; j < width; ++j) {
            data[i][j] = other.get_data(i,j);
        }
    }

    return *this;
}




// Overloaded operators

// Overloaded operator + to add two matrices
ImageMatrix ImageMatrix::operator+(const ImageMatrix &other) const {
    ImageMatrix addedMatrix(height, width);

    for(int i = 0; i<height ; i++){
        for(int j = 0; j<height ; j++){
            addedMatrix.get_data()[i][j] = data[i][j] + other.get_data(i,j);
            
        }
    }
    return addedMatrix;
}

// Overloaded operator - to subtract two matrices
ImageMatrix ImageMatrix::operator-(const ImageMatrix &other) const {
    ImageMatrix subtractedMatrix(height, width);

    for(int i = 0; i<height ; i++){
        for(int j = 0; j<height ; j++){
            subtractedMatrix.get_data()[i][j] = data[i][j] - other.get_data(i,j);
            
        }
    }
    return subtractedMatrix;
}

// Overloaded operator * to multiply a matrix with a scalar
ImageMatrix ImageMatrix::operator*(const double &scalar) const {
    ImageMatrix multipliedMatrix(height, width);
    for(int i = 0; i<height ; i++){
        for(int j = 0; j<height ; j++){
            multipliedMatrix.get_data()[i][j] = data[i][j] * scalar; 
            
        }
    }
    return multipliedMatrix;

}


// Getter function to access the data in the matrix
double** ImageMatrix::get_data() const {

    return data;
}

// Getter function to access the data at the index (i, j)
double ImageMatrix::get_data(int i, int j) const {
    return data[i][j];
}

int ImageMatrix::get_height() const{
    return height;
}

int ImageMatrix::get_width() const{
    return width;
}


