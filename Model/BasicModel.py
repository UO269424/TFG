import os
import random

from PIL import Image
import numpy as np
import keras
from keras.models import Sequential
from keras.layers import Dense, Conv2D, MaxPooling2D, Flatten
from keras.preprocessing import image
from keras.models import load_model

# Definir las dimensiones de las imágenes de entrada
input_shape = (50, 50, 3)

# directorio que contiene las imágenes
directories = []
directories.append("../ImageClassifier/Cheat")
directories.append("../ImageClassifier/Ok")
directories.append("../ImageClassifier/TestingImages")

# tamaño de las imágenes que se espera que el modelo reciba como entrada
image_size = (224, 224)

# lista para almacenar los tensores de entrada
x_train = []

# lista para almacenar las etiquetas de salida
y_train = []

# iterar sobre los archivos en el directorio
for directory in directories:
    for filename in os.listdir(directory):
        if filename.endswith("_0.jpg") or filename.endswith("_1.jpg"):
            rand = random.randint(0,49);
            # cargar la imagen
            img = Image.open(os.path.join(directory, filename))

            # cambiar el tamaño de la imagen
            #img = img.resize(image_size)

            if rand == 8:
                print("Processing " + filename)

            # convertir la imagen a un arreglo de NumPy
            img_array = np.array(img)

            # agregar una dimensión para representar el "batch" (conjunto) de imágenes
            img_tensor = np.expand_dims(img_array, axis=0)

            # agregar el tensor de entrada a la lista x_train
            x_train.append(img_tensor)

            # agregar la etiqueta de salida a la lista y_train
            if filename.endswith("_0.jpg"):
                y_train.append(0)
            else:
                y_train.append(1)

# concatenar los tensores de entrada en un solo tensor de entrenamiento
x_train = np.concatenate(x_train, axis=0)

# convertir la lista de etiquetas en un arreglo de NumPy
y_train = np.array(y_train)

# Crear un modelo secuencial
try:
    model = load_model('modelo_entrenado.h5')
    print('Se ha cargado el modelo pre-entrenado.')
except:
    print('No se ha encontrado el modelo pre-entrenado. Se creará un nuevo modelo.')
    model = Sequential()

    # Agregar capas convolucionales
    model.add(Conv2D(32, kernel_size=(3, 3), activation='relu', input_shape=input_shape))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Conv2D(64, kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Conv2D(128, kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Flatten())

    # Agregar capas completamente conectadas
    model.add(Dense(128, activation='relu'))
    model.add(Dense(1, activation='sigmoid'))

    # Compilar el modelo
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])



# Entrenar el modelo con los datos de entrada y etiquetas correspondientes
model.fit(x_train, y_train, batch_size=32, epochs=10, validation_split=0.2)

model.save('modelo_entrenado.h5')





