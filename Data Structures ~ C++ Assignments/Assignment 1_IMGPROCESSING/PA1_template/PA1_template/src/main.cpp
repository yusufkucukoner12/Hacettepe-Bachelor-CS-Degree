#include <iostream>
#include <vector>
#include <fstream>
#include <iomanip>
#include <utility> 
#include <cmath>

#include "ImageMatrix.h"
#include "Convolution.h"
#include "DecodeMessage.h"
#include "EncodeMessage.h"
#include "ImageSharpening.h"
#include "EdgeDetector.h"
#include "ImageProcessor.h"


using namespace std;



// Global img, bad practice :(

// define a 5x5 matrix to initialize a 5x5 image
const double* directData1[5] = {
    new double[5]{1.0, 2.0, 3.0, 4.0, 5.0},
    new double[5]{6.0, 7.0, 8.0, 9.0, 10.0},
    new double[5]{11.0, 12.0, 13.0, 14.0, 15.0},
    new double[5]{16.0, 17.0, 18.0, 19.0, 20.0},
    new double[5]{21.0, 22.0, 23.0, 24.0, 25.0}
};



// create an iamge
ImageMatrix image1(directData1, 5, 5);


void compare_images(const ImageMatrix& im1, const ImageMatrix& im2, double& score, int& total_number){
    for (int i = 0; i < im1.get_height(); i++) {
        for (int j = 0; j < im1.get_width(); j++) {
            total_number++;
            double im1_value = std::round(im1.get_data()[i][j] * 1e3) / 1e3;  // rounding to 3 decimal places
            double im2_value = std::round(im2.get_data()[i][j] * 1e3) / 1e3;  // rounding to 3 decimal places
            if (im1_value == im2_value) {
                score += 1.0;
            } 
            else{
                cout << "im1_value: " << im1_value << " expected: " << im2_value << " at " << i << "," << j <<endl;

            }
        }
    }   
}

void test_conv(double& score, int& total_number){

    //  3x3 Gaussian kernel, stride 1, no padding
    double* kernel_gaussian[3] = {
        new double[3]{1.0/9.0, 1.0/9.0, 1.0/9.0}, 
        new double[3]{1.0/9.0, 1.0/9.0, 1.0/9.0}, 
        new double[3]{1.0/9.0, 1.0/9.0, 1.0/9.0}
    };    


    // TEST 1
    // conv obj1: 3x3 kernel, stride 1, no padding
    Convolution conv_gaussian_s1_no_pad(kernel_gaussian, 3, 3, 1, false);

    // convolve the image with the kernel
    ImageMatrix convolved_image1 = conv_gaussian_s1_no_pad.convolve(image1);

    // The expected data 3x3 
    const double* directData2[3] = {
        new double[3]{7, 8, 9},
        new double[3]{12, 13, 14},
        new double[3]{17, 18, 19}
    };

    // create an image  
    ImageMatrix expected_image(directData2, 3, 3);

    // compare the convolved image with the expected image
    compare_images(convolved_image1, expected_image, score, total_number);


    // TEST 2
    // conv obj2: 3x3 kernel, stride 1, with padding
    Convolution conv_gaussian_s1_with_pad(kernel_gaussian, 3, 3, 1, true);

    // convolve the image with the kernel
    ImageMatrix convolved_image2 = conv_gaussian_s1_with_pad.convolve(image1);

    // the expected data2:
    const double* expectedData2[5] = {
        new double[5]{1.77778, 3, 3.66667, 4.33333, 3.11111},
        new double[5]{4.33333, 7, 8, 9, 6.33333},
        new double[5]{7.66667, 12, 13, 14, 9.66667},
        new double[5]{11, 17, 18, 19, 13},
        new double[5]{8.44444, 13, 13.6667, 14.3333, 9.77778}
    };    

    // create an image
    ImageMatrix expected_image2(expectedData2, 5, 5);

    // compare the convolved image with the expected image
    compare_images(convolved_image2, expected_image2, score, total_number);



}

