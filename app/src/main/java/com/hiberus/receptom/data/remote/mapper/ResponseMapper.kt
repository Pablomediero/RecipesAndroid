package com.hiberus.receptom.data.remote.mapper

import com.hiberus.receptom.data.remote.remote_const.Const
import com.hiberus.receptom.model.local.Ingredient
import com.hiberus.receptom.model.remote.GeneratedAnswer
import com.hiberus.receptom.model.remote.RequestBody
import retrofit2.Response


class ResponseMapper {
    fun mapToResponseM1(type: List<Ingredient>): RequestBody {
        val listToString = type.map { it.name }
        return RequestBody(
            Const.API_MODEL,
            "Necesito una receta, tengo como ingredientes ${listToString.joinToString(",")}, devuelveme como resultado un formato JSON empezando directamente en llaves {} con el siguiente formato: " +
                    "{\"id\": 0,\"name\":\"Nombre receta\",\"ingredients\":[{\"id\":0,\"idRecipe\": 0,\"name\": \"Nombre de Ingrediente\"}],\"instructions\": \"Resultado preparación de la receta.\",\"serving\": numero de personas}\n" +
                    "Quiero que introduzcas todos los ingredientes que necesite la receta ademas de los que ya tengo, todos los campos id quiero que sean 0 y en una sola linea, que no existan saltos de linea",
            1000,
            0,
        )
    }

    fun mapToResponseM2(type: List<Ingredient>): RequestBody {
        return RequestBody(
            Const.API_MODEL,
            "No quiero ${type[0].name}, necesito otra receta distinta pero con los mismos ingredientes o parecidos, devuelveme como resultado un formato JSON empezando la respuesta directamente desde el inicio de llaves ({}) sin caracteres previos, con el siguiente formato: " +
                    "{\"id\": 0,\"name\":\"Nombre receta\",\"ingredients\":[{\"id\":0,\"idRecipe\": 0,\"name\": \"Nombre de Ingrediente\"}],\"instructions\": \"Resultado preparación de la receta.\",\"serving\": numero de personas}\n" +
                    "Quiero que introduzcas todos los ingredientes que necesite la receta ademas de los que ya tengo, todos los campos id quiero que sean 0 y en una sola linea, que no existan saltos de linea",
            1000,
            0,
        )
    }

    fun mapFromResponse(type: Response<GeneratedAnswer>): GeneratedAnswer {
        return GeneratedAnswer(
            type.body()!!.choices,
            type.body()!!.created,
            type.body()!!.id,
            type.body()!!.model,
            type.body()!!.`object`,
            type.body()!!.usage,
            )
    }
}