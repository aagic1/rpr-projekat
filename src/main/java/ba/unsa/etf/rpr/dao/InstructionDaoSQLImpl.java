package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Instruction;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        Instruction instruction = new Instruction();
        try {
            instruction.setId(rs.getInt("id"));
            instruction.setStep(rs.getInt("step"));
            instruction.setDescription(rs.getString("description"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> object2row(Instruction object) {
        return null;
    }
}
