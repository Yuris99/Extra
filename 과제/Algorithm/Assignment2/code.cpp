#include <iostream>
#include <string>

using namespace std;

int input, BIT = 0;

void add(int x) {
    BIT |= 1 << x;
}
void remove(int x) {
    BIT &= ~(1 << x);
}
void check(int x) {
    if(BIT & (1 << x))
        cout << "1\n";
    else
        cout << "0\n";
}
void toggle(int x) {
    BIT ^= 1 << x;
}
void all() {
    BIT = (1 < 21) - 1;
}
void empty() {
    BIT = 0;

}

int main(void) {
    int m;
    cin >> m;
    while(m--) {
        string cmd;
        cin >> cmd;
        if(!cmd.compare("add")) {
            cin >> input;
            add(input);
        } else if(!cmd.compare("remove")) {
            cin >> input;
            remove(input);
        } else if(!cmd.compare("check")) {
            cin >> input;
            check(input);
        } else if(!cmd.compare("toggle")) {
            cin >> input;
            toggle(input);
        } else if(!cmd.compare("all")){
            all();
        } else if(!cmd.compare("empty")) {
            empty();
        }
    }
    return 0;
}