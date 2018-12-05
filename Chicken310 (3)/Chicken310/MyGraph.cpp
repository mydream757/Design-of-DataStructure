#include"MyGraph.h"
#include<cmath>
#include<vector>
#include<queue>
using namespace std;

#define INF 9999999
int MyGraph::day = 0;
int MyGraph::time = 0;

MyGraph::MyGraph() {
	ifstream file("NodeData.csv");
	CSVRow row;
	if (file >> row) //read first line ( label )
	{
		cout << "First Label" << row[0] << endl;
		cout << "Second Label" << row[1] << endl;
		cout << "Third Label" << row[2] << endl;
		cout << "Fourth Label" << row[3] << endl;

		while (file >> row) // after first row  ( actual data )
		{
			/*----------------------------------------

			create CNode using row[0]

			----------------------------------------*/
			CNode * node = new CNode(row[0]);
			if (row[0][0] == 'E') {
				node->isElv = true;
				node->isStair = false;
			}
			else if (row[0][0] == 'S') {
				node->isElv = false;
				node->isStair = true;
			}
			else {
				if (row[0].length() == 4) {
					node->setFloor(row[0][0] - '0');
				}
				else if (row[0].length() == 5) {

					if (row[0][0] == 'B')//������
						node->setFloor((1 - (int)atoi(&row[0][1]) /1000));
					else if (row[0][0] == '1') // 10��
						node->setFloor((row[0][0] - '0') * 10 + row[0][1] - '0');
					else
						cout << "5 letter node data wrong check again : " << row[0] << row[1] << row[2] << row[3] << endl;
				}
				node->isElv = false;
				node->isStair = false;
			}



			/*----------------------------------------

			add adjNode using row[1]

			----------------------------------------*/
			string adj = row[1]; // a ; b ; c...
							// Vector of string to save tokens 
			vector <string> atokens;

			// stringstream class check1 
			stringstream check1(adj);
			string intermediate;
			// Tokenizing w.r.t. space ' ' 
			while (getline(check1, intermediate, ';'))
				atokens.push_back(intermediate);

			// Printing the token vector 
			for (int i = 0; i < atokens.size(); i++)
				node->addAdj(atokens[i]);

			/*----------------------------------------

			add classroom using row[2]

			----------------------------------------*/
			string rooms = row[2]; // a ; b ; c
			vector <string> rtokens;
			stringstream check2(rooms);
			while (getline(check2, intermediate, ';'))
				rtokens.push_back(intermediate);
			for (int i = 0; i < rtokens.size(); i++) {
				node->addRoomList(rtokens[i]);
				Classroom *c = new Classroom(rtokens[i]);
				insertClass(rtokens[i], *c);

			}
			/*----------------------------------------

			add Coordinate using row[3]

			----------------------------------------*/
			string coord = row[3]; // x ;y
			stringstream check3(coord);
			getline(check3, intermediate, ';');
			float x = stof(intermediate);
			getline(check3, intermediate, ';');
			float y = stof(intermediate);
			node->setCoord(x, y);

			/*----------------------------------------
			set movable using row[4]
			-----------------------------------------*/
			string movable = row[4]; // a ; b ; c
			vector <string> rtokens2;
			stringstream check4(movable);
			while (getline(check4, intermediate, ';'))
				rtokens2.push_back(intermediate);
			for (int i = 0; i < rtokens2.size(); i++) {
				node->movable[stoi(rtokens2[i]) + 5] = true;

			}


			/*----------------------------------------
			insert Node in the graph
			----------------------------------------*/
			insertCross(row[0], *node);
		}


	}
	else
		cout << "The data file has problem." << endl;

	/*--------------------------------------------
	initialize classroom data
	--------------------------------------------*/
	ifstream file2("sortedData.csv");
	CSVRow row2;
	if (file2 >> row2) //read first line ( label )
	{
		/*
		read label
		*/
		cout << "First Label" << row2[0] << endl;
		cout << "Second Label" << row2[1] << endl;
		cout << "Third Label" << row2[2] << endl;
		cout << "Fourth Label" << row2[3] << endl;
		cout << "Fifth Label" << row2[4] << endl;

		while (file2 >> row2) // after first row  ( actual data )
		{
			/*----------------------------------------

			create CNode using row[0]

			----------------------------------------*/
			
			Classroom *temp;
			if (classList.find(row2[1]) == classList.end()) {	//list�� �������� ������
				temp  = new Classroom(row2[1]);
				insertClass(row2[1], *temp);
			}
			
			temp = &classList.find(row2[1])->second;
			
			//input: day, start, peopleNum
			temp->setUP(atoi(row2[0].c_str()), atoi(row2[2].c_str()), atoi(row2[4].c_str()));
			temp->setDown(atoi(row2[0].c_str()), atoi(row2[3].c_str()), atoi(row2[4].c_str()));
			
						
		}

	}
	else
		cout << "The data file has problem." << endl;

	setNumOfElv();
		
}

