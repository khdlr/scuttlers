#include "SDL.h"
#include <iterator>
#include "calc.hpp"
#include <iostream>

SDL_Window* window = NULL;
SDL_Surface* surf = NULL;
SDL_Event event;

int i = 0;

char map[WIDTH * HEIGHT], oldmap[WIDTH * HEIGHT];
Uint32 colors[NUM_STATES];

// SDL_MapRGB( screen->format, r, g, b );

int main() {

	SDL_Init(SDL_INIT_VIDEO);
	window = SDL_CreateWindow("Galaxy", SDL_WINDOWPOS_UNDEFINED,
		SDL_WINDOWPOS_UNDEFINED, WIDTH, HEIGHT, SDL_WINDOW_SHOWN);

	surf = SDL_GetWindowSurface(window);

	colors[0] = SDL_MapRGB( surf->format,   0,   0,   0 );
	colors[1] = SDL_MapRGB( surf->format, 255, 255,   0 );
	colors[2] = SDL_MapRGB( surf->format, 255,   0,   0 );
	colors[3] = SDL_MapRGB( surf->format,   0, 255, 255 );

	for(int y = 0; y < HEIGHT; y++) {
		for(int x = 0; x < WIDTH; x++) {
			map[x + WIDTH * y] = rand() % NUM_STATES;
		}
	}

	bool quit = false;
	while (!quit) {
		while (SDL_PollEvent(&event) > 0)
			if (event.type == SDL_QUIT)
				quit = true;

		i = 0;

		// Display everything
		SDL_LockSurface(surf);
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				((Uint32*) surf->pixels)[x + WIDTH*y] = colors[map[x + WIDTH * y]];
			}
		}
		SDL_UnlockSurface(surf);
		SDL_UpdateWindowSurface(window);

		// Do the simulation
		std::copy(std::begin(map), std::end(map), std::begin(oldmap));
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				switch(oldmap[x + WIDTH*y]) {
					case 0:
						if(total(1, oldmap, x,y) == 3) {
							map[x + WIDTH*y] = 1;
						}
						break;
					case 1:
						if((total(1, oldmap, x,y)& 0xFE) != 2 ) {
							map[x + WIDTH *y] = 0;
						}
						break;
				}
			}
		}

	}

}