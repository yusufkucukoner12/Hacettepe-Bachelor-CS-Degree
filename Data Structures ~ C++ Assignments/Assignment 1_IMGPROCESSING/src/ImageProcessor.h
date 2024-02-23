#ifndef IMAGE_PROCESSOR_H
#define IMAGE_PROCESSOR_H

#include "ImageSharpening.h"
#include "EdgeDetector.h"
#include "DecodeMessage.h"
#include "ImageMatrix.h"
#include "EncodeMessage.h"

#include <vector>
#include <utility>

class ImageProcessor {
    public:
        ImageProcessor(); 
        ~ImageProcessor(); 
        
        std::string decodeHiddenMessage(const ImageMatrix &img);
        ImageMatrix encodeHiddenMessage(const ImageMatrix &img, const std::string &message); 
        std::vector<std::pair<int,int>> edgePixels;

    private:
        
};

#endif // IMAGE_PROCESSOR_H



