#include "Classroom.h"
#include "CNode.h"
#include "MyGraph.h"
#include "CSVRow.h"
#include<iostream>
using namespace std;

int main()
{
	//�׷��� ����
	MyGraph myGraph;
	vector<string> path;


	//pair<�׿�, ž��>
	//�׿� = up or down �߻��ο� - ���������� ���� �ο�
	map<string,pair<int, int>> result;
	
	
	int sumOfRemain = 0;
	
	int checker = 1;	//1 ����, -1 ����
	
	for (int i = 0; i < 6; i++) {
		for (int k = 0; k < 60; k++) {
			MyGraph::setDay(i);
			MyGraph::setTime(k);
			//cout << "Day: " << i << "Time: " << k << endl;
			result = myGraph.Mode2(-5, 12, checker);
		
			for (map<string, pair<int, int>>::iterator iter = result.begin(); iter != result.end(); iter++) {
				//cout << iter->first << endl;	//���������� �̸�
				//cout << "���������� ž�� ���� �ο�: " << iter->second.first << "  ���������� �׿� �ο�: " << iter->second.second << endl;
				sumOfRemain += iter->second.second;
			}
		}
	}
	cout << "Sum: " << sumOfRemain << endl;

	
}

