#include "Convolution.h"

// Default constructor 
Convolution::Convolution() {
}

// Parametrized constructor for custom kernel and other parameters
Convolution::Convolution(double** customKernel, int kh, int kw, int stride_val, bool pad):
    kw(kw), kh(kh), stride(stride_val), padding(pad){
        
    dataConv = new double*[kh];
    for (int i = 0; i < kh; i++) {
        dataConv[i] = new double[kw];
        for (int j = 0; j < kw; j++) {
            dataConv[i][j] = customKernel[i][j];
        }
    }
}

// Destructor
Convolution::~Convolution() {
    for(int i = 0 ; i<kh ; i++){
        delete[] dataConv[i];
    }
    delete[] dataConv;
}

// Copy constructor
Convolution::Convolution(const Convolution &other): kw(other.get_width()), 
    kh(other.get_height()), padding(other.get_padding()), stride(other.get_stride()) {
    dataConv = new double*[kh];
    for (int i = 0; i < kh; i++) {
        dataConv[i] = new double[kw];
        for (int j = 0; j < kw; j++) {
            dataConv[i][j] = other.get_data(i,j);
        }
    }
}

// Copy assignment operator
Convolution& Convolution::operator=(const Convolution &other){
    if (this == &other) {
        return *this; 
    }

    /
    if (dataConv != nullptr) {
        for (int i = 0; i < kh; i++) {
            delete[] dataConv[i];
        }
        delete[] dataConv;
    }

    
    kh = other.get_height();
    kw = other.get_width();
    padding = other.get_padding();
    stride = other.get_stride();
    dataConv = new double*[kh];
    for (int i = 0; i < kh; i++) {
        dataConv[i] = new double[kw];
        for (int j = 0; j < kw; j++) {
            dataConv[i][j] = other.get_data(i,j);
        }
    }

    return *this;
}



ImageMatrix Convolution::convolve(const ImageMatrix& input_image) const {  
    int inputHeight = (int) input_image.get_height();
    int inputWidth = (int) input_image.get_width();

    int outputHeight = (inputHeight - kh + 2 *  padding)/stride + 1;
    int outputWidth = (inputWidth - kw + 2 * padding)/stride + 1;;

    int paddedHeight = inputHeight + 2 * padding;
    int paddedWidth = inputWidth + 2 * padding;

    
    double** paddedImage = new double*[paddedHeight];
    for (int i = 0; i < paddedHeight; i++) {
        paddedImage[i] = new double[paddedWidth];
        for (int j = 0; j < paddedWidth; j++) {
            if (i < padding || i >= paddedHeight - padding || j < padding || j >= paddedWidth - padding) {
                paddedImage[i][j] = 0.0; 
            } else {
                paddedImage[i][j] = input_image.get_data()[i - padding][j - padding]; 
            }
        }
    }
        

    ImageMatrix output(outputHeight,outputWidth);
    for (int i = 0; i < outputHeight; i++) {
        for (int j = 0; j < outputWidth; j++) {
            for (int m = 0; m < kh; m++) {
                for (int n = 0; n < kw; n++) {  
                    int row = i * stride + m;
                    int colm = j * stride + n;
                    if(row < paddedHeight && row >=0 && colm<paddedWidth && colm >= 0){
                        output.get_data()[i][j] += paddedImage[row][colm] * dataConv[m][n];
                    }
                }
            }
                
        }
    }
    

    // Clean up the paddedImage
    for (int i = 0; i < paddedHeight; i++) {
        delete[] paddedImage[i];
    }
    delete[] paddedImage;

    return output;
}

double** Convolution::get_data() const{
    return dataConv;
}
double Convolution::get_data(int i, int j) const{
    return dataConv[i][j];
}

int Convolution::get_height() const{
    return kh;
}

int Convolution::get_width() const{
    return kw;
}

int Convolution::get_stride() const{
    return stride;
}

bool Convolution::get_padding() const{
    return padding;
}