float MyGraph::getWeight(string src, string dst) {
	CNode &sNode = getCNode(src);
	CNode &dNode = getCNode(dst);
	cout << "dijkstra src " << src << ", dst " << dst << endl;
	
	if (dNode.isStair|| sNode.isStair) {//��� �̵�
		return computeStairTime(sNode, dNode);
	}
	else if (sNode.isElv && !(dNode.isElv))//���������� �̵� (���������� -> �Ϲ�)
		return computeElvTime(sNode, dNode);
	else if ((dNode.isElv && !sNode.isElv))//���������� ���ð�  (�Ϲ� -> ����������)
		return computeWaitingTime(sNode, dNode);
	else {//�� �� �̵�
		return computeTime(sNode, dNode);
	}
};

//�ȱ� �̵� ���: �� �� �̵� �� ��� �̵�
float MyGraph::computeTime(CNode &c1, CNode &c2) {
	
	float velocity = 2.7f;	//������ �ȱ� �ӷ� : 2.0f;
	
	pair<float, float> sCoord = c1.getCood();
	pair<float, float> dCoord = c2.getCood();
	//x, y ��ǥ�� ���� �Ÿ� ���
	return (sqrt(pow((sCoord.first - dCoord.first), 2) +
		pow((sCoord.second - dCoord.second), 2))) / velocity;
	
}

float MyGraph::computeStairTime(CNode &c1, CNode &c2) {
	if (c1.isStair == false && c2.isStair == true) {
		c2.setFloor(c1.getFloor());
		return 1.0f;		
	}
	else{
		if (checker == 1) {	//����
			cout << c1.getFloor()<<"->"<<c2.getFloor() << endl;
			cout <<"���� ��ܽð� " <<(c2.getFloor() - c1.getFloor()) * 22 * 1.4 << endl;
			if (c1.getFloor() == c2.getFloor())
				return INF;
			return (c2.getFloor() - c1.getFloor())* 22 *1.4;	//���� �̵� 20�ʶ�� ����
			
		}
		else if (checker == -1) {	//����

			if (c1.getFloor() == c2.getFloor())
				return INF;
			cout << c1.getFloor() << "->" << c2.getFloor() << endl;
			cout << "���� ��ܽð� " << (c1.getFloor() - c2.getFloor()) * 22 * 1.4 << endl;

			return (c1.getFloor() - c2.getFloor())*22;
		}

	}
			
}

