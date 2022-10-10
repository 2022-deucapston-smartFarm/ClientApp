# smartFarm
2022년 동의대 캡스톤 디자인 스마트팜
---
##서버 -> 어플
###sensor.json
sensor {
  name: '상추',
  data: '2022-10-10 17:47:28',
  temperature: 21,
  humidity: 68,
  co2: 2600,
  ph: 6.0,
  illuminance: 40000
}
센서 : 이름,날짜시간,온도,습도,co2,ph,조도

###dailyStats.json
dailyStats {
  name: '상추',
  data: '2022-10-10',
  temperature: 21,
  humidity: 68,
  co2: 2600,
  ph: 6.0
}
일일통계 : 이름,날짜,온도,습도,co2,ph

###weekStats.json
weekStats {
  name: '상추',
  startData: '2022-10-10',
  endData: '2022-10-17',
  temperature: 21,
  humidity: 68,
  co2: 2600,
  ph: 6.0
}
주간통계 : 이름,시작날짜,종료날짜,온도,습도,co2,ph

---
##어플 -> 서버
###sensorNewInfo.json
sensorNewInfo {
  name: '상추',
  temperature: 21,
  co2: 2600,
  ph: 6.0,
  illuminance: 40000
}
식물정보 : 이름,온도,co2,ph,조도

###on/off 수동제어
warnFans true/false  //히터팬 제어
Fans true/false      //노멀팬 제어
leds true/false      //led제어
waterPump true/flase //워터펌프 제어

---
##통신 방법
socket.io를 이용하여 서버와 어플간 연결을 websocket형식으로 연결후 양방향으로 데이터를 전송할 수 있도록한다.


