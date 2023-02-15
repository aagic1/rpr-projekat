package ba.unsa.etf.rpr.dao;


/**
 * Facotry method for singleton implementation of DAOs
 */
public class DaoFactory {
    private static final IngredientDao ingredientDao = IngredientDaoSQLImpl.getInstance();
    private static final InstructionDao instructionDao = InstructionDaoSQLImpl.getInstance();
    private static final UserDao userDao = UserDaoSQLImplementation.getInstance();
    private static final RecipeDao recipeDao = RecipeDaoSQLImpl.getInstance();

    private DaoFactory() {

    }

    public static IngredientDao ingredientDao() {
        return ingredientDao;
    }

    public static InstructionDao instructionDao() {
        return instructionDao;
    }

    public static UserDao userDao() {
        return userDao;
    }

    public static RecipeDao recipeDao() {
        return recipeDao;
    }
}
