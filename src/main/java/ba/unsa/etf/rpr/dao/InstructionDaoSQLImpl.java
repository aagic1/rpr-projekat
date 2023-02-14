package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Instruction;

import java.sql.ResultSet;
import java.util.Map;

/**
 * MySQL implementation of DAO
 * @author Ahmed Agic
 */
public class InstructionDaoSQLImpl extends AbstractDao<Instruction> implements InstructionDao {
    public InstructionDaoSQLImpl() {
        super("instruction");
    }

    @Override
    public Instruction row2object(ResultSet rs) {
        return null;
    }

    @Override
    public Map<String, Object> object2row(Instruction object) {
        return null;
    }
}
