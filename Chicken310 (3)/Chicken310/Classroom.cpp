#include"Classroom.h"
#include<iostream>

void Classroom::printUpTable() {

	for (int i = 0; i < 6; i++) {
		for (int j = 0; j < 60; j++) {

			/*if (up[i][j] < 10) {
			cout << "0";
			}*/

			cout <<"["<<i<<"]"<<"["<<j<<"]: "<< up[i][j] << " ";
		}
		cout << endl;
	}
}

void Classroom::printDownTable() {

	for (int i = 0; i < 6; i++) {
		for (int j = 0; j < 60; j++) {

			/*if (up[i][j] < 10) {
			cout << "0";
			}*/

			cout << "[" << i << "]" << "[" << j << "]: " << down[i][j] << " ";
		}
		cout << endl;
	}
}