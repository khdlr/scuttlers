#ifndef SCUTTLE_CALC
#define SCUTTLE_CALC

#include <algorithm>
#include "config.hpp"
#include <iostream>

#define E(x,y) ((x + 1)%WIDTH + y*WIDTH) 
#define W(x,y) ((x + WIDTH - 1)%WIDTH + y*WIDTH) 
#define N(x,y) (x + ((y + HEIGHT - 1)%HEIGHT)*WIDTH) 
#define S(x,y) (x + ((y + 1)%HEIGHT)*WIDTH) 

#define NE(x,y) ((x + 1)%WIDTH + 		 ((y + HEIGHT - 1) % HEIGHT)*WIDTH) 
#define NW(x,y) ((x + WIDTH - 1)%WIDTH + ((y + HEIGHT - 1) % HEIGHT)*WIDTH) 
#define SE(x,y) ((x + 1)%WIDTH + 		 ((y + 1) % HEIGHT)*WIDTH) 
#define SW(x,y) ((x + WIDTH - 1)%WIDTH + ((y + 1) % HEIGHT)*WIDTH) 
	
inline char direct(char state, char* oldmap, int x, int y) {
	return (oldmap[N(x,y)] == state) +
	       (oldmap[W(x,y)] == state) +
	       (oldmap[E(x,y)] == state) +
	       (oldmap[S(x,y)] == state);
};

inline char cross(char state, char* oldmap, int x, int y) {
	return (oldmap[NW(x,y)] == state) +
	       (oldmap[NE(x,y)] == state) +
	       (oldmap[SW(x,y)] == state) +
	       (oldmap[SE(x,y)] == state);
}
inline char total(char state, char* oldmap, int x, int y) {
	return (oldmap[NW(x,y)] == state) +
	   	   (oldmap[ N(x,y)] == state) +
	       (oldmap[NE(x,y)] == state) +
	       (oldmap[ W(x,y)] == state) +
		   (oldmap[ E(x,y)] == state) +
	       (oldmap[SW(x,y)] == state) +
	       (oldmap[ S(x,y)] == state) +
	       (oldmap[SE(x,y)] == state);
};

inline char getGreatestDirect(char* oldmap, int x, int y) {
	return std::max({
		(oldmap[N(x,y)]),
		(oldmap[E(x,y)]),
		(oldmap[W(x,y)]),
		(oldmap[S(x,y)])
	});
};

inline char getGreatestCross (char* oldmap, int x, int y) {
	return std::max({
		(oldmap[NW(x,y)]),
		(oldmap[NE(x,y)]),
		(oldmap[SW(x,y)]),
		(oldmap[SE(x,y)])
	});
};

inline char getGreatestTotal (char* oldmap, int x, int y) {
	return std::max({
		(oldmap[N(x,y)]),
		(oldmap[E(x,y)]),
		(oldmap[W(x,y)]),
		(oldmap[S(x,y)]),
		(oldmap[NW(x,y)]),
		(oldmap[NE(x,y)]),
		(oldmap[SW(x,y)]),
		(oldmap[SE(x,y)])
	});
}

inline char getSmallestDirect(char* oldmap, int x, int y) {
	return std::min({
		(oldmap[N(x,y)]),
		(oldmap[E(x,y)]),
		(oldmap[W(x,y)]),
		(oldmap[S(x,y)])
	});
};

inline char getSmallestCross (char* oldmap, int x, int y) {
	return std::min({
		(oldmap[NW(x,y)]),
		(oldmap[NE(x,y)]),
		(oldmap[SW(x,y)]),
		(oldmap[SE(x,y)])
	});
};

inline char getSmallestTotal (char* oldmap, int x, int y) {
	return std::min({
		(oldmap[N(x,y)]),
		(oldmap[E(x,y)]),
		(oldmap[W(x,y)]),
		(oldmap[S(x,y)]),
		(oldmap[NW(x,y)]),
		(oldmap[NE(x,y)]),
		(oldmap[SW(x,y)]),
		(oldmap[SE(x,y)])
	});
}

#endif // SCUTTLE_CALC