void test_sharpening(double& score, int& total_number){
    // create an image sharpening object
    ImageSharpening imageSharpening;

    // sharpen the image
    ImageMatrix sharpened_image = imageSharpening.sharpen(image1, 2);

    // the expected data:
    const double* expectedData[5] = {
        new double[5]{0, 0, 1.66667, 3.33333, 8.77778 },
        new double[5]{9.33333, 7, 8, 9, 17.3333},
        new double[5]{17.6667, 12, 13, 14, 25.6667},
        new double[5]{26, 17, 18, 19, 34 },
        new double[5]{46.1111, 40, 41.6667, 43.3333, 55.4444}
    };

    // create an image
    ImageMatrix expected_image(expectedData, 5, 5);

    // compare the convolved image with the expected image
    compare_images(sharpened_image, expected_image, score, total_number);



}


void test_edge_detection(double& score, int& total_number){
    // Create an EdgeDetector object
    EdgeDetector edgeDetector;

    // Detect edges using the EdgeDetector
    vector<pair<int, int>> edgePixels = edgeDetector.detectEdges(image1);

    // Create the expected output: [(2, 0), (2, 4), (3, 0), (3, 4), (4, 0), (4, 1), (4, 2), (4, 3), (4, 4)]
    vector<pair<int, int>> expectedEdgePixels = {
        make_pair(2, 0),
        make_pair(2, 4),
        make_pair(3, 0),
        make_pair(3, 4),
        make_pair(4, 0),
        make_pair(4, 1),
        make_pair(4, 2),
        make_pair(4, 3),
        make_pair(4, 4)
    };

    // Compare the output with the expected output
    for (int i = 0; i < edgePixels.size(); i++) {
        total_number += 1;
        if (edgePixels[i] == expectedEdgePixels[i]) {
            score += 1.0;
        }
    }    
}


void test_encode_mssg(double& score, int& total_number){
    // Test 1 create an arbitrary 10x10 image
    const double* im1_data[10] = {
        new double[10] {182, 151, 174, 154, 119, 247, 43, 170, 39, 146},
        new double[10] {12, 49, 172, 73, 44, 81, 158, 205, 138, 19},
        new double[10] {251, 76, 218, 212, 188, 28, 24, 210, 0, 164},
        new double[10] {188, 96, 174, 77, 67, 134, 64, 33, 55, 182},
        new double[10] {28, 47, 97, 83, 101, 2, 46, 199, 36, 185},
        new double[10] {55, 89, 233, 221, 127, 194, 48, 147, 213, 105},
        new double[10] {86, 199, 15, 41, 131, 237, 48, 82, 45, 213},
        new double[10] {26, 188, 116, 14, 82, 96, 24, 173, 68, 238},
        new double[10] {46, 101, 106, 11, 129, 81, 21, 253, 252, 168},
        new double[10] {78, 105, 165, 8, 232, 81, 184, 91, 243, 69}
    };

    // create an ImageMatrix object with the above data
    ImageMatrix im1(im1_data,10, 10);

    // THESE ARE ARBITRARY EDGE PIXELS. THIS FUNCTION HAS NO DEPENDENCY ON THE EDGE DETECTION FUNCTION.
    vector<pair<int, int>> EdgePixels1 = {
        make_pair(0, 0),
        make_pair(0, 1),
        make_pair(0, 2),
        make_pair(0, 3),
        make_pair(0, 4),
        make_pair(1, 0),
        make_pair(1, 1),
        make_pair(1, 2),
        make_pair(1, 3),
        make_pair(1, 4),
        make_pair(2, 0),
        make_pair(2, 1),
        make_pair(2, 2),
        make_pair(2, 3),
        make_pair(2, 4),
        make_pair(3, 0),
        make_pair(3, 1),
        make_pair(3, 2),
        make_pair(3, 3),
        make_pair(3, 4),
        make_pair(4, 0),
        make_pair(4, 1),
        make_pair(4, 2),
        make_pair(4, 3),
        make_pair(4, 4),
};

    EncodeMessage em1;
    ImageMatrix im1_encoded = em1.encodeMessageToImage(im1, "TestMessage", EdgePixels1);

    //im1_encoded.print();

    // The expected data 10x10
    const double* expected_data1[10] = {
        new double[10] {183, 151, 174, 154, 119, 247, 43, 170, 39, 146 },
        new double[10] {13, 49, 172, 73, 45, 81, 158, 205, 138, 19},
        new double[10] {251, 77, 219, 213, 189, 28, 24, 210, 0, 164},
        new double[10] {188, 96, 174, 76, 67, 134, 64, 33, 55, 182},
        new double[10] {29, 47, 97, 83, 100, 2, 46, 199, 36, 185},
        new double[10] {55, 89, 233, 221, 127, 194, 48, 147, 213, 105 },
        new double[10] {86, 199, 15, 41, 131, 237, 48, 82, 45, 213},
        new double[10] {26, 188, 116, 14, 82, 96, 24, 173, 68, 238},
        new double[10] {46, 101, 106, 11, 129, 81, 21, 253, 252, 168},
        new double[10] {78, 105, 165, 8, 232, 81, 184, 91, 243, 69}
    };

    // create an ImageMatrix object with the above data
    ImageMatrix expectedMatrix1(expected_data1, 10, 10);

    // compare the convolved image with the expected image
    compare_images(im1_encoded, expectedMatrix1, score, total_number);

}

