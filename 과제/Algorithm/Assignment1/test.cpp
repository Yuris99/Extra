#include<stdio.h>
void merge(int a[], int s, int m, int e){
    int b[500010];
    int i=s, j=m+1, k=s;
    while(i<=m && j<=e){
        if(a[i] < a[j]){
            b[k]=a[i]; i++;
        }else{
            b[k]=a[j]; j++;
        }
        k++;
    }
    while(i<=m){
        b[k]=a[i]; k++;   i++;
    }
   while(j<=e){
      b[k]=a[j]; k++;   j++;
   }
   for(i=s;i<=e;i++) a[i]=b[i];
}
void mergesort(int a[], int s, int e){
   if(s<e){
      int m=(s+e)/2;
      mergesort(a,s,m);
      mergesort(a,m+1,e);
      merge(a,s,m,e);
   }
}
int main(){
   int a[500010], i, n = 500000;
   //scanf("%d", &n);
   for(i=1;i<=n;i++)
      scanf("%d",a+i);
    //mergesort(a,1,n);
   for(i=1;i<=n;i++){
      printf("%d\n", a[i]);
   }
}