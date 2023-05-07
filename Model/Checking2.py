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
    directory = "../ImageClassifier/Test"

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
    x_test = []
    y_test = []
    for i in range(2, len(imagenes)):
        secuencia = [imagenes[i - 2], imagenes[i - 1], imagenes[i]]
        nombres = [names[i-2], names[i-1], names[i]]
        x_test.append(secuencia)
        if nombres[len(nombres) - 1].endswith("_1.jpg"):
            y_test.append(1)
        else:
            y_test.append(0)

    x_test = np.array(x_test)
    y_test = np.array(y_test)
    y_test = y_test.reshape(-1, 1)

    print("Length of the x_test array = " + str(len(x_test)))
    print("Length of the y_test array = " + str(len(y_test)))
    print(y_test)


    # cargar el modelo
    model = load_model('modelo_entrenado.h5')
    print('Se ha cargado el modelo pre-entrenado.')

    # Evaluar el modelo con los datos de prueba
    score = model.evaluate(x_test, y_test, verbose=1)
    print("Accuracy:", score[1])

if __name__ == '__main__':
    main()
