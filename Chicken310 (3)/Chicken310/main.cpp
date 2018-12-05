#include "Classroom.h"
#include "CNode.h"
#include "MyGraph.h"
#include "CSVRow.h"
#include<iostream>
using namespace std;

int main()
{
	//그래프 생성
	MyGraph myGraph;
	vector<string> path;


	//pair<잉여, 탑승>
	//잉여 = up or down 발생인원 - 엘레베이터 가용 인원
	map<string,pair<int, int>> result;
	
	
	int sumOfRemain = 0;
	
	int checker = 1;	//1 상행, -1 하행
	
	for (int i = 0; i < 6; i++) {
		for (int k = 0; k < 60; k++) {
			MyGraph::setDay(i);
			MyGraph::setTime(k);
			//cout << "Day: " << i << "Time: " << k << endl;
			result = myGraph.Mode2(-5, 12, checker);
		
			for (map<string, pair<int, int>>::iterator iter = result.begin(); iter != result.end(); iter++) {
				//cout << iter->first << endl;	//엘레베이터 이름
				//cout << "엘레베이터 탑승 가능 인원: " << iter->second.first << "  엘레베이터 잉여 인원: " << iter->second.second << endl;
				sumOfRemain += iter->second.second;
			}
		}
	}
	cout << "Sum: " << sumOfRemain << endl;

	
}

