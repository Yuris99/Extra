import time
import os
import pandas as pd

path = './schedules/'

week = {"월" : 0,
        "화" : 1,
        "수" : 2,
        "목" : 3,
        "금" : 4,
        "토" : 5,
        "일" : 6,}

#최소 시작시간
start_hour = 10

#최대 시작시간
end_hour = 17

#5:평일만 7: 주말까지
week_search = 5

#최소 회의시간 (분)
conf_time = 30

#시간 배열
schedules = [[[[] for m in range(60)] for h in range(24)] for w in range(7)]

#통학시간 배열
movetime = {}

time_divide1 = [[[10, 0], [11, 30]],
                [[11,30], [12, 50]],
                [[12,50], [14, 10]],
                [[14,10], [15, 40]],
                [[15,40], [17, 00]],
                ]
time_divide2 = [[[10,00], [11, 00]],
                [[11,00], [12, 00]],
                [[12,00], [13, 00]],
                [[13,00], [14, 00]],
                [[14,00], [15, 00]],
                [[15,00], [16, 00]],
                [[16,00], [17, 00]],
                ]

timelist = {}

#제외인원
ignore_member = [[],
                []]


def main():
    csvlist = os.listdir(path)
    #print(csvlist)

    #csv파일 가져오기
    os.chdir('schedules')
    for file in csvlist:
        data = pd.read_csv(file)
        if file == "movetime.csv":
            for i in data.index:
                movetime[data["이름"][i]] = data['시간'][i]

            continue
        #print(file)
        #print(data)
        timelist[file] = [[[0 for m in range(60)] for h in range(24)] for w in range(7)]
        
        for i in data.index:
            counter = data['시작시간'][i].split(':')
            counter[0] = int(counter[0])
            counter[1] = int(counter[1])
            endtime = data['끝시간'][i].split(':')
            endtime[0] = int(endtime[0])
            endtime[1] = int(endtime[1])
            weeker = week[data['요일'][i]]
            check_week = [False, False, False, False, False, False, False]
            counter[1] -= 5
            if(counter[1] < 0):
                counter[1] += 60
                counter[0] -= 1
            endtime[1] += 5
            if(endtime[1] >= 60):
                endtime[1] -= 60
                endtime[0] += 1
            if check_week[weeker] == False:
                check_week[weeker] = True
            while counter[0] != endtime[0] or counter[1] != endtime[1]:
                try:
                    timelist[file][weeker][counter[0]][counter[1]] = 1
                except Exception:
                    pass
                counter[1] += 1
                if counter[1] >= 60:
                    counter[0] += 1
                    counter[1] = 0
                    
        for w in range(week_search):
            if check_week[w] == False and file in movetime:
                for h in range(start_hour, end_hour):
                    for m in range(60):
                        timelist[file][w][h][m] = 1


    for key, i in timelist.items():
        print("\n\n")
        print(key)
        print("회실탐테\n")
        for w, wn in week.items():
            if(wn >= week_search): break
            print(w)
            for t in time_divide1:
                startt = [0, 0]
                endt = [0, 0]
                startt[0] = t[0][0]
                startt[1] = t[0][1]
                endt[0] = t[1][0]
                endt[1] = t[1][1]
                while startt[0] != endt[0] or startt[1] != endt[1]:
                    if(i[wn][startt[0]][startt[1]] == 1): break;
                    startt[1]+=1
                    if startt[1] >= 60:
                        startt[0] += 1
                        startt[1] = 0
                if startt[0] == endt[0] and startt[1] == endt[1]:
                    print(str(t[0][0]) + ":" + str(t[0][1]) + " ~ " + str(t[1][0]) + ":" + str(t[1][1]))
        print("\n스엔탐테\n")
        for w, wn in week.items():
            if(wn >= week_search): break
            print(w)
            for t in time_divide2:
                startt = [0, 0]
                endt = [0, 0]
                startt[0] = t[0][0]
                startt[1] = t[0][1]
                endt[0] = t[1][0]
                endt[1] = t[1][1]
                while startt[0] != endt[0] or startt[1] != endt[1]:
                    if(i[wn][startt[0]][startt[1]] == 1): break;
                    startt[1]+=1
                    if startt[1] >= 60:
                        startt[0] += 1
                        startt[1] = 0
                if startt[0] == endt[0] and startt[1] == endt[1]:
                    print(str(t[0][0]) + ":" + str(t[0][1]) + " ~ " + str(t[1][0]) + ":" + str(t[1][1]))



    for w, wn in week.items():
        if(wn >= week_search): break
        print(w + " 회실탐테")
        for t in time_divide1:
            print(str(t[0][0]) + ":" + str(t[0][1]) + " ~ " + str(t[1][0]) + ":" + str(t[1][1]))
            for key, i in timelist.items():
                if key in ignore_member[0]: continue;
                startt = [0, 0]
                endt = [0, 0]
                startt[0] = t[0][0]
                startt[1] = t[0][1]
                endt[0] = t[1][0]
                endt[1] = t[1][1]
                while startt[0] != endt[0] or startt[1] != endt[1]:
                    if(i[wn][startt[0]][startt[1]] == 1): break;
                    startt[1]+=1
                    if startt[1] >= 60:
                        startt[0] += 1
                        startt[1] = 0
                if startt[0] == endt[0] and startt[1] == endt[1]:
                    print(key)
        
            print()
        print()

    for w, wn in week.items():
        if(wn >= week_search): break
        print(w + " 스엔탐테")
        for t in time_divide2:
            print(str(t[0][0]) + ":" + str(t[0][1]) + " ~ " + str(t[1][0]) + ":" + str(t[1][1]))
            for key, i in timelist.items():
                if key in ignore_member[1]: continue;
                startt = [0, 0]
                endt = [0, 0]
                startt[0] = t[0][0]
                startt[1] = t[0][1]
                endt[0] = t[1][0]
                endt[1] = t[1][1]
                while startt[0] != endt[0] or startt[1] != endt[1]:
                    if(i[wn][startt[0]][startt[1]] == 1): break;
                    startt[1]+=1
                    if startt[1] >= 60:
                        startt[0] += 1
                        startt[1] = 0
                if startt[0] == endt[0] and startt[1] == endt[1]:
                    print(key)
                    
            print()
        print()
        

    pass


if __name__=="__main__":
    main()