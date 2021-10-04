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
start_hour = 11

#최대 시작시간
end_hour = 17

#5:평일만 7: 주말까지
week_search = 5

#최대 허용 인원
ignore_member = 0

#최소 회의시간 (분)
conf_time = 30

#시간 배열
schedules = [[[[] for m in range(60)] for h in range(24)] for w in range(7)]

movetime = {}

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
        print(file)
        print(data)
        for w in range(week_search):
            for h in range(start_hour, end_hour):
                for m in range(60):
                    schedules[w][h][m].append(file)

        check_week = [False, False, False, False, False, False, False]
        for i in data.index:
            counter = data['시작시간'][i].split(':')
            counter[0] = int(counter[0])
            counter[1] = int(counter[1])
            endtime = data['끝시간'][i].split(':')
            endtime[0] = int(endtime[0])
            endtime[1] = int(endtime[1])
            weeker = week[data['요일'][i]]
            if check_week[weeker] == False:
                check_week[weeker] = True
            while counter[0] != endtime[0] or counter[1] != endtime[1]:
                try:
                    schedules[weeker][counter[0]][counter[1]].remove(file)
                except Exception:
                    pass
                counter[1] += 1
                if counter[1] >= 60:
                    counter[0] += 1
                    counter[1] = 0
            
        for w in range(week_search):
            if check_week[w] == False:
                for h in range(start_hour, end_hour):
                    for m in range(60):
                        schedules[w][h][m].remove(file)

        

    cnt = 1
    print(str(schedules))
    print(type(schedules[0][11][0]))
    for w in range(week_search):
        for key, value in week.items():
            if value == w:
                weeker = key
                break
        check_roatate = 0
        start_h = 0
        start_m = 0
        print(weeker + "요일")
        for h in range(start_hour, end_hour):
            print("탐테 " + str(h) + "시 00분 : " + str(schedules[w][h][0]))
            print("탐테 " + str(h) + "시 30분 : " + str(schedules[w][h][30]))
        print("\n\n")

if __name__=="__main__":
    main()