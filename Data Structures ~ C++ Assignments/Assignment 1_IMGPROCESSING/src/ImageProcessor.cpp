#include "ImageProcessor.h"
#include <iostream>
ImageProcessor::ImageProcessor() {
    
}

ImageProcessor::~ImageProcessor() {
    
}


std::string ImageProcessor::decodeHiddenMessage(const ImageMatrix &img) {
    ImageSharpening imagesharp;
    int k = 2;
    ImageMatrix sharpImage = imagesharp.sharpen(img,k);

    EdgeDetector findEdge;
    edgePixels = findEdge.detectEdges(sharpImage);

    DecodeMessage decodeMessage;


    

    
    
    
    
    return decodeMessage.decodeFromImage(sharpImage, edgePixels);
    
}

ImageMatrix ImageProcessor::encodeHiddenMessage(const ImageMatrix &img, const std::string &message) {
    EncodeMessage encodeHiddenMessage; 

    return encodeHiddenMessage.encodeMessageToImage(img,message,edgePixels);
}
