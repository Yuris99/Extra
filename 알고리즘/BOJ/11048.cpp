#include <stdio.h>

int map[1001][1001];
int d[1001][1001];

int main(void) {
	int n, m;
	scanf("%d %d", &n, &m);
	for (int i = 1; i <= n; i++) 
		for (int j = 1; j <= m; j++) 
			scanf("%d", &map[i][j]);
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= m; j++) {
			d[i][j] = ((d[i - 1][j - 1] > d[i - 1][j] ? d[i - 1][j - 1] : d[i - 1][j]) > d[i][j - 1] ? (d[i - 1][j - 1] > d[i - 1][j] ? d[i - 1][j - 1] : d[i - 1][j]) : d[i][j - 1]) + map[i][j];
		}
	}
	printf("%d \n", d[n][m]);
}
