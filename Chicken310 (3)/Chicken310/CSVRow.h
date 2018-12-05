#ifndef CSVROW_H
#define CSVROW_H

#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
using namespace std;

class CSVRow
{
private:
	vector<string>    m_data;

public:
	string const& operator[](size_t index) const { return m_data[index]; }
	size_t size() const { return m_data.size(); }
	void readNextRow(istream& str);


};

istream& operator>>(istream& str, CSVRow& data);

#endif // !CSVROW_H