package tacos.data;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {
	private JdbcTemplate jdbc;

	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return taco;
	}

	private long saveTacoInfo(Taco taco) {
//		taco.setCreatedAt(new Date());
//		PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
//				"insert into Taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP)
//						.newPreparedStatementCreator(
//								Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		int state = jdbc.update(psc, keyHolder);
//		return keyHolder.getKey().longValue();
		taco.setCreatedAt(new Date());  
		String sql = "insert into TACO (name, createdAt) values (?, ?)";
	      KeyHolder keyHolder = new GeneratedKeyHolder();

	      jdbc.update(
	              connection -> {
	                  PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
	                  ps.setString(1, taco.getName());
	                  ps.setTimestamp(2,new Timestamp(taco.getCreatedAt().getTime()));
	                  return ps;
	              }, keyHolder);

	      Number key = keyHolder.getKey();
	      return keyHolder.getKey().longValue();
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) " + "values (?, ?)", tacoId, ingredient.getId());
	}
}