float MyGraph::computeElvTime(CNode &c1, CNode &c2) { // floor �Ⱦ��� ����..
	
	int stopCount = 0; // ����Ƚ��
	if (c1.getFloor() == c2.getFloor())
	{
		cout<<c1.getName()<<" : " << c1.getFloor() << "->" <<c2.getName()<<" : "<<c2.getFloor() << " ���������� ���� elv src dst" << endl;
		return INF; // INF�� return �ص� �ɰ� ������
	}

	//src~dst���̿� �����ϴ� �������� ���
	if (c1.getFloor() < c2.getFloor()) // �������� ��������� ������. (����)
	{
		for (int i = c1.getFloor() + 6; i < c2.getFloor() + 5; i++) // src ������(src+1)~ dst �� �������� ���ߴ� ��(������������)
		{
			if (c1.checkMove(i)) // ���ߴ����� ���� �����ο� �ִ��� Ȯ��
				if (peopleNum[i][0] != 0)
					stopCount++;
		}

	}
	else // (����)
	{
		for (int i = c1.getFloor() + 4; i < c2.getFloor() + 5; i--) // src(src-1) ������~ dst �� �������� ���ߴ� ��(������������)
		{
			if (c1.checkMove(i)) { // ���ߴ����� ���� �����ο� �ִ��� Ȯ��
				if (peopleNum[i][1] != 0)
					stopCount++;
			}
		}
	}

	float time = 15+ 12 * stopCount - 0.5*stopCount + 1.5 *abs(c2.getFloor()-c1.getFloor());


	cout <<c1.getName() << ", " << c2.getName() << "elv Time : "<< time << endl;
	return time;

};

float MyGraph::computeWaitingTime(CNode & c1, CNode & elv)
{
	elv.setFloor(c1.getFloor());
	float cycleTime = getCycleTime(elv);
	int carriedPeople = 0;
	int waitingCount = 0;
	float peoplePerMin = 15*60 / cycleTime; // �д� ó���ο��� 

										 //c1���� �ο� ������ ��� Ƚ�� ��� �Ͽ�, (cycleTime*(���Ƚ�� + 0.5))

	switch (checker)
	{

	case 1://���� c1 �� ���� �������� up�� ����
		for (int i = c1.getFloor() + 6; i < 18; i++)
			if(elv.checkMove(i))
				carriedPeople += peopleNum[i][0] * (1/(float)getNumOfElv(i)); // 
		break;

	case -1: // ���� c1 �� ���� �������� down �� ����
		for (int i = c1.getFloor() + 5; i < 18; i++)
			if(elv.checkMove(i))
				carriedPeople += peopleNum[i][1] * (1/(float)getNumOfElv(i));
		break;

	case 0: // �����̵� ȣ��Ǹ� �ȵǴ� �κ�
		cout << "�����̵����� ���������ʹ�Ⱑ ȣ���. error" << endl;
		exit(1);
		//return INF;
		break;

	default:
		cout << "ȣ������ �������� �������� ���� error" << endl;
		exit(1);
		break;
	}
	// �ο� �����Ϸ� ���Ŀ� �ο� �������� ����� �������� ��� �ʿ�.


	// ��ü�ο�/( 15�� ���� �۳��� �� �ִ� �ο�=(15*60/cycleTime)*15)


	cout <<c1.getName()<<", "<< elv.getName() <<"waiting Time"<< cycleTime * (carriedPeople / (peoplePerMin * 15) + 0.5)<< endl;
	return cycleTime * (carriedPeople / (peoplePerMin * 15) + 0.5);


}

float MyGraph::getCycleTime(CNode elv) { // ���ֽð� ���


	list<string> stopList = elv.getAList();   //������������ ������ list
	list<string>::iterator it;
	list<int> floors;

	int highest = 0;
	int lowest = 0;   //������
	int tCount = 0;   //������ ����

	for (it = stopList.begin(); it != stopList.end(); it++) {
		highest = getCNode(*it).getFloor();
		if (tCount == 0) {
			lowest = highest;
		}
		floors.push_front(highest);   //����������Ʈ�� ��������
									  //cout<<"������: "<<mDist<<endl;
		tCount++;
	}

	//�̵����� highest - lowest


	//���ֽð� ���
	//���� up /down �ο��� ���ٸ� �ش� ������ �������� ����.
	//��, ������������ ������ ž�� �ο��� �ִٰ� ����.
	//����, down�� üũ�ϸ� ���ֽð��� ����� �� ����.
	
	list<int>::iterator iiter = floors.begin();

	while (iiter != floors.end()) {
		int up = peopleNum[*iiter + 5][0];   //�ش� �� up �ο�
		int down = peopleNum[*iiter + 5][1];   //�ش� �� down �ο�
		if (up == 0 && down == 0)
			tCount--;
		iiter++;
	}
	//�ð� ����   x:���� n:�̵� ����
	//t(x, n) = 12x + 3/2 (n- 1/3 x)

	float cycleTime = (12+30) * tCount - 0.5*tCount + 1.5 * (highest - lowest + 1);
	return cycleTime * 2;


}


