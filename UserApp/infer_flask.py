from flask import Flask, request
import json

import os
import sys
import numpy as np
from keras.models import load_model
import keras.utils as image
import argparse
from PIL import Image

Image.LOAD_TRUNCATED_IMAGES = True

import datetime

users = dict()

app = Flask(__name__)
@app.route('/')
def up():
    return 'Flask is up and running!'

@app.route('/users/<user>', methods=['GET', 'POST', 'DELETE'])
def user_information(user):
    global users
    if request.method == 'GET':
        if(user in users.keys()):
            return users[user]['images']
        return "User '{}' not found".format(user)
    if request.method == 'POST':
        if (user not in users.keys()):
            users[user] = dict()
            users[user]['model'] = model = load_model(modelo)
            users[user]['images'] = []
        request_data = request.get_json()
        image = request_data['file']
        users[user]['images'].append(image)
        if(len(users[user]['images']) < 3):
            #return "New Image added: '{}'".format(image)
            return "0" # por simplicidad imprimo "no hay copia"
        result = main(users[user]['model'], users[user]['images'][-3], users[user]['images'][-2], users[user]['images'][-1])
        return "{}".format(result)
    if request.method == 'DELETE':
        if (user in users.keys()):
            del users[user]
            return "User '{}' deleted".format(user)
        return "User '{}' NOT deleted".format(user)



# Parámetros de las imágenes
dim_imagen = (50, 50)
canales_color = 3

# Ruta de la carpeta con las imágenes
modelo = '../Model/Modelos/modelo-50.h5'


def cargar_imagen(ruta):
    imagen = Image.open(ruta)

    return imagen


def convertir_a_jpg(imagen):
    imagen.load()
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


def main(model, ruta_imagen_1, ruta_imagen_2, ruta_imagen_3):


    secuencia = cargar_imagenes(ruta_imagen_1, ruta_imagen_2, ruta_imagen_3)

    # Realiza la clasificación
    clase = (np.array(model.predict(secuencia)).flatten() > 0.5).astype(int)

    # Acción basada en la clase asignada
    if clase == 0:
        #sys.exit('0')
        print("{0}\n{1}\n{2} - 0\n".format(ruta_imagen_1, ruta_imagen_2, ruta_imagen_3))
        return "0"
    elif clase == 1:
        #sys.exit('1')
        print("{0}\n{1}\n{2} - 1\n".format(ruta_imagen_1, ruta_imagen_2, ruta_imagen_3))
        return "1"

start = 0

if __name__ == '__main__':
    start = datetime.datetime.now()
    parser = argparse.ArgumentParser()
    parser.add_argument("arg1", help="Ruta de la primera imágen de la secuencia")
    parser.add_argument("arg2", help="Ruta de la segunda imágen de la secuencia")
    parser.add_argument("arg3", help="Ruta de la tercera imágen de la secuencia")
    args = parser.parse_args()
    main(args.arg1, args.arg2, args.arg3)

