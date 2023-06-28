import requests
import time
import datetime

start = datetime.datetime.now()
print("Elapsed Time: {0}".format(datetime.datetime.now() - start))

url = 'http://127.0.0.1:5000/users/pepe'
# x = requests.post(url, data = "alonso.jpg")
data1 = {'file': 'img/alonso_192.168.100.123_2023-06-19_03-55-53.png'}
x = requests.post(url, json=data1)
print(x)
print(x.text)
print("Elapsed Time: {0}".format(datetime.datetime.now() - start))
time.sleep(1)

data2 = {'file': 'img/alonso_192.168.100.123_2023-06-19_03-56-01.png'}
x = requests.post(url, json=data2)
print(x)
print(x.text)
print("Elapsed Time: {0}".format(datetime.datetime.now() - start))
time.sleep(1)

data3 = {'file': 'img/alonso_192.168.100.123_2023-06-19_03-56-35.png'}
x = requests.post(url, json=data3)
print(x)
print(x.text)
print("Elapsed Time: {0}".format(datetime.datetime.now() - start))
time.sleep(1)

data4 = {'file': 'img/alonso_192.168.100.123_2023-06-19_03-56-36.png'}
x = requests.post(url, json=data4)
print(x)
print(x.text)
print("Elapsed Time: {0}".format(datetime.datetime.now() - start))
