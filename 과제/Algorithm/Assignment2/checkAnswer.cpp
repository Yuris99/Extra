#include <iostream>
#include <fstream>
#include <string>


using namespace std;

string getfile = "algoresult.txt";
string answerfile = "answer.txt";

int main(void) {
    ifstream res(getfile);
    ifstream ans(answerfile);
    if(res.fail() || ans.fail()) {
        cerr << "파일을 찾을 수 없습니다." << endl;
        exit(100);
    }
    string str1, str2;
    while(!res.eof() && !ans.eof()) {
        res >> str1;
        ans >> str2;
        if(str1.compare(str2)) {
            cout << "틀렸습니다.\n" << endl;
            return 0;
        }
    }

    res.close();
    ans.close();
    return 0;
}