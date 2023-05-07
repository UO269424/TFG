import os
import numpy as np
from PIL import Image
import keras
from keras.preprocessing import image
from keras.models import Sequential
from keras.layers import Dense, Conv2D, MaxPooling2D, Flatten, TimeDistributed
from keras.models import load_model


def main():
    # Definir las dimensiones de las imágenes de entrada
    input_shape = (50, 50, 3)
    sequence_shape = (3, 50, 50, 3)

    # Ruta de la carpeta con las imágenes
    directory = "../ImageClassifier/Train"

    # Lista para almacenar las matrices de imágenes
    imagenes = []
    names = []

    # Iterar sobre los archivos de la carpeta
    for filename in os.listdir(directory):
        if filename.endswith("_0.jpg") or filename.endswith("_1.jpg"):
            # Cargar la imagen
            img = Image.open(os.path.join(directory, filename))
            # Convertir la imagen a una matriz NumPy
            img_array = np.array(img).astype(np.float32)
            # Agregar la matriz a la lista de imágenes
            imagenes.append(img_array)
            names.append(filename)

    # Convertir la lista de imágenes en una matriz NumPy
    imagenes = np.array(imagenes)

    # Crear secuencias de imágenes y etiquetas correspondientes
    x_train = []
    y_train = []
    for i in range(2, len(imagenes)):
        secuencia = [imagenes[i-2], imagenes[i-1], imagenes[i]]
        nombres = [names[i - 2], names[i - 1], names[i]]
        x_train.append(secuencia)
        if nombres[len(nombres)-1].endswith("_1.jpg"):
            y_train.append(1)
        else:
            y_train.append(0)
    x_train = np.array(x_train)
    y_train = np.array(y_train)
    y_train = y_train.reshape(-1, 1)

    print("Length of the x_train array = " + str(len(x_train)))
    print("Length of the y_train array = " + str(len(y_train)))

    # Crear un modelo secuencial
    try:
        model = load_model('modelo_entrenado.h5')
        print('Se ha cargado el modelo pre-entrenado.')
    except:
        print('No se ha encontrado el modelo pre-entrenado. Se creará un nuevo modelo.')
        model = Sequential()

        # Agregar capas convolucionales
        model.add(TimeDistributed(Conv2D(32, kernel_size=(3, 3), activation='relu', input_shape=sequence_shape)))
        model.add(TimeDistributed(MaxPooling2D(pool_size=(2, 2))))
        model.add(TimeDistributed(Conv2D(64, kernel_size=(3, 3), activation='relu')))
        model.add(TimeDistributed(MaxPooling2D(pool_size=(2, 2))))
        model.add(TimeDistributed(Conv2D(128, kernel_size=(3, 3), activation='relu')))
        model.add(TimeDistributed(MaxPooling2D(pool_size=(2, 2))))
        model.add(TimeDistributed(Flatten()))

        # Agregar capas completamente conectadas
        model.add(Dense(128, activation='relu'))
        model.add(Dense(1, activation='sigmoid'))

        # Compilar el modelo
        model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])

    # Entrenar el modelo con los datos de entrada y etiquetas correspondientes
    model.fit(x_train, y_train, batch_size=32, epochs=10, validation_split=0.2)

    model.save('modelo_entrenado.h5')


if __name__ == '__main__':
    main()
