# Variables
CXX = g++
CXXFLAGS = -g -std=c++11
SOURCES = main.cpp ApplicationLayerPacket.cpp Log.cpp NetworkLayerPacket.cpp PhysicalLayerPacket.cpp Client.cpp Network.cpp Packet.cpp TransportLayerPacket.cpp
HEADERS = ApplicationLayerPacket.h Log.h NetworkLayerPacket.h PhysicalLayerPacket.h Client.h Network.h Packet.h TransportLayerPacket.h
OBJECTS = $(SOURCES:.cpp=.o)
TARGET = hubbmnet

# Default rule to build the project
all: $(TARGET)

# Rule to build the main target
$(TARGET): $(OBJECTS)
	$(CXX) $(CXXFLAGS) -o $(TARGET) $(OBJECTS)

# Rule to compile source files to object files
%.o: %.cpp $(HEADERS)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Rule to clean the build artifacts
clean:
	rm -f $(OBJECTS) $(TARGET)

.PHONY: all clean