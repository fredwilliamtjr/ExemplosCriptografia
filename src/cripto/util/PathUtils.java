/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior
 */
public class PathUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String caminhoDiscoLocal() {
        String path = "";
        try {
            String caminho = new File("/").getCanonicalPath();
            path = caminho;
        } catch (IOException ex) {
            Logger.getLogger(PathUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }

    public static String caminhoRaizProjeto() {
        URL resource = PathUtils.class.getResource("../../");
        return resource.getPath();
    }

}
