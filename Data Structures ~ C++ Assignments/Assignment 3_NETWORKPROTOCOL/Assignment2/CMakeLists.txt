cmake_minimum_required(VERSION 3.22)
project(blockfall)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g")
set(CMAKE_CXX_STANDARD 11)
add_executable(blockfall main.cpp BlockFall.h BlockFall.cpp Block.h GameController.cpp GameController.h LeaderboardEntry.h LeaderboardEntry.cpp Leaderboard.cpp Leaderboard.h)

# For linux, inside PA2 dir:
# mkdir linux_build
# cmake -S . -B linux_build/
# cd linux_build
# make