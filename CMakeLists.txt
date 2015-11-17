cmake_minimum_required(VERSION 2.8)
project(scuttlers)

set( CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11" )
set( CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake/sdl2-cmake-scripts )

find_package( SDL2 REQUIRED sdl2 )

include_directories(
    ${SDL2_INCLUDE_DIR}
)

file(GLOB_RECURSE Headers cpp-src/*.hpp)
file(GLOB_RECURSE Sources cpp-src/*.cpp)

add_executable(scuttlers ${Headers} ${Sources})
target_link_libraries(
    scuttlers
    ${SDL2_LIBRARY}
)