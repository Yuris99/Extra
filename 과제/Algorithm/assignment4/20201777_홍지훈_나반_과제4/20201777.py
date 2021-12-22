import numpy as np
import csv
import matplotlib.pyplot as plt
import pandas as pd
#from sklearn.cluster import KMeans

cities = []
sol = []

def distance(x, y):
    dist = np.linalg.norm(np.array(x)-np.array(y))
    return dist
'''
with open ('example_solution.csv', mode='r', newline='') as solution:

    reader = csv.reader(solution)
    for row in reader:
        sol.append(int(row[0]))

    idx = sol.index(0)

    front = sol[idx:]
    back = sol[0:idx]

    sol = front + back

    sol.append(int(0))
'''
with open('TSP.csv', mode='r', newline='') as tsp:
    reader = csv.reader(tsp)
    for row in reader:
        cities.append(row)

df = pd.DataFrame(cities)

df.columns = ['X', 'Y']
df['X'] = pd.to_numeric(df['X'])
df['Y'] = pd.to_numeric(df['Y'])

'''
total_cost = 0
for idx in range(len(sol)-1):

    pos_city_1 = [float(cities[sol[idx]][0]), float(cities[sol[idx]][1])]
    pos_city_2 = [float(cities[sol[idx+1]][0]), float(cities[sol[idx+1]][1])]

    dist = distance(pos_city_1, pos_city_2)

    total_cost += dist

print('final cost: '+ str(total_cost))
'''

'''
model = KMeans(n_clusters = 50)
model.fit(df)

predict = pd.DataFrame(model.predict(df))
predict.columns=['predict']

r = pd.concat([df,predict],axis=1)


plt.scatter(r['X'],r['Y'],c=r['predict'],alpha=0.5)
'''


plt.figure(figsize=(10,10))
plt.scatter(df['X'], df['Y'])
'''
for idx in range(len(sol)-1):
    plt.plot([float(cities[sol[idx]][0]), float(cities[sol[idx+1]][0])], \
            [float(cities[sol[idx]][1]), float(cities[sol[idx+1]][1])], color="blue")
'''
plt.xlabel('X')
plt.ylabel('Y')
plt.title('TSP cities plot')
plt.show()

#모든 경로 구함
def getAllDis(path):
    totalcost = 0
    
    for idx in range(len(path)-1):
        pos_city_1 = [float(cities[path[idx]][0]), float(cities[path[idx]][1])]
        pos_city_2 = [float(cities[path[idx+1]][0]), float(cities[path[idx+1]][1])]

        dist = distance(pos_city_1, pos_city_2)

        totalcost += dist
    return totalcost

'''
#그리디
ch_num = 100

chrom = []
chrom_dis = []

print("initialize...")
usepath = []
for i in range(ch_num):
    #path = allPath.copy()
    #np.random.shuffle(path)
    path = []
    startpoint = int(np.random.random() * len(cities))
    while startpoint in usepath: startpoint = int(np.random.random() * len(cities))
    usepath.append(startpoint)
    path.append(startpoint)
    for n in (range(len(cities)-1)):
        minlen = 10000
        minn = -1
        for j in range(len(cities)):
            if j in path: continue
            pos_city_1 = [float(cities[j][0]), float(cities[j][1])]
            pos_city_2 = [float(cities[path[-1]][0]), float(cities[path[-1]][1])]

            dist = distance(pos_city_1, pos_city_2)
            if minlen > dist:
                minlen = dist
                minn = j
        path.append(minn)
        
        
    
    #print(path)
        
    chrom.append(path)
    chrom_dis.append(getAllDis(path))
    print("init" + str(i))

#그리디 출력
plt.figure(figsize=(10,10))
plt.scatter(df['X'], df['Y'])
for idx in range(len(chrom[0])-1):
    plt.plot([float(cities[chrom[0][idx]][0]), float(cities[chrom[0][idx+1]][0])], \
            [float(cities[chrom[0][idx]][1]), float(cities[chrom[0][idx+1]][1])], color="blue")
plt.xlabel('X')
plt.ylabel('Y')
plt.title('TSP cities plot[0]')
plt.show()
'''

