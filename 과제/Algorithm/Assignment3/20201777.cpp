#include <iostream>
#include <cstring>

using namespace std;

int N;
int K;
int W[20];
int V[20];

int maxprofit = -1;
int numbest = 0;

class Queue {
    private:
        int* data;
    public:
        int head;
        int tail;
        int size;
        int length;
        

    public:
        Queue() {
            head = 0;
            tail = 0;
            data = new int(20);
            size = 20;
            length = 0;
        }

        bool empty() { return head == tail; }
        void push() {
            tail++;
            length++;
            if(tail >= size) {
                
            }
        }

    
};

int Input() {
    cin >> N >> K;
    for(int i = 0; i < N; i++) 
        cin >> W[i] >> V[i];

}

void BTknapsack(int i, int profit, int weight) {
    if(weight <= K && profit > maxprofit) 
        maxprofit = profit;
    if(promising(i, profit, weight)) {
        BTknapsack(i+1, profit+V[i], weight+W[i]);
        BTknapsack(i+1, profit, weight);
    }

}

bool promising(int i, int profit, int weight) {
    int j;
    int totweight;
    int bound;
    if(weight >= K) return false;
    else {
        j = i + 1;
        bound = profit;
        totweight = weight;
        while((j <= N) && (totweight + W[j] <= K)) {
            totweight+= W[j];
            bound += V[j];
            j++;
        }
        return bound > maxprofit;
    }
}

void Backtracking() {
    BTknapsack(0, 0, 0);
}

void Brknapsack2(int n, const int* p, const int* w, int &maxprofit) {
    Queue Q;
    while(!Q.empty()) {

    }
}

int bound(int u) {

}

void Breadth() {

}

void Best() {

}


int main(int argc, char *argv[]) {
    Input();

    if(strcmp(argv[1], "backtracking") == 0) {
        Backtracking();
    } else if(strcmp(argv[1], "breadth") == 0) {
        Breadth();
    } else if(strcmp(argv[1], "best") == 0) {
        Best();
    }
    return 0;
}