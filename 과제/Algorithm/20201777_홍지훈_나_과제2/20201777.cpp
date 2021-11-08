#define MAXN 300

#include<iostream>
#include<cstring>

using namespace std;

int N;                     // [N]umber of vertex
int W[MAXN + 1][MAXN + 1]; // [W]eight array
int D[MAXN + 1][MAXN + 1]; // [D]istance array
int P[MAXN + 1][MAXN + 1]; // [P]ath array

void GetPath(int q, int r) {
    if(P[q][r] != 0) {
        GetPath(q, P[q][r]);
        cout << " " << P[q][r];
        GetPath(P[q][r], r);
    }
}
int GetPathCount(int q, int r) {
    if(P[q][r] == 0)
        return 0;
    return 1 + GetPathCount(q, P[q][r]) + GetPathCount(P[q][r], r);
    
}

int main(int argc, char *argv[]) {

    cin >> N; // input N
    for(int i = 1; i <= N; i++) {
        for(int j = 1; j <= N; j++) {
            cin >> W[i][j]; // input W[i][j] : i -> j weight
        }
    }

    // TODO : Floyd's Alogirhtm
    for(int i = 1; i <= N; i++) {
        for(int j = 1; j <= N; j++){
            P[i][j] = 0;
            D[i][j] = W[i][j];
        }
    }
    for(int k = 1; k <= N; k++) {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                if((D[i][k] != 0 && D[k][j] != 0)
                && ((D[i][k] + D[k][j] < D[i][j])
                || (D[i][j] == 0 && i != j))) {
                    P[i][j] = k;
                    D[i][j] = D[i][k] + D[k][j];
                }
            }
        }
    }


    if(strcmp(argv[1], "array") == 0) {
        // option : "array"
        // output : "N\nD array \n P array"
        //  ex) 아래 형식으로 N, D와 P를 출력해야함.
        //      +----------+
        //      | 3        |
        //      | 1 2 3    |
        //      | 4 5 6    |
        //      | 7 8 9    |
        //      | 11 12 13 |
        //      | 14 15 16 |
        //      | 17 18 19 |
        //      +----------+
        cout << N << endl;
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                cout << D[i][j] << ' ';
            }
            cout << '\n';
        }

        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                cout << P[i][j] << ' ';
            }
            cout << '\n';
        }
    }
    else if(strcmp(argv[1], "path") == 0) {
        // TODO
        for(int i = 1; i <= N; i++ ){
            for(int j = 1; j <= N; j++) {
                cout << GetPathCount(i, j);
                GetPath(i, j);
                cout << "\n";
            }
        }

        //  OUTPUT : "최소 이동 거리와 해당 경로"
        //  ex) 아래 박스 있는 값처럼 출력하면 됨.
        //      아래 출력 예시는 첫째줄에 각 정점에서 정점으로 이동하는 경로와 둘째줄에 이동 거리를 의미
        //       +-----------+
        //       | 0         | -> 노드 1에서 노드 1로 가는 최소 이동 거리 (이동거리가 0)
        //       | 4 1 3 5 2 | -> 노드 1에서 노드 2로 가는 최소 이동 거리와 그 옆에는 최소 이동 경로
        //       | 2 1 3     | -> 노드 1에서 노드 3로 가는 최소 이동 거리와 그 옆에는 최소 이동 경로
        //       | ...       | -> 이하 생략
        //       +-----------+
    }
    return 0;
}
