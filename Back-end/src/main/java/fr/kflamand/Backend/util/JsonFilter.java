package fr.kflamand.Backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import fr.kflamand.Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

/*
public final class JsonFilter {

    @Autowired
    ObjectMapper objectMapper ;

    public String UserFilter(User user) {

        // transformation manuel en utilisant un "filtre" Jackson en excluant l'attribut "registrationToken"
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAllExcept("registrationToken", "password"));

        // and then serialize using that filter provider:
        String retour = null;
        try {
            retour = objectMapper.writer(filters).writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return retour;
    }


}
*/