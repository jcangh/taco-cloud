package tacos.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
public class IngredientConverter implements Converter<String, Ingredient>{

	@Autowired
	private IngredientRepository ingredientRepo; 
	
	@Override
	public Ingredient convert(String arg0) {
		Ingredient ingredient = ingredientRepo.findOne(arg0);
		return ingredient;
	}

}
