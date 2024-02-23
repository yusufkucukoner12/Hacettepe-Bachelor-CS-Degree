cmake_minimum_required(VERSION 3.22)
project(hubbmnet)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_FLAGS="-g -Wall")
add_executable(hubbmnet main.cpp Client.cpp ApplicationLayerPacket.cpp NetworkLayerPacket.cpp Packet.cpp PhysicalLayerPacket.cpp TransportLayerPacket.cpp Network.cpp Log.cpp)

# For linux, inside PA3 dir:
# mkdir linux_build
# cmake -S . -B linux_build/
# cd linux_build
# make