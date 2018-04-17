"""

Temperature/Humidity monitor using Raspberry Pi

"""
import grovepi
import sys
import RPi.GPIO as GPIO
from time import gmtime, strftime, sleep
import urllib2

the_url = 'http://192.168.1.24:8080/PTUT_PENIBILITE/webresources/generic/'
idRasp = 1
date = None
heure = None
libelT = 'Temperature'
libelH = 'Humidite'



def getSensorData():
    sensor = 4  # The Sensor goes on digital port 4.
    blue = 0   # The Blue colored sensor.
    white = 1   # The White colored sensor.

    while True:
        try:
        # The first parameter is the port, the second parameter is the type of sensor.
            [temp,humidity] = grovepi.dht(sensor,1)      

        except IOError:
            print ("Error")
            
        #Gestion des valeurs erronnees fournies occasionnellement par le capteur
        if (humidity != -1 or humidity != 'nan'):
            #print("temp =", temp, "humidity =", humidity)
            return [temp,humidity]


'''
#ne pas gerer la penibilite sur le raspb
def isPenibleZone(T):
    isPenible = False
    if ( T >= 30 or T <= -5 ) :
        isPenible = True
    return isPenible

def setPenibleZone(bool):
    if (bool == True):
        scanBLE()
'''


''' ancienne architecture avec la plateforme ThingSpeak
def sendToThingSpeak(f1, f2):
    connexion = urllib2.urlopen("https://api.thingspeak.com/update.json?api_key=XZFA696XYS2ZWP4Z&field1=%s&field2=%s" %(f1, f2))
    connexion.close()
    print("envoi vers thingspeak")
'''    


def ajoutMesure(m, libel):
    global idRasp, date, heure
    date = strftime("%Y-%m-%d", gmtime())
    heure = strftime("%H:%M:%S", gmtime())
    r = urllib2.Request(the_url + 'ajoutMesure?rasp='+str(idRasp)+'&type='+libel+'&valeur='+str(m)+'&date='+date+"%20"+heure)
    r.get_method = lambda: 'PUT'
    reponse = urllib2.urlopen(r)
    

# main() function
def main():
    global date, heure
    print 'starting...', '\n-----'

    '''
    T = 30
    H = 50
    print 'Humidite :', H , '%','\nTemperature :', T, 'deg_Celsius', '\n-----'
    indic = isPenibleZone(T)
    setPenibleZone(indic)
    '''
    


    while True:
        try:           
            T, H = getSensorData()
            #T = 30
            #H = 50
            print 'Humidite :', H , '%','\nTemperature :', T, 'deg_Celsius', '\n-----'
            print('ajout de la mesure humidite')
            ajoutMesure(H, libelH)
            print('ajout de la mesure temperature')
            ajoutMesure(T, libelT)
            print(date, heure )
            print('-----')
            sleep(60)
            
            #indic = isPenibleZone(T)
            #setPenibleZone(indic)

        except:
            print 'Erreur fatale de l"update'
            break
    
    
 
# call main
if __name__ ==  '__main__':
    main()
