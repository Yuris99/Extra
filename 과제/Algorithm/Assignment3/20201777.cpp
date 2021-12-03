#include <iostream>
#include <cstring>

using namespace std;



struct node {
    int profit;
    int weight;
    float value;
    int level;
    float bound;
};

class Queue {
    private:
        node *data;
        int head;
        int tail;
        int capacity;

    public:
        Queue() {
            data = new node[30];
            head = 0;
            tail = 0;
            capacity = 30;
        }

        void push(node item) {
            if((tail + 1) % capacity == head) {
                node *newQ = new node[2 * capacity];

                int start = (head + 1) % capacity;
                int cnt = 1;
                if (start < 2) {
                    for(int i = start; i < start + capacity - 1; i++) 
                        newQ[cnt++] = data[i];
                } else {
                    for(int i = start; i < start + capacity - 1; i++) 
                        newQ[cnt++] = data[i];
                    for(int i = 0; i < start + tail + 1; i++) 
                        newQ[cnt++] = data[i];
                }
                head = 2 * capacity - 1;
                tail = capacity - 2;
                capacity *= 2;
                delete[] data;
                data = newQ;
            }
            tail = (tail + 1) % capacity;
            data[tail] = item;
            
        }
        node pop() {
            if(head == tail) throw "Q is empty.";
            head = (head + 1) % capacity;
            node tempnode = data[head];
            data[head].~node();
            return tempnode;
        }


};

int N;
int K;
node V[25];

int maxprofit = 0;


//Node compare
int compare(node a, node b) {
    /*
    if(a.value == b.value)
        return a.weight > b.weight;*/
    return a.value > b.value;
}

//Priority Queue Push
void insert(node *Q, node item, int i) {
    i++;
    while((i != 1) && compare(item, Q[i/2])) {
        Q[i] = Q[i/2];
        i /= 2;
    }
    Q[i] = item;

}
//Priority Queue Pop
node remove(node *Q, int size) {
    int p, c;
    node item, end;
    item = Q[1];
    end = Q[size];
    p = 1; c = 2;

    while(c <= size) {
        if((c < size) && (Q[c].value < Q[c+1].value))
            c++;
        if(end.value >= Q[c].value) break;
        Q[p] = Q[c];
        p = c;
        c *= 2;
    }
    Q[p] = end;
    return item;
}


void Input() {
    cin >> N >> K;
    node temp[25];
    for(int i = 0; i < N; i++)  {
        node tempnode;
        cin >> tempnode.weight >> tempnode.profit;
        tempnode.value = (float)tempnode.profit / tempnode.weight;
        insert(temp, tempnode, i);
    }
    // heapsort
    for(int i = 0; i < N; i++) {
        V[i+1] = remove(temp, N-i);
        V[i+1].level = i+1;
    }
}

bool promising(int i, int profit, int weight) {
    int j, k;
    int totweight;
    float bound;
    if(weight >= K) return false;
    else {
        j = i + 1;
        bound = profit;
        totweight = weight;
        while((j <= N) && (totweight + V[j].weight <= K)) {
            totweight += V[j].weight;
            bound += V[j].profit;
            j++;
        }
        k = j;
        if(k <= N)
            bound += (K-totweight)*V[k].profit/V[k].weight;
        return bound > maxprofit;
    }
}

void BTknapsack(int i, int profit, int weight) {
    if(weight <= K && profit > maxprofit) 
        maxprofit = profit;
    if(promising(i, profit, weight)) {
        BTknapsack(i+1, profit+V[i+1].profit, weight+V[i+1].weight);
        BTknapsack(i+1, profit, weight);
    }

}

void Backtracking() {
    BTknapsack(0, 0, 0);
}

float bound(node u) {
    int j, k; int totweight = 0; float result;
    if(u.weight >= K)
        return 0;
    else {
        result = u.profit; j = u.level + 1; totweight = u.weight;
        while((j<=N) && (totweight + V[j].weight <= K)) {
            totweight += V[j].weight;
            result += V[j].profit;
            j++; 
        }
        k =j;
        if(k <= N)
            result = result + (K-totweight)*V[k].profit/V[k].weight;
        return result;
    }

}

void Breadth() {
    Queue Q;
    int Qsize = 1;
    node u, v;
    v.profit = 0; v.weight = 0; v.value = 0; v.level = 0;
    Q.push(v);
    while(Qsize) {
        v = Q.pop(); Qsize--; u.level = v.level + 1;
        u.profit = v.profit + V[u.level].profit;
        u.weight = v.weight + V[u.level].weight;
        if((u.weight <= K) && (u.profit > maxprofit)) maxprofit = u.profit;
        if(bound(u) > maxprofit) {Q.push(u); Qsize++;}
        u.weight = v.weight;
        u.profit = v.profit;
        if(bound(u) > maxprofit) {Q.push(u); Qsize++;}
    }

}

void Best() {
    node PQ[400];
    int Qsize = 1;
    node u, v;
    v.level = 0; v.profit = 0; v.weight = 0; v.bound = bound(v);
    insert(PQ, v, 0);
    while(Qsize) {
        v = remove(PQ, Qsize--);
        if(v.bound > maxprofit) {
            u.level = v.level + 1;
            u.profit = v.profit + V[u.level].profit;
            u.weight = v.weight + V[u.level].weight;
            if((u.weight <= K) && (u.profit > maxprofit)) maxprofit = u.profit;
            u.bound = bound(u);
            if(u.bound > maxprofit) insert(PQ, u, Qsize++);
            u.weight = v.weight;
            u.profit = v.profit;
            u.bound = bound(u);
            if(u.bound > maxprofit) insert(PQ, u, Qsize++); 
        }

    }
}


int main(int argc, char *argv[]) {
    Input();
    /*
    for(int i = 0; i <= N; i++) {
        cout << V[i].profit << V[i].weight << endl;
    }
    */
    if(strcmp(argv[1], "backtracking") == 0) {
        Backtracking();
    } else if(strcmp(argv[1], "breadth") == 0) {
        Breadth();
    } else if(strcmp(argv[1], "best") == 0) {
        Best();
    }
    cout << maxprofit << endl;
    return 0;
}