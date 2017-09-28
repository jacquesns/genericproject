package com.websystique.api.controllers.common;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.websystique.api.model.Order;
import com.websystique.api.model.OrderStatus;
import com.websystique.api.repositories.ProductRepository;
import com.websystique.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.*;

@JsonComponent
public class OrderSerializationComponent {

	@Autowired
    public OrderSerializationComponent(ProductRepository productRepository, UserRepository userRepository){
    	Deserializer.productRepository=productRepository;
    	Deserializer.userRepository=userRepository;
    }
    
    public static class Serializer extends JsonObjectSerializer<Order> {

		@Override
		protected void serializeObject(Order order, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
            generator.writeNumberField("id", order.getId());
            generator.writeNumberField("totalValue", order.getTotalValue());
            if(order.getStatus() !=null){
            	generator.writeStringField("status", order.getStatus().toString());
            }
            if(order.getUser() !=null){
            	generator.writeNumberField("userId", order.getUser().getId());
            }
            if(order.getProduct() !=null){
            	generator.writeNumberField("productId", order.getProduct().getId());
            }            
		}

    }
    
    public static class Deserializer extends JsonObjectDeserializer<Order> {
        static ProductRepository productRepository;
        static UserRepository userRepository;
		@Override
		protected Order deserializeObject(JsonParser parser, DeserializationContext context, ObjectCodec codec, JsonNode tree)
				throws IOException {
			Order order = new Order();
			if(tree.get("id") !=null){
				order.setId(tree.get("id").asLong());				
			}
			if(tree.get("status") !=null){
				order.setStatus(OrderStatus.valueOf(tree.get("status").asText()));
			}
			if(tree.get("totalValue") != null){
				order.setTotalValue(tree.get("totalValue").asDouble());
			}
			if(tree.get("userId") != null){
				order.setUser(userRepository.findOne(tree.get("userId").asLong()));
			}
			if(tree.get("productId") != null){
				order.setProduct(productRepository.findOne(tree.get("productId").asLong()));
			}
			return order;
		}

    }

}