#ifndef CLASSROOM_H
#define CLASSROOM_H

#include<string>
using namespace std;

class Classroom{
	string name;
	int up[6][60];					//[요일][시간]
	int down[6][60];					//[요일][시간]

public:
	Classroom() {};
	Classroom(string name) { 
		this->name = name; 
		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 60; k++) {
				up[i][k] = 0;
				down[i][k] = 0;
			}
		}
	};
	string getName() { return name; };
	void setUP(int day, int start, int peopleNum) {
		up[day - 1][start] = peopleNum;
	};
	void setDown(int day, int end, int peopleNum) {
		down[day - 1][end] = peopleNum;
	};
	int getUP(int day, int time) {
		return up[day][time];
	};
	int getDown(int day, int time) {
		return down[day][time];
	};
	void printUpTable();
	void printDownTable();
};

#endif // !CLASSROOM_H