ch_num = 100

chrom = []
chrom_dis = []
allPath = list(range(1000))
print("initialize...")
for i in range(ch_num):
    path = allPath.copy()
    np.random.shuffle(path)
        
    chrom.append(path)
    chrom_dis.append(getAllDis(path))
    print("init" + str(i))
    

#순위 선택
def ranksel(ch):
    ch = sorted(ch, key=getAllDis)
    return ch

#룰렛휠 선택
def roulettesel(chdis, k = 4):
    maxcost = max(chdis)
    mincost = min(chdis)
    costsum = sum((maxcost - i) + ((maxcost - mincost) / (k-1)) for i in chdis)
    selected = []
    
    for i in range(2):
        sel = np.random.uniform(0, costsum)
        adder = 0
        for i in range(len(chdis)):
            adder += (maxcost - chdis[i]) + ((maxcost - mincost) / (k-1))
            if(adder >= sel):
                #selected.append(i)
                selected.append(i)
                break
                
    return selected
            
#토너먼트 선택
def getTSel(chdis, arr,k, t_rate):
    if k == 0:
        return arr[0];
    newarr = []
    i = 0
    while i < len(arr):
        if chdis[arr[i]] > chdis[arr[i+1]]:
            arr[i], arr[i+1] = arr[i+1], arr[i]
        if np.random.uniform() > t_rate:
            newarr.append(arr[i])
        else:
            newarr.append(arr[i+1])
        i += 2
    return getTSel(chdis, newarr, k-1, t_rate)
        
    

def tournamentSel(chdis, k,t_rate):
    arr = [int(np.random.random() * len(chdis)) for i in range(2 ** k)]
    
    
    return getTSel(chdis, arr, k, t_rate)
        
        
#심플토너먼트 선택
def simpletournamentSel(chdis, k):
    arr = [int(np.random.random() * len(chdis)) for i in range(k)]
    result = int(np.random.random() * len(chdis))
    for i in range(k-1):
        r = int(np.random.random() * len(chdis))
        if chdis[result] > chdis[r]: result = r
    
    
    return result
    
#2점 교차
def crossover(s1, s2):
    child1 = []
    
    startPoint = int(np.random.random() * len(s1))
    endPoint = int(np.random.random() * len(s1))
    
    if(startPoint > endPoint): startPoint, endPoint = endPoint, startPoint
    
    for i in range(startPoint, endPoint):
        child1.append(s2[i])
    
    news1 = []
    for i in s1:
        if i not in child1:
            news1.append(i)
            
    child = []
    child.extend(news1[:startPoint])
    child.extend(child1)
    child.extend(news1[startPoint:])
    
    #print(str(len(child1)) + " and " + str(len(news1)))
    #print(len(child))
    #print(child)
    
    return child

#순서 교차
def order_crossover(s1, s2):
    child1 = []
    
    startPoint = int(np.random.random() * len(s1))
    endPoint = int(np.random.random() * len(s1))
    
    if(startPoint > endPoint): startPoint, endPoint = endPoint, startPoint
    
    for i in range(startPoint, endPoint):
        child1.append(s2[i])
    
    news1 = []
    for i in s1:
        if i not in child1:
            news1.append(i)
            
    
    child1.extend(news1[:len(s1)-endPoint])
    
    child = []
    child.extend(news1[len(s1)-endPoint:])
    child.extend(child1)
    
    #print(str(len(child1)) + " and " + str(len(news1)))
    #print(len(child))
    
    return child
    
