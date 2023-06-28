import os
import sys
import numpy as np
from keras.models import load_model
import keras.utils as image
import argparse
from PIL import Image

import datetime

# Parámetros de las imágenes
dim_imagen = (50, 50)
canales_color = 3

# Ruta de la carpeta con las imágenes
modelo = '../Model/Modelos/modelo-50.h5'


def cargar_imagen(ruta):
    imagen = Image.open(ruta)
    return imagen


def convertir_a_jpg(imagen):
    imagen_jpg = imagen.convert("RGB")
    return imagen_jpg


def preprocesar_imagen(imagen_jpg):
    imagen_preprocesada = imagen_jpg.resize(dim_imagen)  # Ajustar el tamaño de la imagen según los requisitos del modelo
    imagen_preprocesada = image.img_to_array(imagen_preprocesada)
    imagen_preprocesada = imagen_preprocesada / 255.0  # Normalizar los valores de píxeles entre 0 y 1
    return imagen_preprocesada

def cargar_imagenes(ruta_imagen_1, ruta_imagen_2, ruta_imagen_3):
    # Carga y procesamiento de las imágenes
    imagen_1  = preprocesar_imagen(convertir_a_jpg(cargar_imagen(ruta_imagen_1)))

    imagen_2 = preprocesar_imagen(convertir_a_jpg(cargar_imagen(ruta_imagen_2)))

    imagen_3 = preprocesar_imagen(convertir_a_jpg(cargar_imagen(ruta_imagen_3)))

    # Organiza las imágenes en una secuencia
    secuencia = np.array([imagen_1, imagen_2, imagen_3])
    secuencia = np.expand_dims(secuencia, axis=0)

    return secuencia


def main(ruta_imagen_1, ruta_imagen_2, ruta_imagen_3):
    model = load_model(modelo)
    print("Loaded Model: {0}".format(datetime.datetime.now() - start))

    secuencia = cargar_imagenes(ruta_imagen_1, ruta_imagen_2, ruta_imagen_3)
    print("Loaded Images: {0}".format(datetime.datetime.now() - start))

    # Realiza la clasificación
    clase = (np.array(model.predict(secuencia)).flatten() > 0.5).astype(int)
    print("Predict: {0}".format(datetime.datetime.now() - start))

    # Acción basada en la clase asignada
    if clase == 0:
        #sys.exit('0')
        print("0")
    elif clase == 1:
        #sys.exit('1')
        print("1")

start = 0

if __name__ == '__main__':
    print("Start")
    start = datetime.datetime.now()
    print("Parse Arguments: {0}".format(datetime.datetime.now() - start))
    parser = argparse.ArgumentParser();
    parser.add_argument("arg1", help="Ruta de la primera imágen de la secuencia")
    parser.add_argument("arg2", help="Ruta de la segunda imágen de la secuencia")
    parser.add_argument("arg3", help="Ruta de la tercera imágen de la secuencia")
    args = parser.parse_args();
    print("Before Main: {0}".format(datetime.datetime.now() - start))
    main(args.arg1, args.arg2, args.arg3)
    print("End: {0}".format(datetime.datetime.now() - start))