//������, Mode2�� ���
//s�� ���� ��
pair<int, int> MyGraph::computeWaitingTime(int s, CNode & elv)
{
	elv.setFloor(s);
	float cycleTime = getCycleTime(elv);
	int carriedPeople = 0;
	int waitingCount = 0;
	float peoplePerMin = 15 * 60 / cycleTime; // �д� ó���ο��� 

											  //c1���� �ο� ������ ��� Ƚ�� ��� �Ͽ�, (cycleTime*(���Ƚ�� + 0.5))

	switch (checker)
	{

	case 1://���� c1 �� ���� �������� up�� ����
		for (int i = s + 6; i < 18; i++)
			if (elv.checkMove(i))
				carriedPeople += peopleNum[i][0] * (1/(float)numOfElv[i]); // 
		break;

	case -1: // ���� c1 �� ���� �������� down �� ����
		for (int i = s + 5; i < 18; i++)
			if (elv.checkMove(i))
				carriedPeople += peopleNum[i][1] * (1/(float)numOfElv[i]);
		break;

	case 0: // �����̵� ȣ��Ǹ� �ȵǴ� �κ�
		cout << "�����̵����� ���������ʹ�Ⱑ ȣ���. error" << endl;
		exit(1);
		//return INF;
		break;

	default:
		cout << "ȣ������ �������� �������� ���� error" << endl;
		exit(1);
		break;
	}
	// �ο� �����Ϸ� ���Ŀ� �ο� �������� ����� �������� ��� �ʿ�.


	// ��ü�ο�/( 15�� ���� �۳��� �� �ִ� �ο�=(15*60/cycleTime)*15)


	//cout << s << " �� " << elv.getName() << "waiting Time" << cycleTime *(carriedPeople / (peoplePerMin * 15) + 0.5) << endl;
	
	int stairPeople = 0;
	if ((peoplePerMin) * 15 < carriedPeople) {
		stairPeople = carriedPeople - peoplePerMin*15;
	}
	return pair<int, int>((int)peoplePerMin * 15, stairPeople);


}
map<string, pair<int, int>> MyGraph::Mode2(int s, int d, int c) {
	for (int i = 0; i < 18; i++) {
		for (int k = 0; k < 2; k++) {
			peopleNum[i][k] = 0;
		}
	}
	for (map<string, CNode>::iterator it = crossList.begin(); it != crossList.end(); it++) {
		list<string> roomList = it->second.getCList();
		for (list<string>::iterator iter = roomList.begin(); iter != roomList.end(); iter++) {
			peopleNum[it->second.getFloor() + 5][0] += getClass(*iter).getUP(day, time);		//up ���̺�
			peopleNum[it->second.getFloor() + 5][1] += getClass(*iter).getDown(day, time);		//down ���̺�
		}
	}
	
	checker = c;

	map<string, pair<int, int>> N;
	int count = 0;
	for (map<string, CNode>::iterator iter = crossList.begin(); iter != crossList.end(); iter++) {
		if (iter->second.isElv) {
			//cout << iter->second.getName() << endl;
			N.insert(make_pair(iter->second.getName(),pair<int,int>(computeWaitingTime(iter->second.Closest(s,-1),iter->second))));
			//cout << "�׿� �ο�: " << computeWaitingTime(s, iter->second).first << " ���� �̿� �ο�: " << computeWaitingTime(s, iter->second).second << endl;
			count++;
		}

	}
	//cout << "Num of Elv: " << count << endl;
	
	return N;


};

