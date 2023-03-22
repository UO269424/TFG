import os
from PIL import Image
import numpy as np
from keras.preprocessing import image
from keras.models import load_model

# Cargar el modelo
model = load_model('modelo_entrenado.h5')
print('Se ha cargado el modelo pre-entrenado.')

# Ruta de la imagen que quieres clasificar
img_path = '../ImageClassifier/temp'

filename = 'img.jpg'

# Cargar la imagen y cambiar su tamaño para que tenga las mismas dimensiones que las imágenes de entrenamiento
img = Image.open(os.path.join(img_path, filename))

# Convertir la imagen en un arreglo de numpy
img_array = np.array(img)

# Agregar una dimensión adicional para representar el "batch" (conjunto) de imágenes
img_tensor = np.expand_dims(img_array, axis=0)

# Normalizar los valores de los píxeles de la imagen
img_tensor = (img_tensor/255.)

# Utilizar el modelo para predecir la clase de la imagen
prediccion = model.predict(img_tensor)

# Interpretar el resultado de la predicción
probabilidad_positivo = prediccion[0][0]
if probabilidad_positivo > 0.5:
    print('La imagen es positiva con una probabilidad del', probabilidad_positivo)
else:
    print('La imagen es negativa con una probabilidad del', 1 - probabilidad_positivo)