#PMX 교차
def pmx_crossover(s1, s2):
    child1 = []
    
    startPoint = int(np.random.random() * len(s1))
    endPoint = int(np.random.random() * len(s1))
    
    if(startPoint > endPoint): startPoint, endPoint = endPoint, startPoint
    
    for i in range(startPoint, endPoint):
        child1.append(s2[i])

            
    
    
    child = [-1 for i in range(len(s1))]
    child[startPoint:endPoint] = child1
    
    for i in range(startPoint):
        if s1[i] not in child:
            child[i] = s1[i]
    for i in range(endPoint, len(s1)):
        if s1[i] not in child:
            child[i] = s1[i]
        
    news1 = []
    for i in s1:
        if i not in child:
            news1.append(i)
            
    cnt = 0
    for i in range(len(child)):
        if child[i] == -1:
            child[i] = news1[cnt]
            cnt+=1
    
    #print(str(len(child1)) + " and " + str(len(news1)))
    #print(len(child))
    
    return child
    
#간선재조합(미완성)
def edge_crossover(s1, s2):
    edgelist = [[] for i in range(len(s1))]
    
    edgelist[s1[0]].append(s1[1])
    edgelist[s1[-1]].append(s1[-2])
    edgelist[s2[0]].append(s2[1])
    edgelist[s2[-1]].append(s2[-2])
    for i in range(1, len(s1)-1):
        if s1[i-1] not in edgelist[s1[i]]: edgelist[s1[i]].append(s1[i-1])
        if s1[i+1] not in edgelist[s1[i]]: edgelist[s1[i]].append(s1[i+1])
            
        if s2[i-1] not in edgelist[s2[i]]: edgelist[s2[i]].append(s2[i-1])
        if s2[i+1] not in edgelist[s2[i]]: edgelist[s2[i]].append(s2[i+1])
            
    child = []
    child.append(s1[0])
    for i in range(len(s1)-1):
        min = i+1
        for j in edgelist[child[i]]:
            if len(edgelist[min]) >= len(edgelist[j]) and j not in child:
                min = j
        if min in child:
            while min in child: 
                pass
        child.append(j)
        
    
#변이
def mutation(ch, m_rate = 0.05):
    for i in range(len(ch)):
        if np.random.random() < m_rate:
            j = int(np.random.random() * len(ch))
            ch[i], ch[j] = ch[j], ch[i]
    
    
    return ch

#GA 구현
#세대수
gencnt = 100
#변이 확률
m_rate = 0.0005

#세대 기록
generate = []
gennote = []

def GA(chrom, chrom_dis):
    plt.figure(figsize=(10,10))
    for tri in range(gencnt):
        chrom = ranksel(chrom)
        chrom_dis = [getAllDis(chrom[i]) for i in range(len(chrom))]
        
        nextg = []
        nextdis = []
        
        #엘리트주의
        elitism = 5
        
        for i in range(elitism):
            nextg.append(chrom[i])
            nextdis.append(chrom_dis[i])
        
        for i in range(elitism, len(chrom)):
            #selected = roulettesel(chrom_dis, 5)z
            #selected = [tournamentSel(chrom_dis, 5, 0.8), tournamentSel(chrom_dis, 5, 0.9)]
            selected = [simpletournamentSel(chrom_dis, 20), simpletournamentSel(chrom_dis, 20)]
        
            #child = crossover(chrom[selected[0]], chrom[selected[1]])
            #child = order_crossover(chrom[selected[0]], chrom[selected[1]])
            child = pmx_crossover(chrom[selected[0]], chrom[selected[1]])
        
            child = mutation(child, m_rate)
            
            nextg.append(child)
            nextdis.append(getAllDis(child))
            
        
        generate.append(getAllDis(chrom[0]))
        gennote.append(chrom[0])
        #print(chrom)
        plt.plot(generate)
        #plt.show()
        
        print("Gen " + str(tri) + ": " + str(chrom_dis[0]))
        #print(chrom_dis)
        
        
        chrom = nextg
        chrom_dis = nextdis
        
        
    plt.show()

def main():
    GA(chrom, chrom_dis)

    #파일로 출력

if __name__ == "__main__":
    main()