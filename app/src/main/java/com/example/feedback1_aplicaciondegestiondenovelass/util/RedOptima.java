package com.example.feedback1_aplicaciondegestiondenovelass.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
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



    public static byte[] descomprimirDatos(byte[] datos) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datos);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        gzipInputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

}