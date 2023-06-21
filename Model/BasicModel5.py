import os
import numpy as np
from tensorflow import keras
from tensorflow.keras.preprocessing.image import load_img, img_to_array
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, TimeDistributed, LSTM

# Ruta de la carpeta que contiene las imágenes
carpeta_imagenes = "C:/Users/Alonso/Desktop/Screenshots-Converted-v2"

# Parámetros de las imágenes
dim_imagen = (50, 50)
canales_color = 3

def main():
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
    x_train = np.array(secuencias)
    y_train = np.array(etiquetas)

    # Definir el modelo
    model = Sequential()
    model.add(TimeDistributed(Conv2D(32, (3, 3), activation='relu'),
                              input_shape=(3, dim_imagen[0], dim_imagen[1], canales_color)))
    model.add(TimeDistributed(MaxPooling2D(pool_size=(2, 2))))
    model.add(TimeDistributed(Flatten()))
    model.add(LSTM(64, activation='relu'))
    model.add(Dense(1, activation='sigmoid'))

    # Compilar el modelo
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    # Entrenar el modelo
    model.fit(x_train, y_train, epochs=30)

    # Guardar el modelo entrenado
    model.save('modelo-32.h5')






if __name__ == '__main__':
    main()

