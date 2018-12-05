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

					if (row[0][0] == 'B')//지하층
						node->setFloor((1 - (int)atoi(&row[0][1]) /1000));
					else if (row[0][0] == '1') // 10층
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
			if (classList.find(row2[1]) == classList.end()) {	//list에 존재하지 않으면
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
	
	if (dNode.isStair|| sNode.isStair) {//계단 이동
		return computeStairTime(sNode, dNode);
	}
	else if (sNode.isElv && !(dNode.isElv))//엘레베이터 이동 (엘리베이터 -> 일반)
		return computeElvTime(sNode, dNode);
	else if ((dNode.isElv && !sNode.isElv))//엘리베이터 대기시간  (일반 -> 엘리베이터)
		return computeWaitingTime(sNode, dNode);
	else {//층 내 이동
		return computeTime(sNode, dNode);
	}
};

//걷기 이동 계산: 층 내 이동 및 계단 이동
float MyGraph::computeTime(CNode &c1, CNode &c2) {
	
	float velocity = 2.7f;	//임의의 걷기 속력 : 2.0f;
	
	pair<float, float> sCoord = c1.getCood();
	pair<float, float> dCoord = c2.getCood();
	//x, y 좌표에 따른 거리 계산
	return (sqrt(pow((sCoord.first - dCoord.first), 2) +
		pow((sCoord.second - dCoord.second), 2))) / velocity;
	
}

float MyGraph::computeStairTime(CNode &c1, CNode &c2) {
	if (c1.isStair == false && c2.isStair == true) {
		c2.setFloor(c1.getFloor());
		return 1.0f;		
	}
	else{
		if (checker == 1) {	//상행
			cout << c1.getFloor()<<"->"<<c2.getFloor() << endl;
			cout <<"상행 계단시간 " <<(c2.getFloor() - c1.getFloor()) * 22 * 1.4 << endl;
			if (c1.getFloor() == c2.getFloor())
				return INF;
			return (c2.getFloor() - c1.getFloor())* 22 *1.4;	//층간 이동 20초라고 가정
			
		}
		else if (checker == -1) {	//하행

			if (c1.getFloor() == c2.getFloor())
				return INF;
			cout << c1.getFloor() << "->" << c2.getFloor() << endl;
			cout << "하행 계단시간 " << (c1.getFloor() - c2.getFloor()) * 22 * 1.4 << endl;

			return (c1.getFloor() - c2.getFloor())*22;
		}

	}
			
}

