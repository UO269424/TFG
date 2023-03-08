import os
from PIL import Image
import numpy as np

# directorio que contiene las imágenes
directory = "ruta/a/directorio"

# tamaño de las imágenes que se espera que el modelo reciba como entrada
image_size = (224, 224)

# lista para almacenar los tensores de entrada
x_train = []

# lista para almacenar las etiquetas de salida
y_train = []

# iterar sobre los archivos en el directorio
for filename in os.listdir(directory):
    if filename.endswith("_0.png") or filename.endswith("_1.png"):
        # cargar la imagen
        img = Image.open(os.path.join(directory, filename))

        # cambiar el tamaño de la imagen
        img = img.resize(image_size)

        # convertir la imagen a un arreglo de NumPy
        img_array = np.array(img)

        # agregar una dimensión para representar el "batch" (conjunto) de imágenes
        img_tensor = np.expand_dims(img_array, axis=0)

        # agregar el tensor de entrada a la lista x_train
        x_train.append(img_tensor)

        # agregar la etiqueta de salida a la lista y_train
        if filename.endswith("_0.png"):
            y_train.append(0)
        else:
            y_train.append(1)

# concatenar los tensores de entrada en un solo tensor de entrenamiento
x_train = np.concatenate(x_train, axis=0)

# convertir la lista de etiquetas en un arreglo de NumPy
y_train = np.array(y_train)
