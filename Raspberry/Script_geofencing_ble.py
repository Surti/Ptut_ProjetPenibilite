import sys
import os
import subprocess
from time import gmtime, strftime, sleep
import signal
from threading import Timer
import urllib2


ActualScannedUsers = set()
LastScannedUsers = set()
AuthorisedUsers = set()
idRasp = 1
the_url = 'http://192.168.43.65:8080/PTUT_PENIBILITE/webresources/generic/'
lenscan = 0
endscan = 40
date = None
heure = None


def presence (a,l):
    UsersConnect = ()
    UsersDisconnect = ()
    if (a == l):
        return (UsersConnect,UsersDisconnect)
    else:
        UsersDisconnect = l-a
        UsersConnect = a-l
    return (UsersConnect,UsersDisconnect)


def utilisateurGetCompte(pseudo):
    global the_url
    try:
        if (pseudo != '(unknown)' and pseudo != ''):
            req = urllib2.urlopen(the_url + 'connect?user=' + pseudo)
            return True
        
    except urllib2.HTTPError :
        return False
    
    req.close()



def ajoutEvenementEntree(uco):
    global idRasp
    if ( len(uco) != 0):
        for pseudo in uco:
            if (utilisateurGetCompte(pseudo)) :
                AuthorisedUsers.add(pseudo)
                print(AuthorisedUsers)


        print('la liste des authorise',AuthorisedUsers)
        if (pseudo in AuthorisedUsers) :    
            r = urllib2.Request(the_url + "ajoutEvenement?user="+pseudo+"&rasp="+str(idRasp)+"&type=Entree&date="+date+"%20"+heure)
            r.get_method = lambda: 'PUT'
            reponse = urllib2.urlopen(r)
            return True
        else :
            return False 
    
def ajoutEvenementSorti(udeco):
    global idRasp
    if ( len(udeco) != 0):
        for pseudo in udeco:            
            if (pseudo in AuthorisedUsers):
                r = urllib2.Request(the_url + "ajoutEvenement?user="+pseudo+"&rasp="+str(idRasp)+"&type=Sorti&date="+date+"%20"+heure)
                r.get_method = lambda: 'PUT'
                reponse = urllib2.urlopen(r)
        return True
        
    else :
        return False


#scan meme quand ce n'est pas penible !
def scanBLE():
    #global users
    global lenscan, endscan, date, heure
    global ActualScannedUsers, LastScannedUsers    
    os.system("sudo hciconfig hci0 up")
    try :
        stopBLE()
        LastScannedUsers = set()
        LastScannedUsers = ActualScannedUsers
        ActualScannedUsers = set()
        print("\nscan en cours")
        #scancmd = "sudo hcitool lescan"
        date = strftime("%Y-%m-%d", gmtime())
        heure = strftime("%H:%M:%S", gmtime())
        print date, heure
        print('')
        proc = subprocess.Popen(['sudo','hcitool','lescan'], stdout=subprocess.PIPE, bufsize=0) # start process
        for line in iter(proc.stdout.readline, b''): # read output line-by-line       
            lenscan=lenscan+1
            #print(line)
            user = ''
                
            if (lenscan>0) :
                user = line[18:len(line)-1]
                if(user != '(unknown)' and user != ''):
                    ActualScannedUsers.add(user)
                    print(user)
        
            if (lenscan>endscan):
                #print'nombre de lignes en sortie du scan', lenscan
                print('')
                print('en attente du prochain scan')
                print('')
                print 'Utilisateurs dans l ancien scan', LastScannedUsers
                print 'Utilisateurs dans le dernier scan', ActualScannedUsers
                lenscan=0
                break
            
        stopBLE()
        proc.kill()
        proc.communicate()[0]
    
    except(OSError):
        stopBLE()
        pass
    

    
    #storezone = "cd /home/pi/Desktop/Ptut_Penib"
    #os.system(storezone)
    #file = open('/home/pi/Desktop/Ptut_Penib/resultscan4.txt', 'w+')
    

   
    ''' ancienne architecture avec broadcast beacon

    os.system("cd /home/pi/bluez/bluez-5.23/tools")
    os.system("sudo hciconfig hci0 up")
    os.system("sudo hciconfig hci0 leadv 3")
    uuid = "A9 02 07 43 LD 75 46 AB B8 FC D6 E7 C1 50 ED 07"
    major = "00 00"
    minor = "00 00"
    txPower = "C8 00"
    broadcmd = "sudo hcitool -i hci0 cmd 0x08 0x008 1E 02 01 1A 1A FF 4C 00 02 15" + " " + uuid + " " + major + " " + minor + " " + txPower
    os.system(broadcmd)
    '''

def stopBLE():
   os.system("sudo hciconfig hci0 down")
   os.system("sudo hciconfig hci0 up")

# main() function
def main():
    global date, heure
    print 'starting...', '\n-----'
    '''
    #scanBLE()
    date = strftime("%Y-%m-%d", gmtime())
    heure = strftime("%H:%M:%S", gmtime())
    print(date, heure)
    print(utilisateurGetCompte('ytixi'))
    utilisateurGetCompte('ytixier')
    '''
    


    while True:
        try:           
            scanBLE()
            res = presence(ActualScannedUsers,LastScannedUsers)
            #print res
            print('devices nouveaux', res[0])
            ajoutEvenementEntree(res[0])
            #print('ajout en entree',ajoutEvenementEntree(res[0]))
            print('devices sortant', res[1])
            ajoutEvenementSorti(res[1])
            #print('ajout en sortie', ajoutEvenementSorti(res[1]))
            

        except:
            print 'Erreur fatale de l"update'
            break
    

     
# call main
if __name__ ==  '__main__':
    main()
