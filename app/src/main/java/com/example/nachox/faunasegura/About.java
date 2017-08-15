package com.example.nachox.faunasegura;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class About {

    private String quienessono;
    private String quehacenmos;

    public About(String quehacen,String quienessono) {

        this.quehacenmos = quehacen;
        this.quienessono = quienessono;


    }


    public static final List<About> ABOUT = new ArrayList<About>();
    static {
        ABOUT.add(new About( "Brindamos apoyo para la correcta Denuncias del maltrato animal" +
                "Fomentamos vínculos entre la sociedad civil y organizaciones gubernamentales" +
                "Ayudamos a crear conciencia del cuidado y protección a las especies." +
                "Aportamos datos confiables sobre la propiedad responsable" +
                "Generamos registros que apoyen a los usuarios a tener información relevante sobre temas de interés" +
                "Generamos una comunidad de personas afines a nuestra misión capaces de colaborar con actividades de otras instituciones. \n "," Somos un grupo de personas preocupados por el bienestar animal, y la protección de las especies silvestres, y deseamos crear consciencia social sobre la importancia del cuidado y fomento de leyes que protejan a las especies vulnerables"));


    }


    public String getquehacen() {
        return quehacenmos;
    }
    public String getQuienessono() {
        return quienessono;
    }



}