void test_decode_msg(double& score, int& total_number){
    const double* im_data1[6] = {
        new double[6] {150, 200, 250, 180, 210, 135},
        new double[6] {100, 250, 170, 185, 220, 140},
        new double[6] {80, 60, 90, 120, 130, 110},
        new double[6] {140, 210, 190, 160, 170, 150},
        new double[6] {130, 90, 100, 110, 200, 210},
        new double[6] {120, 130, 140, 150, 160, 170}
    };
    vector<pair<int, int>> EdgePixels1 = {
        make_pair(0, 1),
        make_pair(1, 2),
        make_pair(2, 0),
        make_pair(3, 3),
        make_pair(4, 4),
        make_pair(5, 5),
    };    
    // create an inout image
    ImageMatrix inputImage(im_data1, 6, 6);
    std::string expectedOutput1 = "!";

    DecodeMessage decodeMessage;
    std::string decodedMessage1 = decodeMessage.decodeFromImage(inputImage, EdgePixels1);
    if (!decodedMessage1.compare(expectedOutput1)) {
        score += 1.0;
    } 
    total_number++;    
}

void img_processor_encode(double& score, int& total_number){

    
    // Create an ImageProcessor object
    ImageProcessor imageProcessor;
    
    std::string messageToEncode = "Hello World!";

    // TEST 1
    // encodeHiddenMessage
    ImageMatrix encodedImage = imageProcessor.encodeHiddenMessage(image1, messageToEncode);

    const double* expected_data1[5] = {
        new double[5]{1, 2, 3, 4, 5},
        new double[5]{6, 7, 8, 9, 10},
        new double[5]{11, 12, 13, 14, 15},
        new double[5]{17, 17, 19, 18, 21},
        new double[5]{20, 23, 23, 24, 24}
    };    

    // compare the encoded image with the expected image
    ImageMatrix expectedImage1(expected_data1, 5, 5);
    compare_images(encodedImage, expectedImage1, score, total_number);    

}


void img_processor_decode(double& score, int& total_number){

    
    // Create an ImageProcessor object
    ImageProcessor imageProcessor;

    // TEST 1 
    // decodeHiddenMessage from testImage
    std::string decodedMessage1 = imageProcessor.decodeHiddenMessage(image1);

    std::string expectedOutput1 = "\"#";

    // compare the decoded message with the expected message
    if (!decodedMessage1.compare(expectedOutput1)) {
        score += 1.0;
    } 
    total_number++;   

}

int main() {
    double score = 0.0;
    int total_number = 0;    

    // test convolution
    test_conv(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;

    // test sharpening
    test_sharpening(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;

    // test edge detection
    test_edge_detection(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;

    

    // test decode message
    test_decode_msg(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;

    

    // test image processor's decoder
    img_processor_decode(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;

    // test encode message
    test_encode_mssg(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;

    // test image processor's encoder   
    img_processor_encode(score, total_number);

    cout << "Score: " << score << " / " << total_number << endl;
}