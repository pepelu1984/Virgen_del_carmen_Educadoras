package com.dynamicdroides.virgendelcarmen.data;

import com.dynamicdroides.virgendelcarmen.service.data.Behavior;

/**
 * Created by noel on 9/1/16.
 */
public class BehaviorData
{

    public String type;
    public String value;

    public BehaviorData(String type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public static BehaviorData importFrom(Behavior behavior)
    {
        return new BehaviorData(behavior.getTipo(), behavior.getValor());
    }

    public static String getStringFor(String type)
    {
        if (type.equals(StudentData.SALAD))
            return "Ensalada";

        if (type.equals(StudentData.DESSERT))
            return "Postre";

        if (type.equals(StudentData.ROW))
            return "Fila";

        if (type.equals(StudentData.DINNINGROOM))
            return "Comedor";

        if (type.equals(StudentData.GAMES))
            return "Juegos";

        if (type.equals(StudentData.WASHING))
            return "Aseo";

        if (type.equals(StudentData.DISH1))
            return "Primer plato";

        if (type.equals(StudentData.DISH2))
            return "Segundo plato";

        if (type.equals(StudentData.HAPPY))
            return "Contento";

        if (type.equals(StudentData.SLEEP))
            return "Sueño";

        if (type.equals(StudentData.PEE))
            return "Esfínter";

        if (type.equals(StudentData.BAG))
            return "Bolsa de aseo";

        if (type.equals(StudentData.CLOTHES))
            return "Ropa de cambio";

        if (type.equals(StudentData.SUMMARY))
            return "Observaciones";

        return "";
    }

}