float MyGraph::Dijkstra(string src, string dst, vector<string> &path) {
		
	map<string, float> dist;			//������ �� �ִ� �Ÿ��� ����
	map<string, string> prevNode;
	//�� ���� up(0), down(1) ���̺�
		//���̺� �ʱ�ȭ
	for (int i = 0; i < 18; i++) {
		for (int k = 0; k < 2; k++) {
			peopleNum[i][k] = 0;
		}
	}
	for (map<string, CNode>::iterator it = crossList.begin(); it != crossList.end(); it++) {
		dist[it->first] = 9999999.0f;
		list<string> roomList = it->second.getCList();
		for (list<string>::iterator iter = roomList.begin(); iter != roomList.end(); iter++) {
			peopleNum[it->second.getFloor() + 5][0] += getClass(*iter).getUP(day, time);		//up ���̺�
			peopleNum[it->second.getFloor() + 5][1] += getClass(*iter).getDown(day, time);		//down ���̺�
		}
	}
	
	//������ ����
	int dFloor = getCNode(dst).getFloor();
	
	if (getCNode(src).getFloor() > getCNode(dst).getFloor()) {
		//����
		checker = -1;
	}
	else if (getCNode(src).getFloor() == getCNode(dst).getFloor()) {
		//����
		checker = 0;
	}
	else if (getCNode(src).getFloor() < getCNode(dst).getFloor()) {
		//����
		checker = 1;
	}

	queue<pair<string, float>> q;
	q.push(make_pair(src, 0));


		while (!q.empty()) {
		//���� ���� �湮�ߴ� ����ġ�� ���� ���� ������ ���� ���� ������ ��ȯ
		string here = q.front().first;
		float weight = q.front().second;
		q.pop();
		
		//here�� �ش��ϴ� map �����̳��� ���� iterator ��ȯ
		//�ش� ������ CNode�� ���� ���� ����Ʈ 
		list<string> adjList(crossList.find(here)->second.getAList());
		list<string>::iterator aiter;
		 	//���� ���� �̸�
		string temp;
		string there;
		for (aiter = adjList.begin(); aiter != adjList.end(); aiter++) {
			string temp = *aiter;
			//there: ���� ������ �̸�
			//nextDist : ���� ���������� ����ġ + ���� ������ ���� ���� �� ����ġ
			if (checker == 0) {
				//���� or ����̸� �ǳʶ�
				if (getCNode(temp).isElv || getCNode(temp).isStair)
					continue;
			}
			else{
				if (getCNode(here).isElv || getCNode(here).isStair) {	//����(���)-> ������
					if (getCNode(here).Closest(dFloor, checker) != getCNode(temp).getFloor()) {
						continue;
					}

				}
			}
			there = temp;
			
			float nextWeight = weight + getWeight(here, there);
			if (dist.find(there)->second > nextWeight) {
				dist.find(there)->second = nextWeight;
				prevNode[there] = here;
				q.push(make_pair(there, nextWeight));
			}
		}
	}
	string tmp = dst;
	path.push_back(tmp);
	while (tmp != src) {
		if (tmp == prevNode[tmp])
			break;
		cout << tmp << endl;
		tmp = prevNode[tmp];
		path.push_back(tmp);
	}
	return dist.find(dst)->second;
};
void MyGraph::showAllMyClass() {
	map<string, Classroom>::iterator iter;
	for (iter = classList.begin(); iter != classList.end(); iter++) {
		cout << "name: " << iter->first << endl;
		iter->second.printUpTable();
		iter->second.printDownTable();
		cout << "---------------" << endl;
	}
};

void MyGraph::showAllMyGraph() //for debugging
{
	for (std::map<string, CNode>::iterator it = crossList.begin(); it != crossList.end(); ++it)
	{
		cout << "key : " << it->first << endl;
		cout << "floor : " << it->second.getFloor() << endl;
		cout << "Elv: " << it->second.isElv << endl;
		cout << "Stair: " << it->second.isStair << endl;
		
		it->second.showAllAdj();

	}
}