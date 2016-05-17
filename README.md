# Smart Alarm

A Java based Smart Alarm application that gives the user a flash briefing on weather, stocks, news, and sports. Based off of [alarmpi](https://github.com/skiwithpete/alarmpi) project by SkiWithPete.
# Getting Started
1. Sign Up for a [Forecast IO Developer Account](https://developer.forecast.io/register) - FREE
2. Sign Up for a [Ivona Developer Account](https://www.ivona.com/) - FREE
3. Download [SmartAlarm](https://dl.dropboxusercontent.com/u/8895586/SmartAlarm.zip)
4. Configure SmartAlarmConfig.properties
5. ```cd``` to smartAlarm directory in command line.
6. Run ```sudo java -jar SmartAlarm.jar``` from command line.


# Download
You can download the latest version from [**here.**](https://dl.dropboxusercontent.com/u/8895586/SmartAlarm.zip)

You can also compile it yourself. Scroll down for more info.

# Configuration

A config file named SmartAlarmConfig.properties will be saved to the local directory.

```
Smart Alarm Configuration

#Your first name
Your_First_Name=Rudy

#Whether or not you want the weather in the briefing
Enable_Weather=true

#Weather Units (Imperial or Metric)
Weather_Units=Imperial

#Enable if you want to automatically get your location from IP address
Get_Weather_Location_from_IP=true

#Manually Enter City Name and State if Get_Weather_Location_from_IP=false
City_Name=Los Angeles
State_Name=California

#Enable if you want to hear stocks in the briefing
Enable_Stocks=true
#Enter the ticker names of the stocks you would like to follow separated by commas
Stock_Names=AAPL,GOOG,TSLA

#Enable if you want the current value of Bitcoin in the briefing
Get_Bitcoin_Exchange_Rate=true

#Enable if you want news stories in the briefing
Enable_News=true
#Set the news source (NYT or BBC)
News_Source=BBC
#Set the amount of stories you want read to you
Number_of_News_Stories=4

# Enable if you want soccer in the briefing
Enable_Soccer=true

#Enable if you want a song to be played after the briefing
playSong=true
#Enter the path of the song. Just the name if it is in directory
songLocation=Had to Hear.mp3

#You need to enter your Ivona Credentials here
ivonaAccessKey=LettersAndNumbers
ivonaSecretKey=MoreLettersAndNumbers

#You need to enter your Forecast IO Credentials here
ForecastIoApiKey=MoreLettersAndNumbers
```

# Common Errors
```ERROR: Could not determine your location based off of you IP. Please manually enter your location```

- Fix: Set Get_Weather_Location_from_IP=false and manually define City_Name and State_Name

<br>
```java.io.IOException: Connection to https://api.forecast.io/forecast//23.5633,63.7532/?units=us was not successful```    

- Fix: Edit the config file and enter the API Key for Forecast.io

# Dependencies
On Gradle:
```
compile 'com.ivona:ivona-speechcloud-sdk-java:1.0.0'
compile 'javazoom:jlayer:1.0.1'
compile 'com.squareup.okhttp3:okhttp:3.2.0'
```

# About
Feel Free to Fork

[Trello Board](https://trello.com/b/2KvGaEX5) - If you would like to be added, just email me


- Geo-Location is provided by [IPinfo.io](http://ipinfo.io)
- City Name to Lat/Long is provided by Yahoo.
- Weather is provided by [Forecast.io](http://Forecast.io)
- News is provided by [BBC](http://BBC.com) and [New York Times.](http://nytimes.com)
- Soccer is provided by [football-data.org](http://api.football-data.org/index)
- Text to Speech is powered by [Amazon Ivona.](https://www.ivona.com/)

# Author
Rudy Bermudez  -  [hello@rudybermudez.co](mailto:hello@rudybermudez.co)

# License

Copyright 2016 Rudy Bermudez
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
