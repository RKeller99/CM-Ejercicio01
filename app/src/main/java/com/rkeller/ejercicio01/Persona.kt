package com.rkeller.ejercicio01;

import android.os.Parcelable;
import kotlinx.parcelize.Parcelize;

@Parcelize
class Persona(
    var nombre: String?,
    var apellidos: String?,
    var edad: Int,
    var signo: String?,
    var signoChino: String?,
    var correo: String?,
    var numCuenta: Int,
    var carrera: Int
) : Parcelable