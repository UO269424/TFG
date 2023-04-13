import os
import numpy as np
from PIL import Image
from keras.models import load_model

# Definir las dimensiones de las imágenes de entrada
input_shape = (50, 50, 3)

# tamaño de las imágenes que se espera que el modelo reciba como entrada
image_size = (224, 224)

# directorio que contiene las imágenes de prueba
test_dir = "../ImageClassifier/Test"

# lista para almacenar los tensores de entrada
x_test = []

# lista para almacenar las etiquetas de salida
y_test = []

# iterar sobre los archivos en el directorio
for filename in os.listdir(test_dir):
    if filename.endswith("_0.jpg") or filename.endswith("_1.jpg"):
        # cargar la imagen
        img = Image.open(os.path.join(test_dir, filename))

        # cambiar el tamaño de la imagen
        #img = img.resize(image_size)

        # convertir la imagen a un arreglo de NumPy
        img_array = np.array(img)

        # agregar una dimensión para representar el "batch" (conjunto) de imágenes
        img_tensor = np.expand_dims(img_array, axis=0)

        # agregar el tensor de entrada a la lista x_test
        x_test.append(img_tensor)

        # agregar la etiqueta de salida a la lista y_test
        if filename.endswith("_0.jpg"):
            y_test.append(0)
        else:
            y_test.append(1)

# concatenar los tensores de entrada en un solo tensor de prueba
x_test = np.concatenate(x_test, axis=0)

# convertir la lista de etiquetas en un arreglo de NumPy
y_test = np.array(y_test)

# cargar el modelo
model = load_model('modelo_entrenado.h5')
print('Se ha cargado el modelo pre-entrenado.')

# Evaluar el modelo con los datos de prueba
score = model.evaluate(x_test, y_test, verbose=1)
print("Accuracy:", score[1])