float MyGraph::computeElvTime(CNode &c1, CNode &c2) { // floor 안쓸것 같음..
	
	int stopCount = 0; // 정차횟수
	if (c1.getFloor() == c2.getFloor())
	{
		cout<<c1.getName()<<" : " << c1.getFloor() << "->" <<c2.getName()<<" : "<<c2.getFloor() << " 같은층으로 가는 elv src dst" << endl;
		return INF; // INF를 return 해도 될것 같긴함
	}

	//src~dst사이에 정차하는 층에대한 계산
	if (c1.getFloor() < c2.getFloor()) // 목적지가 출발층보다 높을때. (상행)
	{
		for (int i = c1.getFloor() + 6; i < c2.getFloor() + 5; i++) // src 다음층(src+1)~ dst 층 구간에서 멈추는 곳(목적지층포함)
		{
			if (c1.checkMove(i)) // 멈추는층에 대해 승차인원 있는지 확인
				if (peopleNum[i][0] != 0)
					stopCount++;
		}

	}
	else // (하행)
	{
		for (int i = c1.getFloor() + 4; i < c2.getFloor() + 5; i--) // src(src-1) 다음층~ dst 층 구간에서 멈추는 곳(목적지층포함)
		{
			if (c1.checkMove(i)) { // 멈추는층에 대해 승차인원 있는지 확인
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
	float peoplePerMin = 15*60 / cycleTime; // 분당 처리인원수 

										 //c1기준 인원 정의후 대기 횟수 계산 하여, (cycleTime*(대기횟수 + 0.5))

	switch (checker)
	{

	case 1://상행 c1 층 기준 위의층의 up만 참조
		for (int i = c1.getFloor() + 6; i < 18; i++)
			if(elv.checkMove(i))
				carriedPeople += peopleNum[i][0] * (1/(float)getNumOfElv(i)); // 
		break;

	case -1: // 하행 c1 층 기준 위의층의 down 만 참조
		for (int i = c1.getFloor() + 5; i < 18; i++)
			if(elv.checkMove(i))
				carriedPeople += peopleNum[i][1] * (1/(float)getNumOfElv(i));
		break;

	case 0: // 층내이동 호출되면 안되는 부분
		cout << "층내이동에서 엘리베이터대기가 호출됨. error" << endl;
		exit(1);
		//return INF;
		break;

	default:
		cout << "호출전에 상하행이 지정되지 않음 error" << endl;
		exit(1);
		break;
	}
	// 인원 산정완료 이후에 인원 기준으로 몇명이 가능한지 계산 필요.


	// 전체인원/( 15분 동안 퍼내릴 수 있는 인원=(15*60/cycleTime)*15)


	cout <<c1.getName()<<", "<< elv.getName() <<"waiting Time"<< cycleTime * (carriedPeople / (peoplePerMin * 15) + 0.5)<< endl;
	return cycleTime * (carriedPeople / (peoplePerMin * 15) + 0.5);


}

float MyGraph::getCycleTime(CNode elv) { // 일주시간 계산


	list<string> stopList = elv.getAList();   //엘리베이터의 정차점 list
	list<string>::iterator it;
	list<int> floors;

	int highest = 0;
	int lowest = 0;   //최하층
	int tCount = 0;   //정차층 갯수

	for (it = stopList.begin(); it != stopList.end(); it++) {
		highest = getCNode(*it).getFloor();
		if (tCount == 0) {
			lowest = highest;
		}
		floors.push_front(highest);   //정차층리스트에 역순삽입
									  //cout<<"정차층: "<<mDist<<endl;
		tCount++;
	}

	//이동층은 highest - lowest


	//일주시간 계산
	//만약 up /down 인원이 없다면 해당 층에는 정차하지 않음.
	//단, 최하층에서는 무조건 탑승 인원이 있다고 간주.
	//따라서, down만 체크하면 일주시간을 계산할 수 있음.
	
	list<int>::iterator iiter = floors.begin();

	while (iiter != floors.end()) {
		int up = peopleNum[*iiter + 5][0];   //해당 층 up 인원
		int down = peopleNum[*iiter + 5][1];   //해당 층 down 인원
		if (up == 0 && down == 0)
			tCount--;
		iiter++;
	}
	//시간 공식   x:정차 n:이동 층수
	//t(x, n) = 12x + 3/2 (n- 1/3 x)

	float cycleTime = (12+30) * tCount - 0.5*tCount + 1.5 * (highest - lowest + 1);
	return cycleTime * 2;


}


//재정의, Mode2에 사용
//s는 시작 층
pair<int, int> MyGraph::computeWaitingTime(int s, CNode & elv)
{
	elv.setFloor(s);
	float cycleTime = getCycleTime(elv);
	int carriedPeople = 0;
	int waitingCount = 0;
	float peoplePerMin = 15 * 60 / cycleTime; // 분당 처리인원수 

											  //c1기준 인원 정의후 대기 횟수 계산 하여, (cycleTime*(대기횟수 + 0.5))

	switch (checker)
	{

	case 1://상행 c1 층 기준 위의층의 up만 참조
		for (int i = s + 6; i < 18; i++)
			if (elv.checkMove(i))
				carriedPeople += peopleNum[i][0] * (1/(float)numOfElv[i]); // 
		break;

	case -1: // 하행 c1 층 기준 위의층의 down 만 참조
		for (int i = s + 5; i < 18; i++)
			if (elv.checkMove(i))
				carriedPeople += peopleNum[i][1] * (1/(float)numOfElv[i]);
		break;

	case 0: // 층내이동 호출되면 안되는 부분
		cout << "층내이동에서 엘리베이터대기가 호출됨. error" << endl;
		exit(1);
		//return INF;
		break;

	default:
		cout << "호출전에 상하행이 지정되지 않음 error" << endl;
		exit(1);
		break;
	}
	// 인원 산정완료 이후에 인원 기준으로 몇명이 가능한지 계산 필요.


	// 전체인원/( 15분 동안 퍼내릴 수 있는 인원=(15*60/cycleTime)*15)


	//cout << s << " 층 " << elv.getName() << "waiting Time" << cycleTime *(carriedPeople / (peoplePerMin * 15) + 0.5) << endl;
	
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
			peopleNum[it->second.getFloor() + 5][0] += getClass(*iter).getUP(day, time);		//up 테이블
			peopleNum[it->second.getFloor() + 5][1] += getClass(*iter).getDown(day, time);		//down 테이블
		}
	}
	
	checker = c;

	map<string, pair<int, int>> N;
	int count = 0;
	for (map<string, CNode>::iterator iter = crossList.begin(); iter != crossList.end(); iter++) {
		if (iter->second.isElv) {
			//cout << iter->second.getName() << endl;
			N.insert(make_pair(iter->second.getName(),pair<int,int>(computeWaitingTime(iter->second.Closest(s,-1),iter->second))));
			//cout << "잉여 인원: " << computeWaitingTime(s, iter->second).first << " 엘베 이용 인원: " << computeWaitingTime(s, iter->second).second << endl;
			count++;
		}

	}
	//cout << "Num of Elv: " << count << endl;
	
	return N;


};

float MyGraph::Dijkstra(string src, string dst, vector<string> &path) {
		
	map<string, float> dist;			//교차점 별 최단 거리를 저장
	map<string, string> prevNode;
	//각 층의 up(0), down(1) 테이블
		//테이블 초기화
	for (int i = 0; i < 18; i++) {
		for (int k = 0; k < 2; k++) {
			peopleNum[i][k] = 0;
		}
	}
	for (map<string, CNode>::iterator it = crossList.begin(); it != crossList.end(); it++) {
		dist[it->first] = 9999999.0f;
		list<string> roomList = it->second.getCList();
		for (list<string>::iterator iter = roomList.begin(); iter != roomList.end(); iter++) {
			peopleNum[it->second.getFloor() + 5][0] += getClass(*iter).getUP(day, time);		//up 테이블
			peopleNum[it->second.getFloor() + 5][1] += getClass(*iter).getDown(day, time);		//down 테이블
		}
	}
	
	//목적지 층수
	int dFloor = getCNode(dst).getFloor();
	
	if (getCNode(src).getFloor() > getCNode(dst).getFloor()) {
		//하행
		checker = -1;
	}
	else if (getCNode(src).getFloor() == getCNode(dst).getFloor()) {
		//층내
		checker = 0;
	}
	else if (getCNode(src).getFloor() < getCNode(dst).getFloor()) {
		//상행
		checker = 1;
	}

	queue<pair<string, float>> q;
	q.push(make_pair(src, 0));


		while (!q.empty()) {
		//라운드 마다 방문했던 가중치가 가장 낮은 정점을 꺼내 비용과 정점을 반환
		string here = q.front().first;
		float weight = q.front().second;
		q.pop();
		
		//here에 해당하는 map 컨테이너의 원소 iterator 반환
		//해당 원소의 CNode의 인접 정점 리스트 
		list<string> adjList(crossList.find(here)->second.getAList());
		list<string>::iterator aiter;
		 	//인접 정점 이름
		string temp;
		string there;
		for (aiter = adjList.begin(); aiter != adjList.end(); aiter++) {
			string temp = *aiter;
			//there: 인접 정점의 이름
			//nextDist : 현재 정점까지의 가중치 + 현재 정점과 인접 정점 간 가중치
			if (checker == 0) {
				//엘베 or 계단이면 건너뜀
				if (getCNode(temp).isElv || getCNode(temp).isStair)
					continue;
			}
			else{
				if (getCNode(here).isElv || getCNode(here).isStair) {	//엘베(계단)-> 교차점
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