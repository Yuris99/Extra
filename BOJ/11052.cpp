#include <stdio.h>

int p[1001];
int d[1001];

int main(void) {
	int n;
	scanf("%d", &n);
	for (int i = 1; i <= n; i++)
		scanf("%d", &p[i]);
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= i; j++) 
			d[i] = d[i] < d[i - j] + p[j] ? d[i - j] + p[j] : d[i];
	}
	printf("%d \n", d[n]);
}
