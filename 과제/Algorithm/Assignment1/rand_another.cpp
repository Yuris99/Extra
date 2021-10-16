#include <iostream>
#include <time.h>

using namespace std;

int arr[500005];
bool check[500001] = {false};

void init() {
    for(int i = 1; i <= 500000; i++) 
        arr[i] = i;
}

void shuffle() {
    srand(time(NULL));

    //shuffle
    int temp, r;
    for(int i = 1; i <= 500000; i++){
        r = (rand() * 32768 + rand()) % 500000 + 1;
        if(!check[r]) {
            check[r] = true;
            arr[i] = r;
        } else {
            i--;
            continue;
        }
    }
}


int main(void) {
    init();
    shuffle();
    for(int i = 1; i <= 500000; i++) 
        printf("%d ", arr[i]);
    return 0;
}