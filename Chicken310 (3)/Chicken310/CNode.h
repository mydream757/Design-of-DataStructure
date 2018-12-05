#ifndef CNODE_H
#define CNODE_H
#include<list>
#include<map>
#include<iostream>
#include<string>
using namespace std;

class CNode {
	
	pair<float, float> coord;
	int floor;
	string name;
	list<string> adjList;	//인접 교차점의 이름 list
	list<string> roomList;
	
public:
	
	bool isStair;
	bool movable[18] = {0, };
	bool isElv;
	string getName() { return name; };
	void setMovable(bool b[18]) {
		for (int i = 0; i < 18; i++) {
			movable[i] = b[i];
		}
	};
	
	
	bool checkMove(int i) { return movable[i]; };
	list<string> getRoom() { return roomList; };
	CNode() {};
	CNode(string pname){
		name = pname;
	};
	inline int getFloor() { return floor; };
	inline void addAdj(string name) { adjList.push_back(name); };
	inline void addRoomList(string name) { roomList.push_back(name); };
	
	list<string> getAList() { return adjList; };
	list<string> getCList() { return roomList; };
	void setCoord(float x, float y);
	void setFloor(int i) { floor = i; };
	pair<float, float> getCood() { return coord; };
	void showAllAdj(void);
	int Closest(int floor, int checker);
};

#endif // !CNODE_H
