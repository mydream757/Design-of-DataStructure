#ifndef MYGRAPH_H
#define MYGRAPH_H

#include<map>
#include<string>
#include"CNode.h"
#include"CSVRow.h"
#include"Classroom.h"
using namespace std;


class MyGraph {
	static int day;
	static int time;
	map<string, CNode> crossList;
	map<string, Classroom> classList;
	int numOfElv [18];

public:
	int peopleNum[18][2];
	int checker = -310;
	static void setDay(int d) { MyGraph::day = d; };
	static void setTime(int t) {MyGraph::time = t; };
	static void Input(int hour, int minute) {
		//입력시간을 index로 환산해야함.
		//시/분으로 입력
		MyGraph::time = (hour - 8) * 60 / 15 + minute / 15;
		

	};
	MyGraph();//initialize using CSV file.
	~MyGraph() {}; //delete crossList map<>;
	CNode& getCNode(string name) { return crossList.find(name)->second; };
	Classroom& getClass(string name) { return classList.find(name)->second; };
	//bool getElv(string name, CNode& node);
	void insertCross(string name, CNode point) { crossList.insert(pair<string, CNode>(name, point)); };
	void insertClass(string name, Classroom room) { classList.insert(pair<string, Classroom>(name, room)); };
	int getNumOfElv(int i) { return numOfElv[i]; };
	void setNumOfElv() {

		for (int i = 0; i < 18; i++) {
			numOfElv[i] = 0;
		}
		for (map<string, CNode>::iterator iter = crossList.begin(); iter != crossList.end(); iter++) {
			if (iter->second.isElv == true) {
				for (int i = 0; i < 18; i++) {
					if (iter->second.checkMove(i) == true)
						numOfElv[i] += 1;
				}

			}
			
		}
	}
	float getWeight(string src, string dst);
	float computeTime(CNode &c1, CNode &c2);
	float computeStairTime(CNode &c1, CNode &c2);
	float computeElvTime(CNode &c1, CNode &c2);
	float computeWaitingTime(CNode & c1, CNode & elv);
	pair<int, int> computeWaitingTime(int s, CNode & elv);
	map<string, pair<int, int>> Mode2(int s, int d, int c);
	float getCycleTime(CNode elv);
	
	float Dijkstra(string src, string dst, vector<string> &path);
	void showAllMyGraph(); // for debugging
	void showAllMyClass(); // for debugging
};

#endif // !MYGRAPH_H
