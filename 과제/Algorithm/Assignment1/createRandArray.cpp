#include <iostream>
#include <time.h>

using namespace std;

int arr[500005];

void init() {
    for(int i = 1; i <= 500000; i++) 
        arr[i] = i;
}

void shuffle() {
    srand(time(NULL));

    //shuffle
    int temp, r1, r2;
    int n = rand() % 500000 + 100000;
    for(int i = 1; i <= n; i++) {
        r2 = (rand() % 500000 + rand() % 300000) % 500000 + 1;
        r1 = rand() % 500000 + 1;
        temp = arr[r1];
        arr[r1] = arr[r2];
        arr[r2] = temp;
    }
}


int main(void) {
    init();
    shuffle();
    for(int i = 1; i <= 500000; i++) 
        printf("%d ", arr[i]);
    return 0;
}