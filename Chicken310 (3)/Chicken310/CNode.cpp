#include"CNode.h"

void CNode::setCoord(float x, float y) {
	coord = pair<float, float>(x, y);
};

void CNode::showAllAdj(void)
{
	cout << "coordinate - (x = " << coord.first << ", y = " << coord.second << ")" << endl;
	for (auto v : adjList)
		cout << "adj List : " << v << endl;

	for (auto v : roomList)
		cout << "Room List : " << v << endl;
}

int CNode::Closest(int floor, int checker){
	int temp= -999;
	switch (checker)
	{
	case 1:
		for (int i = 0; i < 18; i++) {
			if (movable[i] == true && floor >= i - 5) {
				temp = i-5;
			}
		}
		break;
	case -1:
		for (int i = 17; i >= 0; i--) {
			if (movable[i] == true && floor <= i - 5) {
				temp = i-5;
			}
		}
		break;
	default:
		cout << "checker 입력 오류" << endl;
	}
	return temp;
	
}