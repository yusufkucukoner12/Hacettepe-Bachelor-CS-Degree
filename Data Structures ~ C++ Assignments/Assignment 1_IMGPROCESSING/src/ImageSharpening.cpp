#include "ImageSharpening.h"

// Default constructor
ImageSharpening::ImageSharpening() {

}

ImageSharpening::~ImageSharpening(){

}

ImageMatrix ImageSharpening::sharpen(const ImageMatrix& input_image, double k) {
    // CREATING KERNEL WITH POINTER.
    kernel_height = 3;
    kernel_width = 3;
    blurring_kernel = new double*[kernel_height];
    for (int i = 0; i < kernel_height; i++) {
        blurring_kernel[i] = new double[kernel_width];
        for (int j = 0; j < kernel_width; j++) {
            blurring_kernel[i][j] = 1.0/9.0; // Customize the kernel values
        }
    }

    Convolution blurConv(blurring_kernel,kernel_height,kernel_width,1,true);
    
    ImageMatrix blurredImage = blurConv.convolve(input_image);

    ImageMatrix sharpImage = input_image + (input_image - blurredImage)*k;

    for(int i = 0 ; i < sharpImage.get_height() ; i++){
        for(int j = 0 ; j < sharpImage.get_width() ; j++){
            if(sharpImage.get_data(i,j) > 255){
                sharpImage.get_data()[i][j] = 255;
            }
            else if (sharpImage.get_data(i,j) < 0)
            {
                sharpImage.get_data()[i][j] = 0;
            }
        }   
    }

    return sharpImage;
    
}
