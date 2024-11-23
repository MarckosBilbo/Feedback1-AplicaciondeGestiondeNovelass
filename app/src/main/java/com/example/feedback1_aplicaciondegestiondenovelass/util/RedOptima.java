package com.example.feedback1_aplicaciondegestiondenovelass.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


//Para comprimir los datos de la red y que la aplicación sea más rápida (para el PROFILER punto 2)
public class RedOptima {
    public static byte[] comprimirDatos(byte[] datos) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(datos);
        gzipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}