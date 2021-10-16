#include <iostream>
#include <fstream>
#include <windows.h>
#include <string>
#include <vector>
#include <time.h>

using namespace std;

//실행파일 이름(Path)
string file_name = "test.exe";


//input파일 개수
int cnt_input = 10;

//input파일 목록
vector<string> file_input = 
{
    "randinput0.txt",
    "randinput1.txt",
    "randinput2.txt",
    "randinput3.txt",
    "randinput4.txt",
    "randinput5.txt",
    "randinput6.txt",
    "randinput7.txt",
    "randinput8.txt",
    "randinput9.txt",
};

//output파일
string file_ouput = "output.txt";

//answer파일 목록
vector<string> file_answer =
{
    "answer.txt"
};

//모든 testcase의 결과가 같으면 true
bool same_answer = true;

//명령어 개수 (default: 1)
bool arg_cnt = 2;

//명령어 목록
vector< vector<string> > run_args = 
{
    //{"insertion"},
    {"merge"},
    //{"quick"},
    //{"heap"}
};

bool CheckError() {
    if(file_input.size() != cnt_input) {
        cout << "Error: 입력 파일의 개수가 맞지 않습니다.\ncnt_input: " << cnt_input << "\n입력된 파일 개수: " << file_input.size() << endl;
        return true;
    }
    if(!same_answer && file_answer.size() != cnt_input) {
        cout << "Error: 정답 파일의 개수가 맞지 않습니다.\ncnt_input: " << cnt_input << "\n입력된 파일 개수: " << file_answer.size() << "\n정답 종류: 개별\n";
        return true;
    }
    if(file_answer.size() < 1) {
        cout << "Error: 정답 파일을 입력해주세요.\n";
        return true;
    }
    return false;
}

void TestRun(int count, int arg) {
    //시간측정용(프로그램 전체)
    clock_t start, end;

    string filename = file_name;
    filename += " ";
    for(int i = 0; i < arg_cnt; i++) 
        filename += run_args[arg][i] + " ";
    filename += "< " + file_input[count] + " > testoutput.txt";
    cout << "명령어 : " << filename << endl;

    start = clock();
    system(filename.c_str());
    end = clock();
    cout << "실행시간: " << (double)(end - start) << "ms\n";

}

bool cmpFile(int outputNum = 0) {
    //출력 결과
    ifstream result("testoutput.txt");
    //정답
    ifstream answer(file_answer[outputNum]);
    
    if(!result.is_open()) {
        cout << "프로그램 에러: 출력이 정상적으로 되지 않았습니다.\n";
        return 0;
    }
    if(!answer.is_open()) {
        cout << "정답파일을 찾을 수 없습니다.\n";
        return 0;
    }
    
    vector<string> res_arr;
    string num;
    while(result >> num) {
        res_arr.push_back(num);
    }
    int cnt = 0;
    while(answer >> num) {
        if(res_arr[cnt++] != num) {
            cout << "틀렸습니다.\n결과: " << res_arr[cnt-1] << "\n정답: " << num << endl;
            return 0;
        }
    }
    cout << "실행완료.\n정답입니다!\n";
    while(cnt < res_arr.size()) {
        cout << res_arr[cnt++] << " ";
    }
    cout << "\n";
    result.close();
    answer.close();
    return true;
}

void RunTestCase(int argn) {
    int RunCount = 0;
    while(RunCount++ < cnt_input) {
        cout << RunCount << "번째 테스트케이스 실행\n";
        TestRun(RunCount-1, argn);
        if(!cmpFile()) break;
    }
}


int main(void) {
    if(CheckError()) return 0;
    cout << "=============== Start Test \"" << file_name << "\" ===============\n";
    for(int i = 0; i < run_args.size(); i++) {
        cout << run_args[i][0] << " 테스트 시작\n";
        RunTestCase(i);
    }
    cout << "=============== End Test \"" << file_name << "\" ===============\n";
    return 0;
}