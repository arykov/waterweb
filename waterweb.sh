#cookies not needed since I hit agree all the time, but if needed they can be used as shown bellow
#wget --referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=02HB004&syr=2010&smo=6&sday=5&eyr=2010&emo=6&eday=6" --cookies=on --load-cookies=cookie.txt --keep-session-cookies --save-cookies=cookie.txt --post-data="disclaimer_action=I+Agree"  --timeout=5 -O test.xml "http://www.wateroffice.ec.gc.ca/include/disclaimer.php"
stn=02EC002
eyear=`date --date='tomorrow' +%Y`
emon=`date --date='tomorrow'  +%m`
eday=`date --date='tomorrow'  +%d`
syear=`date --date='yesterday' +%Y`
smon=`date --date='yesterday' +%m`
sday=`date --date='yesterday' +%d`

query_discharge="mode=text&stn=${stn}&syr=${syear}&smo=${smon}&sday=${sday}&eyr=${eyear}&emo=${emon}&eday=${eday}&prm1=6"
query_raw="mode=text&stn=${stn}&syr=${syear}&smo=${smon}&sday=${sday}&eyr=${eyear}&emo=${emon}&eday=${eday}&prm1=3"
query_both="mode=text&stn=${stn}&syr=${syear}&smo=${smon}&sday=${sday}&eyr=${eyear}&emo=${emon}&eday=${eday}&prm1=3&prm2=6"

wget --referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?${query_discharge}"  --post-data="disclaimer_action=I+Agree"  --timeout=5 -O ${stn}_discharge.html "http://www.wateroffice.ec.gc.ca/include/disclaimer.php"
wget --referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?${query_raw}"  --post-data="disclaimer_action=I+Agree"  --timeout=5 -O ${stn}_raw.html "http://www.wateroffice.ec.gc.ca/include/disclaimer.php"
wget --referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?${query_both}"  --post-data="disclaimer_action=I+Agree"  --timeout=5 -O ${stn}_both.html "http://www.wateroffice.ec.gc.ca/include/disclaimer.php"