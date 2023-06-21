import os
import numpy as np
from PIL import Image
from tensorflow import keras
import keras
from keras.preprocessing import image
from keras.models import Sequential
from keras.layers import Dense, Conv2D, MaxPooling2D, Flatten, TimeDistributed
from keras.models import load_model
from tensorflow.keras.preprocessing.image import load_img, img_to_array
from sklearn.metrics import cohen_kappa_score


def main():
    # Parámetros de las imágenes
    dim_imagen = (50, 50)
    canales_color = 3

    # Ruta de la carpeta con las imágenes
    carpeta_imagenes = "../ImageClassifier/Test"

    # Obtener la lista de nombres de archivos en la carpeta de imágenes
    nombres_archivos = sorted(os.listdir(carpeta_imagenes))

    # Crear listas para almacenar las secuencias de imágenes y las etiquetas correspondientes
    secuencias = []
    etiquetas = []

    # Generar las secuencias de imágenes y obtener las etiquetas
    for i in range(len(nombres_archivos) - 2):
        secuencia = []
        for j in range(3):
            nombre_archivo = nombres_archivos[i + j]
            ruta_imagen = os.path.join(carpeta_imagenes, nombre_archivo)
            imagen = load_img(ruta_imagen, target_size=dim_imagen)
            imagen_array = img_to_array(imagen)
            imagen_array /= 255.0
            secuencia.append(imagen_array)
        secuencias.append(secuencia)

        # Obtener la etiqueta de la secuencia
        if nombres_archivos[i + 2].endswith("_1.jpg"):
            etiqueta = 1  # "Copia"
        else:
            etiqueta = 0  # "No copia"
        etiquetas.append(etiqueta)

    # Convertir las listas en arrays numpy
    x_test = np.array(secuencias)
    y_test = np.array(etiquetas)

    print("Length of the x_test array = " + str(len(x_test)))
    print("Length of the y_test array = " + str(len(y_test)))
    print(y_test)


    # cargar el modelo
    model = load_model('modelo-32.h5')
    print('Se ha cargado el modelo pre-entrenado.')

    # Evaluar el modelo con los datos de prueba
    score = model.evaluate(x_test, y_test, verbose=1)

    predictions = model.predict(x_test)

    print("Accuracy:", score[1])

    print(score)
    #print(predictions)

if __name__ == '__main__':
    main()
