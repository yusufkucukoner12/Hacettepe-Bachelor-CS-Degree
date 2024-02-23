// Convolution.h

#ifndef CONVOLUTION_H
#define CONVOLUTION_H

#include "ImageMatrix.h"

// Class `Convolution`: Provides the functionality to convolve an image with
// a kernel. Padding is a bool variable, indicating whether to use zero padding or not.
class Convolution {
public:
    // Constructors and destructors
    Convolution(); // Default constructor
    Convolution(double** customKernel, int kernelHeight, int kernelWidth, int stride, bool padding); // Parametrized constructor for custom kernel and other parameters
    ~Convolution(); // Destructor
    Convolution(const Convolution &other); // Copy constructor
    Convolution& operator=(const Convolution &other); // Copy assignment operator
    ImageMatrix convolve(const ImageMatrix& input_image) const; // Convolve Function: Responsible for convolving the input image with a kernel and return the convolved image.
    int get_height() const;
    int get_width() const;
    int get_stride() const;
    bool get_padding() const;
    double** get_data() const;
    double get_data(int i, int j) const;
    

private:
    // Add any private member variables and functions .
    int kh;
    int kw;
    int stride;
    bool padding;
    double **dataConv;
};

#endif // CONVOLUTION_H