package com.leancode.datajpa.models.provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.leancode.datajpa.models.provider.dao.ICloudinaryDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CloudinaryDao implements ICloudinaryDao {

    @Autowired
    @Qualifier("cloudinaryInit")
    // Inicializamos la instancia de nuestro objeto cloudinary (que ya tiene todos los parametros necesarios)
    private Cloudinary cloudinaryInstance;

    @Override
    // Implementamos el metodo que nos permita subir el archivo al host
    public Map<String, Object> upload(MultipartFile file) {
        Map <String, Object> result = new HashMap<String, Object>();
        // Preguntamos si el archivo esta vacio:
        var fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!(file.isEmpty())) {
            if (fileExtension.equals("png") || fileExtension.equals("jpg")) {
                try {
                    // Se retorna un map como resultado de la consulta:
                    var uploadPhotoResult = cloudinaryInstance.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    result.put("urlFoto", uploadPhotoResult.get("url").toString());
                    result.put("message", "La foto se subio con exito.");
                    result.put("idFoto", uploadPhotoResult.get("asset_id").toString());
                    result.put("isUpload", true);
                    return result;
                } catch (Exception e) {
                    result.put("message", "Ocurrio un error inesperado al subir el archivo de la foto.");
                    result.put("isUpload", false);
                    return result; 
                }
            } else {
                // Si no es de la extension png o jpg, enviamos el siguiente mensaje:
                result.put("message", "No se pudo subir el archivo de la foto (formato inadecuado).");
                result.put("isUpload", false);
                return result;
            }
        }
        // Si el archivo esta vacio, se manda una url con cadena vacia:
        result.put("urlFoto", "");
        result.put("isUpload", false);
        return result;
    }
}
