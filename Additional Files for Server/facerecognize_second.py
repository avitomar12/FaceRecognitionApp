
import face_recognition 
import os
import pickle

l=os.listdir("/var/www/html/trainingimage")
length=len(l)

with open('data.pickle','rb') as f:
	encode=pickle.load(f)




image_to_test = face_recognition.load_image_file("/var/www/html/uploads/avi1.jpg")
image_to_test_encoding = face_recognition.face_encodings(image_to_test)[0]

# See how far apart the test image is from the known faces
face_distances = face_recognition.face_distance(encode, image_to_test_encoding)
m=min(face_distances)
if  m< .5:
        print(l[list(face_distances).index(min(face_distances))].split(".")[0])
else:
        print("Unknown")


