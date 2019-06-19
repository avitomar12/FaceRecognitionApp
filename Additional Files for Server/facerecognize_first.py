
import face_recognition 
import os


l=os.listdir("/var/www/html/trainingimage")
length=len(l)
encode=[0]*length
for i in range (0,length):
        k='/var/www/html/trainingimage/'+str(l[i])
        know= face_recognition.load_image_file(k)
        encod=face_recognition.face_encodings(know)[0]
        encode[i]=encod

import pickle
with open('data.pickle','wb') as f:
    pickle.dump(encode,f,pickle.HIGHEST_PROTOCOL)
print("success")
